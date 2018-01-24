jQuery(function() {
    jQuery("#grid").kendoGrid({
        "columnMenu": true,
        "dataSource": {
            "schema": {
                "total": "total",
                "model": {
                    "fields": {
                        "shipCity": {
                            "type": "string"
                        },
                        "shipName": {
                            "type": "string"
                        },
                        "shipCountry": {
                            "type": "string"
                        },
                        "shipAddress": {
                            "type": "string"
                        },
                        "orderId": {
                            "type": "number"
                        }
                    }
                },
                "data": "data",
                "groups": "data"
            },
            "serverFiltering": true,
            "transport": {
                "parameterMap": function(options) {
                    return JSON.stringify(options);
                },
                "read": {
                    "contentType": "application/json",
                    "type": "POST",
                    "url": "/kendouijsp/grid/frozen-columns/read"
                }
            },
            "serverSorting": true,
            "pageSize": 50.0,
            "serverPaging": true,
            "serverGrouping": true
        },
        "height": "430px",
        "reorderable": true,
        "filterable": true,
        "sortable": true,
        "pageable": true,
        "columns": [{
            "field": "orderId",
            "title": "Order ID",
            "width": "120px",
            "lockable": false,
            "locked": true
        },
        {
            "field": "shipCountry",
            "title": "Ship Country",
            "width": "200px"
        },
        {
            "field": "shipCity",
            "title": "Ship City",
            "width": "160px"
        },
        {
            "field": "shipName",
            "title": "Ship Name",
            "width": "200px",
            "locked": true
        },
        {
            "field": "shipAddress",
            "title": "Ship Address",
            "width": "300px",
            "lockable": false
        }],
        "scrollable": {},
        "resizable": true,
        "groupable": true
    });
})