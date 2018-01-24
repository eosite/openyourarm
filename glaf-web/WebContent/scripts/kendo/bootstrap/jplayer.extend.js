var jplayerFunc = {		
    //接收文件信息
	acceptMessage : function(id, args){
	   var $id = pubsub.getJQObj(id[0].outid, true),
	       build = $id.data("jp"),
	       content = $id.data("jplayer").target,
	       config = {},
	       para = []; 
	   /*
	    config = {	   
		        title : arg['title'],
				mp3 : arg['value'],
				ogg : arg['value'],
				wav : arg['value']
		 }
		
	   build.add(config);*/
	   var config = new jPlayerPlaylist({
			jPlayer: "#jquery_jplayer_2",
			cssSelectorAncestor: "#jp_container_2"
		}, [], {
			swfPath: "js/jplayer",
			supplied: "oga, mp3,wav",
			wmode: "window",
			useStateClassSkin: true,
			autoBlur: false,
			smoothPlayBar: true,
			keyEnabled: true
		});
	   var data = {	   
		        title : args['title'],
				mp3 : contextPath + args['value'],
				ogg : contextPath + args['value'],
				wav : contextPath + args['value']
		 }
	   config.add(data);
	},
	//接收文件信息
	linkageControl : function(id, args){
		var $id = pubsub.getJQObj(id.outid, true),
	       build = $id.data("jplayer");
	    build.target.find("#jquery_jplayer_2").jPlayer("clearMedia");
	    build.target.find("ul").empty();
		$.ajax({
			url : build.option.read.url,
			async : build.option.read.async,
			data : JSON.stringify(build.option.read.data),
			type : 'post',
			dataType : 'JSON',
			contentType : "application/json",
			success : function(ret) {
				
			    var data = build._convertData(ret[0].data,build.option)
				$.each(data,function(i,v){
					build.option.config.add(v);
				});  
			}
	    });
	},
	refresh : function(id, args){
		   var $id = pubsub.getJQObj(id.outid, true),
		       build = $id.data("jplayer");
		    build.target.find("#jquery_jplayer_2").jPlayer("clearMedia");
		    build.target.find("ul").empty();
			$.ajax({
				url : build.option.read.url,
				async : build.option.read.async,
				data : JSON.stringify(build.option.read.data),
				type : 'post',
				dataType : 'JSON',
				contentType : "application/json",
				success : function(ret) {
					
				    var data = build._convertData(ret[0].data,build.option)
					$.each(data,function(i,v){
						build.option.config.add(v);
					});  
				}
		    });
    
		},
	
};
pubsub.sub("jplayer", jplayerFunc);
