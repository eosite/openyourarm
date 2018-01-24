/**
 * 表格 合并
 * 
 * @author klaus.wang
 * @param $
 */
(function($) {

	function visibleFilter($firstTd, $currentTd) {
		return $firstTd.is(":visible") && $currentTd.is(":visible");
	}

	function fnFilter(fn, $firstTd, $currentTd) {
		return (fn ? fn($firstTd, $currentTd) : ($firstTd.text() == $currentTd
				.text()));
	}

	function filters(fn, $firstTd, $currentTd) {
		return visibleFilter($firstTd, $currentTd)
				&& fnFilter(fn, $firstTd, $currentTd);
	}

	/**
	 * 获取当前单元格的相邻的单元格
	 */
	function getNearBy($td) {
		var nearBy = 'nearBy', near = $.data($td[0],nearBy);
		if(!near){
			var $tr = $td.closest("tr");
			var x = $td.index(), y = $tr.index();
			return $.data($td[0], nearBy, {
				x : x,
				y : y,
				top : $tr.prev() == null ? null : $tr.prev().find(
						">td:eq(" + x + ")"),
				bottom : $tr.next() == null ? null : $tr.next().find(
						">td:eq(" + x + ")"),
				left : $td.prev(),
				right : $td.next()
			});
		}
		return near;
	}

	function nearFilter(fn, $prevTd, $currentTd, w) {
		if ($prevTd && $currentTd) {
			var pn = getNearBy($prevTd), cn = getNearBy($currentTd);
			if (pn[w] && pn[w][0] && cn[w] && cn[w][0]) {
				return fnFilter(fn, pn[w], cn[w]);
			}
		}
		return true;
	}

	/**
	 * 合并指定表格（表格id为selector）指定列（列数为column）的相同文本的相邻单元格 selector
	 * 为需要合并单元格的所在列。为数字，从最左边第一列为1开始算起。
	 * isTree 是否按树形结构合并	
	 *	eg : $.rowspan("#table", 5, function(td0,
	 * td1){ return td0.text() === td1.text(); });
	 */
	function $rowspan(selector, column, fn, isTree) {

		if (typeof column == 'string') {
			$(selector + " tr:eq(0)").each(
					function(i, $tr) {
						$($tr).find("td:nth-child(" + column + ")").each(
								function($i, $td) {
									_rs(selector, ($(this).index() + 1), fn, isTree);
								});
					});
		} else {
			_rs(selector, column, fn, isTree);
		}
	}

	function _rs(selector, column, fn, isTree) {
		var $firstTd = "", $currentTd = "", spanNum = 0, //
		$selected = $(selector + " tr td:nth-child(" + column + ")");
		$selected
				.each(function(i) {
					var $this = $(this);
					if (i == 0) {
						$firstTd = $this;
						spanNum = 1;
					} else {
						var rst = filters(fn, $firstTd, $this)
								&& (isTree ? nearFilter(fn, $currentTd, $this, 'left') : true)
								&& ($this.attr('colspan') === $firstTd
										.attr('colspan'));
						$currentTd = $this;
						if (rst) {
							$currentTd.hide(); // remove();
							$firstTd.attr("rowspan", ++spanNum);
						} else {
							$firstTd = $this;
							spanNum = 1;
						}
					}
				});

		// ////合并行以后合并列
		function getColspan($td, collection) {
			var near = getNearBy($td), left = near.left;
			if (left && left[0]) {
				if (filters(fn, $td, left)
						&& left.attr('rowspan') === $td.attr('rowspan')) {
					collection.push(left);
					getColspan(left, collection);
				}
			}
		}

		$(selector + " td[rowspan]").each(function(i) {
			var $this = $(this), collection;
			if ($this.is(":visible")) {
				collection = [];
				getColspan($this, collection);
				if (collection.length > 0) {
					$this.attr("colspan", collection.length + 1);
					$.each(collection, function() {
						$(this).hide();
					});
				}
			}
		});
	}

	/**
	 * 合并指定表格（表格id为selector）指定行（行数为rowNum）的相同文本的相邻单元格 rowNum
	 * 为需要合并单元格的所在行。其参数形式请参考jQuery中nth-child的参数,如果为数字，则从最左边第一行为1开始算起。
	 * isTree 是否按树形结构合并	
	 * maxColNum
	 * 为指定行中单元格对应的最大列数，列数大于这个数值的单元格将不进行比较合并,此参数可以为空，为空则指定行的所有单元格要进行比较合并。
	 * 
	 * eg: $.colspan("#table", 3, 6, function(td0, td1){ return td0.text() ===
	 * td1.text(); });
	 * 
	 */
	function $colspan(selector, rowNum, maxColNum, fn, isTree) {
		var $firstTd = "", $currentTd = "", spanNum = 0;
		maxColNum = (maxColNum == void 0) ? 0 : maxColNum;
		var $selected = $(selector + " tr:nth-child(" + rowNum + ")");
		$selected.each(function(i) {
			$(this).children().each(
					function(i) {
						var $this = $(this);
						if (i == 0) {
							$firstTd = $this;
							spanNum = 1;
						} else if ((maxColNum > 0) && (i >= maxColNum)) {
							return;
						} else {
							var rst = filters(fn, $firstTd, $this)
									&& (isTree ? nearFilter(fn, $currentTd, $this, 'top') : true)
									&& ($this.attr('rowspan') === $firstTd
											.attr('rowspan'));
							$currentTd = $this;
							if (rst) {
								$currentTd.hide(); // remove();
								$firstTd.attr("colspan", ++spanNum);
							} else {
								$firstTd = $this;
								spanNum = 1;
							}
						}
					});
		});

		// ////////////合并列以后合并行
		function getRowspan($td, collection) {
			var near = getNearBy($td), bottom = near.bottom;
			if (bottom && bottom[0]) {
				if (filters(fn, $td, bottom)
						&& bottom.attr('colspan') === $td.attr('colspan')) {
					collection.push(bottom);
					getRowspan(bottom, collection);
				}
			}
		}

		$(selector + " td[colspan]").each(function(i) {
			var $this = $(this), collection;
			if ($this.is(":visible")) {
				collection = [];
				getRowspan($this, collection);
				if (collection.length > 0) {
					$this.attr("rowspan", collection.length + 1);
					$.each(collection, function() {
						$(this).hide();
					});
				}
			}
		});

	}
	
	//////方法在这里
	$.extend({
		rowspan : $rowspan, //行合并
		colspan : $colspan // 列合并
	});


})(jQuery);

