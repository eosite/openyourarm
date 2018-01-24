define([
    "dojo/Evented",
    "dojo/parser",
    "dojo/on",
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/_base/array",
    "dojo/dom-style",
    "dojo/_base/lang",
    "dojo/dom-class",
    "dojo/fx/Toggler",
    "dojo/fx",
    "dojo/Deferred",
    "esri/domUtils",
    "esri/InfoWindowBase"

  ],
  function(
    Evented,
    parser,
    on,
    declare,
    domConstruct,
    array,
    domStyle,
    lang,
    domClass,
    Toggler,
    coreFx,
    Deferred,
    domUtils,
    InfoWindowBase
  ) {
    return declare([InfoWindowBase, Evented], {

      isContentShowing: false,

      constructor: function(parameters) {


        lang.mixin(this, parameters);


        domClass.add(this.domNode, "myInfoWindow");

        this._closeButton = domConstruct.create("div", {
          "class": "infowindowclose",
          "title": "Close"
        }, this.domNode);
        this._title = domConstruct.create("div", {
          "class": "title"
        }, this.domNode);
        this._content = domConstruct.create("div", {
          "class": "content"
        }, this.domNode);
        this._hook = domConstruct.create("div", {
          "class": "hook"
        }, this.domNode);
        this._hook_top = domConstruct.create("div", {
          "class": "hook_top"
        }, this.domNode);
        this._hook_bottom = domConstruct.create("div", {
          "class": "hook_bottom"
        }, this.domNode);

        /*var toggler = new  Toggler({
          "node": this._content,
          showFunc: coreFx.wipeIn,
          hideFunc: coreFx.wipeOut
        });*/
        //toggler.hide();

        on(this._closeButton, "click", lang.hitch(this, function() {
          //hide the content when the info window is toggled close.
          this.hide();
          if (this.isContentShowing) {
            //toggler.hide();
            this.isContentShowing = false;
            //domClass.remove(this._toggleButton);
            //domClass.add(this._toggleButton, "toggleOpen");
          }
        }));
        /* on(this._toggleButton, "click", lang.hitch(this, function(){
           //animate the content display 
             if(this.isContentShowing){
  
               toggler.hide();
               this.isContentShowing = false;
               domClass.remove(this._toggleButton);
               domClass.add(this._toggleButton,"toggleOpen");

             }else{
               toggler.show();
               this.isContentShowing=true;
               domClass.remove(this._toggleButton);
               domClass.add(this._toggleButton,"toggleClose");  
             }

         }));*/
        //hide initial display 
        domUtils.hide(this.domNode);
        this.isShowing = false;

      },
      setMap: function(map) {
        this.inherited(arguments);
        //domConstruct.place(this.domNode, map.root)
        map.on("pan-start", lang.hitch(this, function(e, r, t) {
          //console.log(e);
          // this.show(e.extent);
          //this.hide();
        }));
        map.on("pan-end", lang.hitch(this, function(e, r, t) {
          //console.log(e);
          // this.show(e.delta);
          //this.hide();
        }));
         map.on("zoom-start", lang.hitch(this, function(e) {
          //if (this.isContentShowing)
            this.showProp = this.isContentShowing
            //this.show(this.mapCoords || this.coords);
          //this.move(e.extent);
          //this.onShow();
        }));
        map.on("zoom-end", lang.hitch(this, function(e) {
          if (this.showProp)
            this.show(this.mapCoords || this.coords);
          //this.move(e.extent);
          //this.onShow();
        }));
        // map.on("zoom-start", //this, this.hide);

      },
      setTitle: function(title) {
        this.place(title, this._title);

      },
      setContent: function(content) {
        this.place(content, this._content);
      },
      show: function(location) {
        if (location.spatialReference) {
          this.mapCoords = location;
          this.coords = this.map.toScreen(location, !0)
          location = this.map.toScreen(location);
        } else {
          this.mapCoords = null;
          this.coords = location;
        }
        this.mapOffsetTop = this.map.container.offsetTop;
        this.mapOffsetLeft = this.map.container.offsetLeft;
        this.offsetX = 95.5 + this.mapOffsetLeft;
        this.offsetY = domStyle.get(this.domNode, "height") + 33 - this.mapOffsetTop;
        //Position 10x10 pixels away from the specified location
        domStyle.set(this.domNode, {
          "left": (location.x + this.offsetX) + "px",
          "top": (location.y - this.offsetY) + "px"
        });
        this.isContentShowing = true;
        //display the info window
        domUtils.show(this.domNode);
        this.isShowing = true;
        this.onShow();
      },
      hide: function() {
        this.isContentShowing = false;
        domUtils.hide(this.domNode);
        this.isShowing = false;
        this.onHide();
      },
      resize: function(width, height) {
        domStyle.set(this._content, {
          "width": width + "px",
          "height": height + "px"
        });
        domStyle.set(this._title, {
          "width": width + "px"
        });
         domUtils.show(this.domNode);
      },
      destroy: function() {
        domConstruct.destroy(this.domNode);
        this._closeButton = this._title = this._content = null;

      },
      onShow: function() {
        this.__registerMapListeners();
        this.startupDijits(this._title);
        this.startupDijits(this._content)
      },
      onHide: function() {
        this.__unregisterMapListeners()
      },
      move: function(a, b) {
        b ? a = this.coords.offset(a.x, a.y) : (this.coords = a, this.mapCoords && (this.mapCoords = this.map.toMap(a)));
        domStyle.set(this.domNode, {
          left: (Math.round(a.x) + this.offsetX) + "px",
          top: (Math.round(a.y) - this.offsetY) + "px"
        })
      },


    });

  });