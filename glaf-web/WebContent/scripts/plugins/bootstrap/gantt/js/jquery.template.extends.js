$.fn.loadGanttTemplates = function() {
  $.GanttTemplate.loadTemplates($(this));
  $.GanttTemplate.convertTemplates($(this));
  return this;
};

$.GanttTemplate = {
  _templates: new Object(),
  _decorators:new Object(),

  loadTemplates: function(elems) {
    elems.each(function() {
      $(this).find(".__template__").each(function() {
        var tmpl = $(this);
        var type = tmpl.attr("type");

        //template may be inside <!-- ... --> or not in case of ajax loaded templates
        var found=false;
        var el=tmpl.get(0).firstChild;
        while (el && !found) {
          if (el.nodeType == 8) { // 8==comment
            var templateBody = el.nodeValue; // this is inside the comment
            found=true;
            break;
          }
          el=el.nextSibling;
        }
        if (!found)
          var templateBody = tmpl.html(); // this is the whole template

        $.GanttTemplate._templates[type] = function(){
          return templateBody;
        }

      });
    });
  },

  convertTemplates: function(elems) {
    elems.each(function() {
      var templates = $.GanttTemplate._templates;
      $(this).find(".__template__").each(function() {
        var tmpl = $(this);
        var type = tmpl.attr("type");
        tmpl.empty();
        tmpl.html("<!--"+ArtTemplateDataUtils.convertOne({columns: gantt_columns || [],canWrite:canWrite},templates[type]())+"-->");
      });
    });
  },

  // asynchronous
  ajaxLoadAsynchTemplates: function(templateUrl, callback) {

    $.get(templateUrl, function(data) {

      var div = $("<div>");
      div.html(data);

      $.GanttTemplate.loadTemplates(div);

      if (typeof(callback == "function"))
        callback();
    },"html");
  },

  ajaxLoadTemplates: function(templateUrl) {
    $.ajax({
      async:false,
      url: templateUrl,
      dataType: "html",
      success: function(data) {
        var div = $("<div>");
        div.html(data);
        $.GanttTemplate.loadTemplates(div);
      }
    });

  }

};
