var dropDownEditor = kendo.ui.DropDownList.extend({
    options: {
        name: 'dropDownEditor'
    },
    init: function (element, options) {
        var gridOptions = options.gridOptions;
        options.value = gridOptions.model.get(gridOptions.field);
        options.select = function (e) {
            this.select(function (dataItem) {
                return dataItem.text != e.item.text();
            });
        }
        kendo.ui.DropDownList.fn.init.call(this, element,options);
        if (gridOptions.model.get(gridOptions.field) == "") {        // 让进入Editor的时候显示cell中的值
            this.text("");
        }
    }
});
kendo.ui.plugin(dropDownEditor);