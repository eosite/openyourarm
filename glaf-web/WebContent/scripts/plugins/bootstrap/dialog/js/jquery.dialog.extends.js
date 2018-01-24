/**
 * 
 * 
 * 
 * BootstrapDialog.show({
		title : '选择',
		size : BootstrapDialog.SIZE_WIDE,
		message : "<iframe id='23'  width='100%' height='610' src='' frameborder='no' scrolling='no'></iframe>",
		buttons : [
		     {
		    	label: 'OK!',
                cssClass: 'btn-success',
                action: function(d){
                	
                }
		     }, 
		     {
		    	label: 'Close!',
                cssClass: 'btn-warning',
                action: function(d){
                	d.close();
                }
		     }
		],
		css : {
			height : '500',
			width : $(window).width() * 0.8
		},
		modal : false //遮蔽层
	});
	
	BootstrapDialog
 */
(function($, BootstrapDialog) {

	BootstrapDialog.prototype.setModalDialog = function($modalDialog) {
		this.$modalDialog = $modalDialog;
		if (this.$modalDialog && this.defaultOptions && this.defaultOptions.css) {
			this.$modalDialog.css(this.defaultOptions.css);
		}
		return this;
	};
	
	BootstrapDialog.prototype.open = function() {
		!this.isRealized() && this.realize();
        this.getModal().modal('show');
        if (this.defaultOptions.modal == false) {
        	var $backdrop = this.$modal.data("bs.modal").$backdrop;
			!!$backdrop[0] && $backdrop.hide();
		}
        return this;
	};


    /**
     * Alert window
     *
     * @returns the created dialog instance
     */
    BootstrapDialog.alert = function () {
        var alertOptions = {};
        var defaultAlertOptions = {
            type: BootstrapDialog.TYPE_PRIMARY,
            title: "提示信息：",
            message: null,
            closable: false,
            draggable: false,
            buttonLabel: BootstrapDialog.DEFAULT_TEXTS.OK,
            callback: null
        };

        if (typeof arguments[0] === 'object' && arguments[0].constructor === {}.constructor) {
            alertOptions = $.extend(true, defaultAlertOptions, arguments[0]);
        } else {
            alertOptions = $.extend(true, defaultAlertOptions, {
                message: arguments[0],
                callback: typeof arguments[1] !== 'undefined' ? arguments[1] : null
            });
        }

        var dialog = null;
        defaultAlertOptions.onshow = function(){
            dialog.getModalHeader().css("padding","5px");
            dialog.$modalBody.find(".bootstrap-dialog-message").css("word-break","break-word");
        };

        dialog = new BootstrapDialog(alertOptions);
        dialog.setData('callback', alertOptions.callback);
        dialog.addButton({
            label: alertOptions.buttonLabel,
            action: function (dialog) {
                if (typeof dialog.getData('callback') === 'function' && dialog.getData('callback').call(this, true) === false) {
                    return false;
                }
                dialog.setData('btnClicked', true);

                return dialog.close();
            }
        });
        if (typeof dialog.options.onhide === 'function') {
            dialog.onHide(function (dialog) {
                var hideIt = true;
                if (!dialog.getData('btnClicked') && dialog.isClosable() && typeof dialog.getData('callback') === 'function') {
                    hideIt = dialog.getData('callback')(false);
                }
                if (hideIt === false) {
                    return false;
                }
                hideIt = this.onhide(dialog);

                return hideIt;
            }.bind({
                onhide: dialog.options.onhide
            }));
        } else {
            dialog.onHide(function (dialog) {
                var hideIt = true;
                if (!dialog.getData('btnClicked') && dialog.isClosable() && typeof dialog.getData('callback') === 'function') {
                    hideIt = dialog.getData('callback')(false);
                }

                return hideIt;
            });
        }

        return dialog.open();
    };
	
	// BootstrapDialog.confirm2 = BootstrapDialog.confirm;

	BootstrapDialog.confirm2 = function () {
        var confirmOptions = {};
        var defaultConfirmOptions = {
            type: BootstrapDialog.TYPE_PRIMARY,
            title: null,
            message: null,
            closable: false,
            draggable: false,
            btnCancelLabel: BootstrapDialog.DEFAULT_TEXTS.CANCEL,
            btnCancelClass: null,
            btnOKLabel: BootstrapDialog.DEFAULT_TEXTS.OK,
            btnOKClass: null,
            callback: null
        };
        if (typeof arguments[0] === 'object' && arguments[0].constructor === {}.constructor) {
            confirmOptions = $.extend(true, defaultConfirmOptions, arguments[0]);
        } else {
            confirmOptions = $.extend(true, defaultConfirmOptions, {
                message: arguments[0],
                callback: typeof arguments[1] !== 'undefined' ? arguments[1] : null
            });
        }
        if (confirmOptions.btnOKClass === null) {
            confirmOptions.btnOKClass = ['btn', confirmOptions.type.split('-')[1]].join('-');
        }

        var dialog = null;
        confirmOptions.onshow = function(){
            dialog.getModalHeader().css("padding","5px");
        };

        dialog = new BootstrapDialog(confirmOptions);
        dialog.setData('callback', confirmOptions.callback);
        dialog.addButton({
            label: confirmOptions.btnOKLabel,
            cssClass: confirmOptions.btnOKClass,
            action: function (dialog) {
                if (typeof dialog.getData('callback') === 'function' && dialog.getData('callback').call(this, true) === false) {
                    return false;
                }

                return dialog.close();
            }
        });
        dialog.addButton({
            label: confirmOptions.btnCancelLabel,
            cssClass: confirmOptions.btnCancelClass,
            action: function (dialog) {
                if (typeof dialog.getData('callback') === 'function' && dialog.getData('callback').call(this, false) === false) {
                    return false;
                }

                return dialog.close();
            }
        });

        return dialog.open();

    };
	delete BootstrapDialog.confirm;
	
	window.$alert = window.alert;
	
	$.extend(true, window, BootstrapDialog);
	
})(jQuery, window.BootstrapDialog || {});

