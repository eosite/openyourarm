(function($) {
	/**
	 * 属性盒插件
	 * @type {String}
	 */
	var pluginName = "mtProBox";
	$.fn[pluginName] = function(options, params) {

		if (!this.length) {
			return this;
		}

		if (typeof options === 'string') {
			var method = $.fn[pluginName].methods[options];
			if (method) {
				return method(this, params);
			} else {
				$.error('Method ' + options + ' does not exist on jQuery.' + pluginName);
			}
		}

		options = options || {};
		return this.each(function() {
			var state = $.data(this, pluginName);
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, pluginName, {
					options: $.extend({}, $.fn[pluginName].defaults, $.fn[pluginName].parseOptions(this), options)
				});
			}
			buildPlugin(this);
		});
	};

	function _sortGroup(rows, opts) {
		var groups = [];

		function getGroup(groupName) {
			for (var i = 0; i < groups.length; i++) {
				var group = groups[i];
				if (group.groupName === groupName) {
					return group;
				}
			}
			return null;
		}
		$.each(rows, function(i, row) {
			var group = getGroup(row[opts.groupField]);
			if (!group) {
				group = {
					groupName: row[opts.groupField],
					rows: [row],
					startRow: i
				};
				groups.push(group);
			} else {
				group.rows.push(row);
			}
		});
		return groups;
	}

	function buildPlugin(target) {
		var opts = $.data(target, pluginName).options;

		methods.__beforeRender__.call(target);
		methods.__render__.call(target);
		methods.__bindEvent__.call(target);
		methods.__afterRender__.call(target);
	}

	var methods = $.fn[pluginName].methods = {
		__renderCell__:function(row,column,opts){
			return opts.injectEditor[row.editor]?opts.injectEditor[row.editor](row,column):row[column.field];
		},
		__renderRow__: function(row) {
			var $this = $(this),
				data = $.data(this, pluginName),
				opts = data.options,
				rowhtml = [];
			$.each(opts.columns, function(index, column) {
				rowhtml.push("<td width=" + (column.width||"") + "><div class='mtbox_cell'>");
				if(column.edit){
					rowhtml.push(methods.__renderCell__(row,column,opts));
				}else{
					rowhtml.push(row[column.field]);
				}
				rowhtml.push("</div></td>");
			});
			return rowhtml.join("");
		},
		__renderTitle__: function() {
			var that = this,
				data = $.data(that, pluginName),
				opts = data.options,
				columns = opts.columns,
				headHtml = ['<table class="mtbox_table" cellpadding="0" cellspacing="0"><tr>'];
			$.each(columns, function(i, column) {
				headHtml.push("<td width=" + (column.width||"") + "><div class='mtbox_cell'>" + column.title + "</div></td>");
			});
			headHtml.push("</tr></table>");
			return headHtml.join("");
		},
		__render__: function() {
			var that = this,
				$that = $(that),
				data = $.data(that, pluginName),
				opts = data.options,
				groups = opts.groups,
				container = [],
				innerTable = [],
				table = [],
				rowIndex = 0,
				newRows = [];

			innerTable.push('<div class="mtbox_content ' + (opts.scroll ? "mtbox_scroll" : "") + '">');
			table.push('<div class="mtbox_content ' + (opts.scroll ? "mtbox_scroll" : "") + '">');
			$.each(groups, function(index, group) {
				innerTable.push('<div class="mtbox_group" group-index="' + index + '"><span class="mtbox_expander mtbox_collapse"></span></div>');
				innerTable.push('<table class="mtbox_table" group-index="' + index + '" cellpadding="0" cellspacing="0">');
				table.push('<div class="mtbox_group" group-index="' + index + '">' + group.groupName + '</div>');
				table.push('<table class="mtbox_table" group-index="' + index + '" cellpadding="0" cellspacing="0">');
				$.each(group.rows, function(i, row) {
					innerTable.push("<tr>");
					innerTable.push("</tr>");
					table.push("<tr class='mtbox_row' row-index='"+(rowIndex++)+"'>");
					table.push(methods.__renderRow__.call(that, row));
					table.push("</tr>");
					newRows.push(row);
				});
				table.push("</table>");
				innerTable.push("</table>");
			});
			innerTable.push('</div>');
			table.push('</div>');

			this.newRows = newRows;

			container.push('<div class="mtbox_container">');
			container.push('<div class="mtbox_inner"><div class="mtbox_header"></div>');
			container.push(innerTable.join(""));
			container.push('<div class="mtbox_footer"></div></div>');
			container.push('<div class="mtbox_view"><div class="mtbox_header">');
			container.push(methods.__renderTitle__.call(that));
			container.push('</div>');
			container.push(table.join(""));
			container.push('<div class="mtbox_footer"></div></div></div>');
			$that.addClass("mtbox").empty().append(container.join(""));
			$that.css({
				width: opts.width || defaults.width,
				height: opts.scroll ? (opts.height || defaults.height) : defaults.height
			});
			$that.find('.mtbox_view').width($that.width() - 18);
			if(opts.scroll)
				$that.find(".mtbox_scroll").height($that.height() - 26);
		},
		__beforeRender__: function() {
			var opts = $.data(this, pluginName).options;
			opts.groups = _sortGroup(opts.rows, opts);
			opts.beforeRender.call(this);
		},
		__afterRender__: function() {
			var opts = $.data(this, pluginName).options;
			opts.afterRender.call(this);
		},
		__bindEvent__: function() {
			var opts = $.data(this, pluginName).options,
				that = this,
				$that = $(that);
			$that.find(".mtbox_expander").bind("click.mtbox", function() {
				var $this = $(this),
					$tdiv = $this.closest('div'),
					index = $tdiv.attr("group-index");
				if ($this.hasClass('mtbox_collapse')) {
					$this.removeClass('mtbox_collapse').addClass('mtbox_expand');
					$that.find("table[group-index=" + index + "]").hide();
				} else {
					$this.removeClass('mtbox_expand').addClass('mtbox_collapse');
					$that.find("table[group-index=" + index + "]").show();
				}
			});
			$that.find(".mtbox_view .mtbox_content").bind("scroll.mtbox", function(e) {
				$that.find(".mtbox_inner .mtbox_content").scrollTop(this.scrollTop);
			});
			$that.find(".mtbox_row").bind("click.mtbox_row",function(){
				$that.find(".mtbox_row").removeClass('mtbox_select');
				$(this).addClass('mtbox_select');
				var index = $(this).attr("row-index");
				opts.clickRow.call(this,index,that.newRows[index],that.newRows);
			});
		}
	};

	$.fn[pluginName].parseOptions = function(target) {
		var $t = $(target);
		return $.extend({}, {
			showGroup: ($t.attr('showGroup') ? $t.attr('showGroup') == 'true' : undefined)
		});
	};

	// default options
	var defaults = $.fn[pluginName].defaults = {
		scroll: true,
		width: "300px",
		height: "auto",
		rows: [],
		columns: [],
		injectEditor: {},
		groupField: "groupName",
		beforeRender: function() {

		},
		afterRender:function(){

		},
		clickRow:function(index,row,rows){
			
		}
	};

})(jQuery);