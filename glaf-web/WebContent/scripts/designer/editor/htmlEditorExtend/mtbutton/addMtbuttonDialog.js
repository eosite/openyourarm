UE.registerUI('mtbutton',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
            var dataRole = uiName;
            var obj = createComponent(dataRole);
            var html = ' <button id="'+obj.id+'" data-role="'+dataRole+'" template="'+obj.template+'" contenteditable="false">按钮</button>';
            editor.execCommand("inserthtml", html, true);
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:'按钮',
        title:'按钮',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: 114px 100px;',
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




// UE.registerUI('mtbutton',function(editor,uiName){

//     var me = editor;
//     editor.commands["insertmtbutton"] = {
//         execCommand: function(cmd, obj) {
//             //插入代码
//             //var html = '<img class="" src="'+contextPath+'/scripts/htmlEditorExtend/mtbutton/mtbutton.png" id="'+obj.id+'" data-role="'+obj.dataRole+'"/>';
//             //var html = '<img class="'+obj.dataRole+'" src="'+obj.imageUrl+'" id="'+obj.id+'" data-role="'+obj.dataRole+'" templateid="'+obj.templateId+'" />';
//             var html = ' <button id="'+obj.id+'" data-role="'+obj.dataRole+'" templateid="'+obj.templateId+'" template="'+obj.template+'" contenteditable="false">按钮</button>';
//             me.execCommand("inserthtml", html, true);
//         }
//     };

//     //点击弹出窗口信息
// //    var popup = new baidu.editor.ui.Popup({
// //        editor: editor,
// //        content: '',
// //        className: 'edui-bubble',
// //        _updateIframe: function() { //弹出
// //            var img = editor._iframe = popup.anchorEl;
// //            if (img.className =='mtbutton' || img.className.indexOf("mtbutton") != -1) {
// //                editor.selection.getRange().selectNode(img).select();
// //                dialog.open();
// //                popup.hide();
// //            } else {
// //                //dialog.open();
// //               // popup.hide();
// //            }
// //        }
// //    });
// //    popup.render();
// //    //增加监听事件 监听是否点击了输入框
// //    editor.addListener('click', function(t, evt) {
// //        evt = evt || window.event;
// //        var el = evt.target || evt.srcElement;
// //        if (editor.ui._dialogs.insertframeDialog && /img/ig.test(el.tagName) && (el.className == "mtbutton" || el.className.indexOf("mtbutton") != -1)) {
// //            var html = popup.formatHtml(
// //                '<nobr>' + editor.getLang("property") + ': <span class="edui-clickable">' + editor.getLang("default") 
// //                + '&nbsp;&nbsp;<br/><br/>' +
// //                ' <span onclick="$$._updateIframe( this);" class="edui-clickable">' + editor.getLang("modify") + '</span></nobr>');
// //            if (html) {
// //                popup.getDom('content').innerHTML = html;
// //                popup.anchorEl = el;
// //                popup.showAnchor(popup.anchorEl);
// //            } else {
// //                popup.hide();
// //            }
// //        }
// //    });


//     //创建dialog
//     var dialog = new UE.ui.Dialog({
//         //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
//         iframeUrl:contextPath+'/scripts/designer/editor/htmlEditorExtend/dialog.html?dataRole=mtbutton',
//         //需要指定当前的编辑器实例
//         editor:editor,
//         //指定dialog的名字
//         name:uiName,
//         //dialog的标题
//         title:"选择mtbutton模板",

//         //指定dialog的外围样式
//         cssRules:"width:600px;height:300px;"

//         //如果给出了buttons就代表dialog有确定和取消
//         // buttons:[
//         //     {
//         //         className:'edui-okbutton',
//         //         label:'确定',
//         //         onclick:function () {
//         //             dialog.close(true);
//         //         }
//         //     },
//         //     {
//         //         className:'edui-cancelbutton',
//         //         label:'取消',
//         //         onclick:function () {
//         //             dialog.close(false);
//         //         }
//         //     }
//         // ]
//     });

//     //参考addCustomizeButton.js
//     var btn = new UE.ui.Button({
//         name:'按钮',
//         title:'按钮',
//         //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
//         cssRules :'background-position: 114px 100px;',
//         onclick:function () {
//             //渲染dialog
//             dialog.render();
//             dialog.open();
//         }
//     });

//     return btn;
// }/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);