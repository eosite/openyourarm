// register the grid component
Vue.component('demo-grid', {
    template: '#grid-template',
    replace: true,
    props: {
        data: Array,
        columns: Array,
        filterKey: String,
        groupField: String,
        showGroup: Boolean,
        obj: Object,
        scroll: Boolean
    },
    data: function() {
        var sortOrders = {}
        this.columns.forEach(function(key) {
            sortOrders[key['field']] = 1
        })
        return {
            sortKey: '',
            sortOrders: sortOrders
        }
    },
    computed: {
        filteredData: function() {
            var sortKey = this.sortKey
            var filterKey = this.filterKey && this.filterKey.toLowerCase()
            var order = this.sortOrders[sortKey] || 1
            var data = this.data
            if (filterKey) {
                data = data.filter(function(row) {
                    return Object.keys(row).some(function(key) {
                        return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                    })
                })
            }
            if (sortKey) {
                data = data.slice().sort(function(a, b) {
                    a = a[sortKey]
                    b = b[sortKey]
                    return (a === b ? 0 : a > b ? 1 : -1) * order
                })
            }
            if (!this.showGroup) {
                return data;
            }
            //进行分组
            var groups = [];

            function getGroup(groupName) {
                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    if (group.groupName == groupName) {
                        return group;
                    }
                }
                return null;
            }
            $.each(data, function(i, row) {
                var group = getGroup(row['groupName']);
                if (!group) {
                    group = {
                        groupName: row['groupName'],
                        rows: [row],
                        startRow: i
                    };
                    groups.push(group);
                } else {
                    group.rows.push(row);
                }
            });

            return groups;
        },
        width: function() {
            var $that = $(this.$parent.$el);
            return $that.width() - 18;
        },
        conClass: function() {
            return null;
        }
    },
    filters: {
        capitalize: function(str) {
            return str.charAt(0).toUpperCase() + str.slice(1)
        }
    },
    methods: {
        updateValue: function(e) {
            var $t = $(e.target),
                key = $t.attr("tname"),
                val = $t.val();
            //如果值未变更 
            if(this.obj[key] == val){
            	return;
            }
            this.$set(this.obj, key, val);
            if(e.target.nodeName == "SELECT" && val){
        		this.$set(this.obj, key+"Name", $t.find("option[value="+val+"]").text());
        	}
            updateModdleStatue(key,val);
            this.$emit('updatevalue');
        },
        dialogClick: function(e) {
            var $t = $(e.target),
                code = $t.attr("tname"),
                dep = $t.attr("dep");
            if (dep) {
            	var outExtObj = findOutExtObj();
            	var dep = outExtObj.value || "";
               /* var data = this.obj[dep + "_data"];
                if (data && data.length == 1) {
                    dep = data[0]["eName"];
                }*/
            }
            openDialog(code, dep);
            this.selectProp = code;
            //$('body').toggleClass('page-quick-sidebar-open');
        },
        sortBy: function(key) {
            this.sortKey = key['field'];
            this.sortOrders[key['field']] = this.sortOrders[key['field']] * -1
        },
        expander: function(e) {
            var $this = $(e.target),
                $tdiv = $this.closest('div'),
                index = $tdiv.attr("group-index"),
                $that = $(this.$parent.$el);
            if ($this.hasClass('mtbox_collapse')) {
                $this.removeClass('mtbox_collapse').addClass('mtbox_expand');
                $that.find("table[group-index=" + index + "]").hide();
            } else {
                $this.removeClass('mtbox_expand').addClass('mtbox_collapse');
                $that.find("table[group-index=" + index + "]").show();
            }
        },
        dependMethod: function(dep, depVal, e) {
            if (dep && depVal) {
                var data = this.obj[dep];
                if (data) {
                    return true;
                }
                return false;
            }
            return true;
        }
    }
});

//bootstrap the demo
function initVue(options) {
    options || (options = {});

    return new Vue({
        el: '#demo',
        data: {
            obj: options.obj || {},
            options: options.options || {},
            defaultOptions: {
                scroll: true,
                width: "320px",
                height: "auto",
            },
            searchQuery: '',
            showGroup: true,
            groupField: options.groupField || "groupName",
            gridColumns: options.gridColumns || [],
            gridData: options.gridData || [],
        },
        computed: {
            width: function() {
                return this.options.width || this.defaultOptions.width;
            },
            height: function() {
                return this.options.height || this.defaultOptions.height;
            },
            scroll: function() {
                return this.options.scroll || this.defaultOptions.scroll;
            }
        },
        methods: {
            updateObj: function() {
                console.log(2);
            }
        },
        mounted: function() {
            var $that = $(this.$el);
            $that.css({
                width: this.width,
                height: this.scroll ? this.height : this.defaultOptions.height
            });
            $that.find('.mtbox_view').width($that.width() - 18);
            if (this.scroll) {
                var $container = $that.find(".mtbox_container");
                $container.height($that.height() - 23);
                $that.find(".mtbox_scroll").height($container.height() - 26);
            }

            $that.find(".mtbox_view .mtbox_content.mtbox_scroll").bind("scroll.mtbox", function(e) {
                $that.find(".mtbox_inner .mtbox_content.mtbox_scroll").scrollTop(this.scrollTop);
            });
        }
    })
}
