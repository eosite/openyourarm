//初始化属性值
var autocompleteAttr = new Object();
autocompleteAttr.type = "autocomplete" ; 
autocompleteAttr.attr = {"data-placeholder":"请输入提示","data-text-field":"ProductName",
		"data-bind":"value: selectedProduct,source: products,visible: isVisible,enabled: isEnabled"} ;
attributeInit.push(autocompleteAttr);
//属性值对应中文名,如果属性值没有对应中文名，则不显示
var acCNName = {"data-placeholder":"提示语"};
cnMap.put(autocompleteAttr.type,acCNName);
//动态执行方法---元素创建区(方法名必须和template和type命名一致) 
function autocomplete(ev){
	var createEle = document.createElement("input");
	createEle.setAttribute("type","text");
	//绑定id、data-role、draggable、ondragstart和坐标绑定
	fixEleAttr(createEle,ev,autocompleteAttr);
}
//动态执行方法---kendoui渲染区域