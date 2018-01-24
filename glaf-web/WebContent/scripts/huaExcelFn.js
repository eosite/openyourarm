/**
 * [hef_Abs 返回参数的绝对值]
 * @param  {[type]} number [description]
 * @return {[type]}        [description]
 */
function hef_Abs(number){
	if(number<0){
		return 0-number;
	}
	return number;
}
/**
 * [getCNCurrency 中文货币单位]
 * @return {[type]} [description]
 */
function getCNCurrency(){
	if(source=='yuan'){
		return '\u5143';//元
	}else if(source=='jiao'){
		return '\u89d2';//角
	}else if(source=='fen'){
		return '\u5206';//分
	}else if(source=='qianyuan'){
		return '\u5343\u5143';//千元
	}else{
		return '';
	}
}
/**
 * [getCNTime 时间]
 * @param  {[type]} source [description]
 * @return {[type]}        [description]
 */
function getCNTime(source){
	if(source=='y'){
		return '\u5e74';//年
	}else if(source=='m'){
		return '\u6708';//月
	}else if(source=='d'){
		return '\u65e5';//日
	}else if(source=='h'){
		return '\u65f6';//时
	}else if(source=='mi'){
		return '\u5206';//分
	}else if(source=='s'){
		return '\u79d2';//秒
	}else{
		return '';
	}
}
/**
 * [upperCaseCNNum description]
 * @param  {[type]} source [一位阿拉伯数字或固定多位数字]
 * @return {[type]}        [description]
 */
function upperCaseCNNum(source){
	if(source=='.'){
		return '\u70b9';//点
	}else if(source=='0'){
		return '\u96f6';//零
	}else if(source=='1'){
		return '\u58f9';//壹
	}else if(source=='2'){
		return '\u8d30';//贰
	}else if(source=='3'){
		return '\u53c1';//叁
	}else if(source=='4'){
		return '\u8086';//肆
	}else if(source=='5'){
		return '\u4f0d';//伍
	}else if(source=='6'){
		return '\u9646';//陆
	}else if(source=='7'){
		return '\u67d2';//柒
	}else if(source=='8'){
		return '\u634c';//捌
	}else if(source=='9'){
		return '\u7396';//玖
	}else if(source=='10'){
		return '\u62fe';//拾
	}else if(source=='100'){
		return '\u4f70';//佰
	}else if(source=='1000'){
		return '\u4edf';//仟
	}else if(source=='10000'){
		return '\u4e07';//万
	}else if(source=='100000000'){
		return '\u4ebf';//亿
	}else{
		return '';
	}
}
function numToCNNum(source){
	source = source.replace(/[.]/g,upperCaseCNNum('.'));
	source = source.replace(/[0]/g,upperCaseCNNum('0'));
	source = source.replace(/[1]/g,upperCaseCNNum('1'));
	source = source.replace(/[2]/g,upperCaseCNNum('2'));
	source = source.replace(/[3]/g,upperCaseCNNum('3'));
	source = source.replace(/[4]/g,upperCaseCNNum('4'));
	source = source.replace(/[5]/g,upperCaseCNNum('5'));
	source = source.replace(/[6]/g,upperCaseCNNum('6'));
	source = source.replace(/[7]/g,upperCaseCNNum('7'));
	source = source.replace(/[8]/g,upperCaseCNNum('8'));
	source = source.replace(/[9]/g,upperCaseCNNum('9'));
	return source;
}
/**
 * [hef_CNNum 返回中文小写或大写格式]
 * @param  {[number]} source [原字符串]
 * @param  {[type]} style  [格式：0为小写，1为大写]
 * @return {[type]}        [description]
 */
function hef_CNNum(source, style){
	if(style==0){
		return source;
	}
	var cnStr = '';
	var leftStr = source.substring(0,source.indexOf('.'));
	var rightStr = source.substring(source.indexOf('.'));
	var k = 0;
	var unit = '';
	for(i=leftStr.length-1;i>=0;i--){
		unit = '';
		k++;
		if(k==5){
			unit = upperCaseCNNum('10000');//万
		}else if(k==9){
			unit = upperCaseCNNum('100000000');//亿
		}else if(k%4==2){
			unit = upperCaseCNNum('10');//拾
		}else if(k%4==3){
			unit = upperCaseCNNum('100');//佰
		}else if(k%4==0){
			unit = upperCaseCNNum('1000');//仟
		}
		cnStr = source.substring(i,i+1)+unit+cnStr;
	}
	cnStr += rightStr;
	cnStr = numToCNNum(cnStr);
	return cnStr;
}
/**
 * [hef_Digit 返回指定数字的某位上的数]
 * @param  {[type]} num1 [指定数字]
 * @param  {[type]} num2 [数字的哪个位，1指个位，2指十位，-1指分位，依此类推，有效范围[-6,9]]
 * @return {[type]}      [description]
 */
function hef_Digit( num1, num2 ){
	num1 += '';
	var dotId = num1.indexOf('.');
	var digitId = -1;
	if(num2>=-6 && num2<0){
		digitId = dotId+(0-num2);
	}else if(num2>0 && num2<=9){
		digitId = dotId-num2;
	}
	return num1.substring(digitId,digitId+1);
}
/**
 * [hef_Sin 返回给定值的正弦值]
 * @param  {[type]} number [弧度]
 * @return {[type]}        [description]
 */
function hef_Sin(number){
	return Math.sin(number);
}
/**
 * [hef_Cos 返回给定值的余弦值]
 * @param  {[type]} number [弧度]
 * @return {[type]}        [description]
 */
function hef_Cos(number){
	return Math.cos(number);
}
/**
 * [hef_Tan 返回给定值的正切值]
 * @param  {[type]} number [弧度]
 * @return {[type]}        [description]
 */
function hef_Tan(number){
	return Math.atan(number);
}
/**
 * [hef_Ctan 返回给定值的余切值]
 * @param  {[type]} number [弧度]
 * @return {[type]}        [description]
 */
function hef_Ctan(number){
	return 1/Math.atan(number);
}
/**
 * [hef_Log10 返回一个数以10为底的对数]
 * @param  {[type]} number [description]
 * @return {[type]}        [description]
 */
function hef_Log10( number ){
	return Math.log10(number);
}
/**
 * [hef_Ln 返回一个数的自然对数(注释：Ln函数是Exp函数的反函数。)]
 * @param  {[type]} number [description]
 * @return {[type]}        [description]
 */
function hef_Ln(number){
	return Math.log(number);
}
/**
 * [hef_Log description]
 * @param  {[type]} number [用于计算对数的正实数]
 * @param  {[type]} base   [base 对数的底数,如果省略底数，缺省值为10]
 * @return {[type]}        [description]
 */
function hef_Log( number, base ){
	if(base==undefined){
		return hef_Log10(number);
	}
	return Math.log(number)/Math.log(base);
}
/**
 * [hef_Exp 返回e的n次幂]
 * @param  {[type]} number [description]
 * @return {[type]}        [description]
 */
function hef_Exp(number){
	return Math.exp(number);
}
/**
 * [hef_Format 返回一个数值格式化后的字符串]
 * @param {[type]} number [数值]
 * @param {[type]} style  [格式风格
 *  style  = 1  中文货币大写
	style  = 2  英文货币大写
	style  = 3  中国票据年格式，例如 2000 => 贰零零零 
	style  = 4  中国票据月格式，0-9  零玖  10  壹拾 11 壹拾壹
	style  = 5  中国票据日格式，0-9  零玖  10, 20, 30 壹拾, 11-19 壹拾壹
 * ]
 */
function hef_Format( number, style ){
	if(style==1){
		return hef_CNNum(number,1)+getCNCurrency("yuan");
	}else if(style==2){

	}else if(style==3){
		return numToCNNum(number)+getCNTime("y");
	}else if(style==4 || style==5){
		var mmordd = "";
		var xx = number+"";
		var suffix = style==4?getCNTime("m"):getCNTime("d");
		if(xx.length==1){
			mmordd = upperCaseCNNum('0')+upperCaseCNNum(xx);
		}else if(xx.length==2){
			mmordd = upperCaseCNNum(xx.charAt(0))+upperCaseCNNum('10')+upperCaseCNNum(xx.charAt(1));
		}
		return mmordd + suffix;
	}else if(style==5){

	}else{
		return "";
	}
}
/**
 * [hef_Fract 求分数的值]
 * @param  {[type]} str [分数的字符串形式]
 * @return {[type]}     [description]
 */
function hef_Fract(str){
	var slashId = str.indexOf("/");
	return eval(str.substring(0,slashId))/eval(str.substring(slashId+1));
}
/**
 * [hef_If 条件取值函数]
 * @param  {[type]} source [数值或数值表达式]
 * @param  {[type]} para1  [任意数据类型的参数1]
 * @param  {[type]} para2  [任意数据类型的参数2]
 * @return {[type]}        [当number不为0时返回para1,否则返回para2]
 */
function hef_If( source, para1, para2 ){
	if(eval(source.toString())){
		return para1;
	}else{
		return para2;
	}
}
/**
 * [hef_Int 下舍入函数]
 * @param  {[type]} number [description]
 * @return {[type]}        [description]
 */
function hef_Int( number ){
	var numstr = number+"";
	return numstr.substring(0,numstr.indexOf("."));
}
/**
 * [hef_Mod 返回两数相除的余数，结果的正负号与除数相同]
 * @param  {[type]} number  [description]
 * @param  {[type]} divisor [description]
 * @return {[type]}         [description]
 */
function hef_Mod(number,divisor){

}