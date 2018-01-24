/*
@license

dhtmlxGantt v.4.2.1 Professional
This software is covered by DHTMLX Enterprise License. Usage without proper license is prohibited.

(c) Dinamenta, UAB.
*/
Gantt.plugin(function(t){t._fs_change=[];var e=t.getState;t.getState=function(){var t=e.apply(this,arguments);return t.fullscreen=!!this._expanded,t},t._onFullScreenChange=function(){if(t.$container){var e=t.getState().fullscreen;e&&(t._isFullScreenActive()||t.collapse());var i=t._fs_change.length?t._fs_change.pop():null;if(t._expanded=!t._expanded,i)if(i.condition())i.callback();else{var n=setInterval(function(){i.condition()&&(clearInterval(n),n=null,i.callback())},10);setTimeout(function(){n&&(clearInterval(n),
i.callback())},100)}else t.render()}},t._isFullScreenActive=function(){return document.fullscreenElement||document.mozFullScreenElement||document.webkitFullscreenElement||document.msFullscreenElement},t._isFullScreenAvailable=function(){return document.fullscreenEnabled||document.webkitFullscreenEnabled||document.mozFullScreenEnabled||document.msFullscreenEnabled},t.event(document,"webkitfullscreenchange",t._onFullScreenChange),t.event(document,"mozfullscreenchange",t._onFullScreenChange),t.event(document,"MSFullscreenChange",t._onFullScreenChange),
t.event(document,"fullscreenChange",t._onFullScreenChange),t.event(document,"fullscreenchange",t._onFullScreenChange),t.expand=function(){if(t.callEvent("onBeforeExpand",[])){t._toggleFullScreen(!0);var e=t._obj;do e._position=e.style.position||"",e.style.position="static";while((e=e.parentNode)&&e.style);e=t._obj,e.style.position="absolute",e._width=e.style.width,e._height=e.style.height,e.style.width=e.style.height="100%",e.style.top=e.style.left="0px";var i=document.body;i.scrollTop=0,i=i.parentNode,
i&&(i.scrollTop=0),document.body._overflow=document.body.style.overflow||"",document.body.style.overflow="hidden",document.body._width=document.body.style.width,document.body._height=document.body.style.height;var n=function(){document.documentElement.msRequestFullscreen&&t._obj&&(t._obj.style.width=document.body.style.width=window.outerWidth+"px",t._obj.style.height=document.body.style.height=window.outerHeight+"px"),t.render(),t.callEvent("onExpand",[])};if(t._isFullScreenAvailable()){var a=window.outerHeight,r=window.outerWidth;
t._fs_change.push({condition:function(){return a<window.outerHeight&&r<=window.outerWidth},callback:n})}else t._expanded=!t._expanded,n()}},t.collapse=function(){if(t.callEvent("onBeforeCollapse",[])){var e=t._obj;do e.style.position=e._position;while((e=e.parentNode)&&e.style);e=t._obj,e.style.width=e._width,e.style.height=e._height,document.body.style.overflow=document.body._overflow,document.body.style.width=document.body._width,document.body.style.height=document.body._height,t._toggleFullScreen(!1);
var i=function(){t.render(),t.callEvent("onCollapse",[])};if(t._isFullScreenAvailable()){var n=window.outerHeight,a=window.outerWidth;t._fs_change.push({condition:function(){return n>window.outerHeight&&a>=window.outerWidth},callback:i})}else t._expanded=!t._expanded,i()}},t._toggleFullScreen=function(t){!t&&(document.fullscreenElement||document.mozFullScreenElement||document.webkitFullscreenElement||document.msFullscreenElement)?document.exitFullscreen?document.exitFullscreen():document.msExitFullscreen?document.msExitFullscreen():document.mozCancelFullScreen?document.mozCancelFullScreen():document.webkitExitFullscreen&&document.webkitExitFullscreen():document.documentElement.requestFullscreen?document.documentElement.requestFullscreen():document.documentElement.msRequestFullscreen?document.documentElement.msRequestFullscreen():document.documentElement.mozRequestFullScreen?document.documentElement.mozRequestFullScreen():document.documentElement.webkitRequestFullscreen&&document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
}});
//# sourceMappingURL=../sources/ext/dhtmlxgantt_fullscreen.js.map