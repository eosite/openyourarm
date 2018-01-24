UE.registerUI('col-md-12',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
            var dataRole = uiName;
            var obj = createComponent(dataRole);
            var html = ' <input type="text" id="'+obj.id+'" data-role="'+dataRole+'" template="'+obj.template+'" placeholder="栅格"/>';
            editor.execCommand("inserthtml", html, true);
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:'栅格',
        title:'栅格',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -580px -20px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);
