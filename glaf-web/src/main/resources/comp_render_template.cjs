var compRenderMethod={
      render:function(id,componentId){
	    if(compRenderMethod[componentId])
	    compRenderMethod[componentId].call(this,id);
	 }
<#list templateRenderList as templateRender>
	<#if templateRender?exists && templateRender.template?exists &&templateRender.template?trim!="" >
		 ,${templateRender.componentId}:function(id){
			   ${templateRender.template}
		 }
	</#if>
</#list>
};