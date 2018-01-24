


  function dateToRelative(localTime){
    var diff=new Date().getTime()-localTime;
    var ret="";

    var min=60000;
    var hour=3600000;
    var day=86400000;
    var wee=604800000;
    var mon=2629800000;
    var yea=31557600000;

    if (diff<-yea*2)
      ret ="in ## years".replace("##",(-diff/yea).toFixed(0));

    else if (diff<-mon*9)
      ret ="in ## months".replace("##",(-diff/mon).toFixed(0));

    else if (diff<-wee*5)
      ret ="in ## weeks".replace("##",(-diff/wee).toFixed(0));

    else if (diff<-day*2)
      ret ="in ## days".replace("##",(-diff/day).toFixed(0));

    else if (diff<-hour)
      ret ="in ## hours".replace("##",(-diff/hour).toFixed(0));

    else if (diff<-min*35)
      ret ="in about one hour";

    else if (diff<-min*25)
      ret ="in about half hour";

    else if (diff<-min*10)
      ret ="in some minutes";

    else if (diff<-min*2)
      ret ="in few minutes";

    else if (diff<=min)
      ret ="just now";

    else if (diff<=min*5)
      ret ="few minutes ago";

    else if (diff<=min*15)
      ret ="some minutes ago";

    else if (diff<=min*35)
      ret ="about half hour ago";

    else if (diff<=min*75)
      ret ="about an hour ago";

    else if (diff<=hour*5)
      ret ="few hours ago";

    else if (diff<=hour*24)
      ret ="## hours ago".replace("##",(diff/hour).toFixed(0));

    else if (diff<=day*7)
      ret ="## days ago".replace("##",(diff/day).toFixed(0));

    else if (diff<=wee*5)
      ret ="## weeks ago".replace("##",(diff/wee).toFixed(0));

    else if (diff<=mon*12)
      ret ="## months ago".replace("##",(diff/mon).toFixed(0));

    else
      ret ="## years ago".replace("##",(diff/yea).toFixed(0));

    return ret;
  }

  //override date format i18n
  
  Date.monthNames = ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
  // Month abbreviations. Change this for local month names
  Date.monthAbbreviations = ["一","二","三","四","五","六","七","八","九","十","十一","十二"];
  // Full day names. Change this for local month names
  Date.dayNames =["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
  // Day abbreviations. Change this for local month names
  Date.dayAbbreviations = ["日","一","二","三","四","五","六"];
  // Used for parsing ambiguous dates like 1/2/2000 - default to preferring 'American' format meaning Jan 2.
  // Set to false to prefer 'European' format meaning Feb 1
  Date.preferAmericanFormat = false;

  Date.firstDayOfWeek =1;
  Date.defaultFormat = "dd/MM/yyyy";


  Number.decimalSeparator = ".";
  Number.groupingSeparator = ",";
  Number.minusSign = "-";
  Number.currencyFormat = "##0.00";



  var millisInWorkingDay =36000000;
  var workingDaysPerWeek =5;

  function isHoliday(date) {
    //window.isHoly5 = false;
    //window.isHoly6 = false;
    //window.isHoly7 = false;
    //window.dholidays = "";
    var friIsHoly = false;
    var satIsHoly = true;
    var sunIsHoly = true;

    if(typeof window.isHoly5 === "boolean"){
      friIsHoly = window.isHoly5;
    }
    if(typeof window.isHoly6 === "boolean"){
      satIsHoly = window.isHoly6;
    }
    if(typeof window.isHoly7 === "boolean"){
      sunIsHoly = window.isHoly7;
    }

    pad = function (val) {
      val = "0" + val;
      return val.substr(val.length - 2);
    };

    var holidays = "#01_01#04_25#08_15#11_01#12_25#12_26#06_02#12_08#05_01#2010_04_05#2010_10_19#2010_05_15#2011_04_04#";
    if(typeof window.dholidays === "string"){
      holidays = window.dholidays;
    }

    var ymd = "#" + date.getFullYear() + "_" + pad(date.getMonth() + 1) + "_" + pad(date.getDate()) + "#";
    var md = "#" + pad(date.getMonth() + 1) + "_" + pad(date.getDate()) + "#";
    var day = date.getDay();

    return  (day == 5 && friIsHoly) || (day == 6 && satIsHoly) || (day == 0 && sunIsHoly) || holidays.indexOf(ymd) > -1 || holidays.indexOf(md) > -1;
  }


  
  var i18n = {
    FORM_IS_CHANGED:"You have some unsaved data on the page!",
    YES:"yes",
    NO:"no",
    FLD_CONFIRM_DELETE:"confirm the deletion?",
    INVALID_DATA:"The data inserted are invalid for the field format.",
    ERROR_ON_FIELD:"Error on field",
    CLOSE_ALL_CONTAINERS:"close all?",



    DO_YOU_CONFIRM:"Do you confirm?"
  };

  