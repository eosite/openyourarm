var textboxFunc = {
	getValue: function(rule, args) {
		var $dom = pubsub.getJQObj(rule, true);
		return $dom.val() || $dom.attr("defaultval");
	},
	setValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	memorySetValue: function(rule, args) {
		var $id = ( pubsub.getThat(rule) && pubsub.getThat(rule).memoryObj ) || pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("memorySetValue", "memorySetValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	setValueByName: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "",
			columnname = $id.attr("columnname"),
			name = $id.attr("name"),
			$target = $id.parents("body").find("[" + (columnname ? "columnname" : "name") + "=" + (columnname ? columnname : name) + "]");
		$target.attr("setValueByName", "setValueByName");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$target.val(v);
		} else {
			$target.val(args);
		}
	},
	appendSetValue: function(rule, args) {
		var $id = pubsub.getJQObj(rule),
			r, v = "",
			bval = $id.val() ? $id.val() + "," : "";
		$id.attr("setValue", "setValue");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(bval + v);
		} else {
			$id.val(bval + args);
		}
	},
	setValueByDynamic: function(rule, args) { //变长区动态公式
		var $id = pubsub.getJQObj(rule),
			r, v = "";
		$id.attr("setValueByDynamic", "setValueByDynamic");
		if (typeof args == "object") {
			for (var i = 0; i < rule.length; i++) {
				r = rule[i];
				v = args[r.param];
			}
			$id.val(v);
		} else {
			$id.val(args);
		}
	},
	tclear: function(rule, args) {
		pubsub.getJQObj(rule).attr("tclear", "tclear").val("");
	},
	cellCriterion: function(rule, args) {
		var $this = pubsub.getJQObj(rule);
		console.log(rule);
		if (args) {
			switch (args.type) {
				case "cri":

					break;
				case "cri_params":

					break;
				case "stat":

					break;
				default:
					break;
			}
		}
	}
};
pubsub.sub("textbox", textboxFunc);