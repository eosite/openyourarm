function openDialog(e)
		{
		    var ahtml=$(e.target).closest("a");
	        var dialogHeight=ahtml.attr("dialogHeight");
		    var dialogWidth=ahtml.attr("dialogWidth");
		    var url=ahtml.attr("ahref");
		    var title=ahtml.text;
			//获取tgt
			var tgt=getCookie('TGT');
			if(tgt!=null){
				if(url.indexOf("?")>0){
					url=url+"&TGT="+tgt;
				}else{
					url=url+"?TGT="+tgt;
				}
			}
		    var iTop = (window.screen.availHeight-30-dialogHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-dialogWidth)/2; //获得窗口的水平位置;
		    var option="height="+dialogHeight+",width="+dialogWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no";
		    window.open(url,title,option);
		}
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (prefix){
    return this.slice(0, prefix.length) === prefix;
  };
}
if (typeof String.prototype.endsWith != 'function') {
  String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
  };
}
function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1 
    c_end=document.cookie.indexOf(";",c_start)
    if (c_end==-1) c_end=document.cookie.length
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
return ""
}