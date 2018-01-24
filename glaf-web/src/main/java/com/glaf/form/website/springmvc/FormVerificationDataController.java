package com.glaf.form.website.springmvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.util.ResponseUtils;
import com.glaf.form.core.domain.MsgValidHistory;
import com.glaf.form.core.query.MsgValidHistoryQuery;
import com.glaf.form.core.service.IFormDictoryService;
import com.glaf.form.core.service.MsgValidHistoryService;

/**
 * 验证码生成算法
 * 
 * @author lvd
 *
 */
@Controller("/form/verification")
@RequestMapping("/form/verification")
public class FormVerificationDataController {
	
	//控件基础字典数据
	@Autowired
	IFormDictoryService formDictoryService;
	//短信验证历史记录
	@Autowired
	MsgValidHistoryService msgValidHistoryService;
	@Autowired
	SysUserService sysUserService;
	
	// 字符数量
	private static int SIZE = 4;
	// 干扰线数量?
	private static int LINES = 10;
	// 宽度
	private static int WIDTH = 120;
	// 高度
	private static int HEIGHT = 32;
	// 字体大小
	private static int FONT_SIZE = 30;
	// 字体最大倾斜角度
	private static int FONT_DIP = 30;
	
	// 准备常用汉字集
	private static  String chinese = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
	private static  String character = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static  String number = "0123456789"; 
	
	protected static final Log logger = LogFactory.getLog(FormVerificationDataController.class);


	public FormVerificationDataController() {

	}

	
	/**
     * 设置背景色
     * 
     * @param g
     */
    private void setBackGround(Graphics g) {
        // 设置颜色
        g.setColor(Color.WHITE);
        // 填充区域
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
    
    /**
	 * 随机取色
	 */
	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), 
				ran.nextInt(256), ran.nextInt(256));
		return color;
	}
    /**
     * 设置边框
     * 
     * @param g
     */
    private void setBorder(Graphics g) {
        // 设置边框颜色
        g.setColor(Color.BLUE);
        // 边框区域
        g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
    }
    /**
     * 画随机线条
     * 
     * @param g
     */
    private void drawRandomLine(Graphics g) {
        // 设置线条个数并画线
        for (int i = 0; i < LINES; i++) {
        	// 设置颜色
        	g.setColor(getRandomColor());
            int x1 = new Random().nextInt(WIDTH);
            int y1 = new Random().nextInt(HEIGHT);
            int x2 = new Random().nextInt(WIDTH);
            int y2 = new Random().nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }
    /**
     * 画随机汉字
     * 
     * @param g
     * @return
     */
    private String drawRandomNum(Graphics2D g,String type) {
        StringBuffer sb = new StringBuffer();
        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD, FONT_SIZE));
        // 控制字数
        String str = "character".equals(type)?character:chinese;
        switch(type){
        	case "character": str = character;break;
        	case "chinese" : str = chinese; break;
        	case "number" : str = number;break;
        	default : str = "";
        }
        for (int i = 0; i < SIZE; i++) {
        	// 设置颜色
        	g.setColor(getRandomColor());
            // 设置字体旋转角度
            int degree = new Random().nextInt() % FONT_DIP;
            // 截取汉字
            String ch = str.charAt(new Random().nextInt(str.length())) + "";
            sb.append(ch);
            // 正向角度
            g.rotate(degree * Math.PI / 180, i * (WIDTH-20) / SIZE +10, HEIGHT / 2);
            g.drawString(ch,  i * (WIDTH-20)/ SIZE +10, HEIGHT / 2+10); 
            // 反向角度
            g.rotate(-degree * Math.PI / 180, i * (WIDTH-20)/ SIZE +10, HEIGHT / 2+10);
        }
        return sb.toString();
    }
	
	
	@RequestMapping("/data")
	public @ResponseBody void createImage(HttpServletRequest request, HttpServletResponse response){
		// 发头控制浏览器不要缓存
		HEIGHT = Integer.parseInt(request.getParameter("height"));
		String type = request.getParameter("type");
		
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg"); 
		// 1.创建空白图片
		BufferedImage bi = new BufferedImage(
			WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 2.获取图片画笔
		Graphics g = bi.getGraphics();
        // 设置背影色
        setBackGround(g);
        // 设置边框
//        setBorder(g);
        // 画干扰线
        drawRandomLine(g);
        // 写随机数
        String random = drawRandomNum((Graphics2D) g,type);
        // 将随机汉字存在session中
        request.getSession().setAttribute("checkcode", random);
        // 将图片写给浏览器
        try {
			ImageIO.write(bi, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("写出图片验证码失败" + e.getMessage());
		}
    }


	@RequestMapping("/checkcode")
	public @ResponseBody byte[] login(HttpSession session,HttpServletResponse res,HttpServletRequest req,String v){
	
		String code = (String) session.getAttribute("checkcode");
		JSONObject obj = new JSONObject();

		if(StringUtils.isNotEmpty(v)){
			if(v.equalsIgnoreCase(code)){
				obj.put("status",true);
			}else{
				obj.put("status", false);
			}
		}else{
			obj.put("status", false);
		}
		return obj.toJSONString().getBytes();
	}

	@RequestMapping("/setValidCode")
	public @ResponseBody byte[] setValidCode(HttpServletRequest request) throws Exception{
		String telephone = request.getParameter("telephone");
		if(StringUtils.isEmpty(telephone)){
			return ResponseUtils.responseJsonResult(false,"未输入手机号码");
		}
		//验证手机号码
		if(!isMobileNO(telephone)){
			return ResponseUtils.responseJsonResult(false,"手机号码不正确。");
		}
		// 写随机数
        StringBuffer randomCode = new StringBuffer();
        // 随机数字
        String str = number;
        for (int i = 0; i < SIZE; i++) {
            // 截取汉字
            String ch = str.charAt(new Random().nextInt(str.length())) + "";
            randomCode.append(ch);
        }
        
        JSONObject result = sendMessage(telephone,randomCode.toString());
        if(result.getBoolean("status")){
        	//将信息放入session中
        	request.getSession().setAttribute("MsgValidCode", randomCode.toString());
    		request.getSession().setAttribute("MsgValidTelephone", telephone);
    		request.getSession().setAttribute("MsgValidTime", System.currentTimeMillis());
    		return ResponseUtils.responseJsonResult(true);
        }else{
        	return ResponseUtils.responseJsonResult(false,result.getString("msg"));
        }
	}
	
	public JSONObject sendMessage(String telephone,String randomCode) throws Exception{
		JSONObject result = new JSONObject();
		String telephoneName=null,	//电话名称
				msgName=null,	//信息名称
				condictionCodeName=null,	//特征码值
				condictionCodeValue=null,	//特征码值
				url=null,	//短信发送地址
				msgValue=null,
				retMessage="";
		int maxLoginSendNum=0,maxRegisterSendNum=0;
		boolean retFlag = false;
		//从字典中获取对应的基础数据
		BaseDataManager bm = BaseDataManager.getInstance();
		List<BaseDataInfo> themeList = bm.getBaseData("loginmsgvalid");
		for (BaseDataInfo dictory : themeList) {
			if (dictory.getCode().equals("mobileName")) {
				telephoneName = dictory.getValue();
			} else if (dictory.getCode().equals("msgName")) {
				msgName = dictory.getValue();
			} else if (dictory.getCode().equals("number")){
				condictionCodeValue = dictory.getValue();
			} else if (dictory.getCode().equals("numberName")){
				condictionCodeName = dictory.getValue();
			} else if(dictory.getCode().equals("url")){
				url = dictory.getValue();
			} else if(dictory.getCode().equals("msgValue")){
				msgValue = dictory.getValue();
			} else if(dictory.getCode().equals("maxLoginSend")){
				maxLoginSendNum = Integer.valueOf(dictory.getValue());
			} else if(dictory.getCode().equals("maxRegisterSend")){
				maxRegisterSendNum = Integer.valueOf(dictory.getValue());
			} 
		}
		if(telephoneName!=null && msgName!=null && condictionCodeName!=null && condictionCodeValue!=null && url!=null){
			MsgValidHistory msgValidHistory = new MsgValidHistory();
			//			msgValidHistoryService 
			//组装成发送短信的url
			String sendUrl = url,msg = msgValue;
			msg = msg.replace("{{validCode}}", randomCode);
			sendUrl += "?"+condictionCodeName+"="+condictionCodeValue+DigestUtils.md5Hex(telephone+msg);
			sendUrl += "&telphone=" + telephone;
			sendUrl += "&msg=" + URLEncoder.encode(msg,"UTF-8");
//			sendUrl = "www.baidu.com";
			try{
				msgValidHistory.setUrl(sendUrl);
				msgValidHistory.setTelephone(telephone);
				msgValidHistory.setMsg(msg);
				msgValidHistory.setSendDate(new Date());
				//创建出今天的最初时间
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(new Date());
		        calendar.set(Calendar.HOUR_OF_DAY, 0);
		        calendar.set(Calendar.MINUTE, 0);
		        calendar.set(Calendar.SECOND, 0);
		        Date zeroDate = calendar.getTime();
		        
				//根据电话号码查询出用户
				SysUser sysUser = sysUserService.findByMobile(telephone);
				if(sysUser != null){
					//查询出今天所发送的次数
			        MsgValidHistoryQuery query = new MsgValidHistoryQuery();
					query.setTelephone(telephone);
					query.setType(0);
					query.setSendDateGreaterThanOrEqual(zeroDate);
					query.setStatus(2);//设置状态为成功,2
					int count = msgValidHistoryService.getMsgValidHistoryCountByQueryCriteria(query);

					msgValidHistory.setType(0);
					msgValidHistory.setTypeName("登陆验证");
					//校验同一号码的每天最大使用短信验证的登陆次数
					if(count >= maxLoginSendNum){
						//超过限制
						result.put("status", false);
						result.put("msg", "今天的短信登陆次数，已超过最大次数，请使用账号/密码登陆！");
						msgValidHistory.setStatus(0);	//发送失败
						msgValidHistory.setStatusName("发送失败");
						msgValidHistory.setResult("今天的短信登陆次数，已超过最大次数，请使用账号/密码登陆！");	//结果
						return result;
					}
				}else{
					MsgValidHistoryQuery query = new MsgValidHistoryQuery();
					query.setTelephone(telephone);
					query.setType(1);
					query.setSendDateGreaterThanOrEqual(zeroDate);
					query.setStatus(2);//设置状态为成功,2
					int count = msgValidHistoryService.getMsgValidHistoryCountByQueryCriteria(query);

					
					msgValidHistory.setType(1);
					msgValidHistory.setTypeName("注册验证");
					//校验同一号码的每天最大注册次数
					if(count >= maxRegisterSendNum){
						//超过限制
						result.put("status", false);
						result.put("msg", "该号码的短信注册今天已超多最大次数，请明天再注册！");
						msgValidHistory.setStatus(0);	//发送失败
						msgValidHistory.setStatusName("发送失败");
						msgValidHistory.setResult("该号码的短信注册今天已超多最大次数，请明天再注册！");	//结果
						return result;
					}
				}
				
				//短信发送
				HttpClient client = new HttpClient();
				GetMethod method = new GetMethod(sendUrl);
				int variable = client.executeMethod(method);
				if (variable != HttpStatus.SC_OK) {  
					retMessage = "远程访问失败，请稍后再试。";
				}else{
					//解析返回发送
					String ret = method.getResponseBodyAsString();	
					if(ret == null ){
						retMessage = "返回发送解析不正确。";
					}else if(ret.equals("YES")){
						retMessage = "发送成功";
						retFlag = true;
					}else if(ret.equals("NO")){
						retMessage = "发送失败。";
					}else if(ret.equals("No Code")){
						retMessage = "token错误";
					}else if(ret.equals("No Telphone")){
						retMessage = "手机号码格式不对";
					}else if(ret.equals("No msg")){
						retMessage = "没有需要发送的内容";
					}else if(ret.equals("Send Error")){
						retMessage = "MD5值不相同";
					}else{
						retMessage = "数据解析不正确。";
					}
				}
				//保存返回信息
				if(retFlag){
					msgValidHistory.setStatus(2);//成功
					msgValidHistory.setStatusName("发送成功");
				}else{
					msgValidHistory.setStatus(0);//失败
					msgValidHistory.setStatusName("发送失败");
				}
				msgValidHistory.setResult(retMessage);	//结果
			}catch(Exception e){
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				logger.error(sw.toString());
				msgValidHistory.setStatus(0);//失败
				msgValidHistory.setStatusName("发送失败");
				msgValidHistory.setResult("异常错误");	//结果
			}finally{
				//保存
				msgValidHistoryService.save(msgValidHistory);
			}
		}else{
			retMessage = "短信基础配置不正确，请联系配置者。";
		}
//		retFlag = true;//测试使用
		result.put("status", retFlag);
		result.put("msg", retMessage);
		return result;
	}
	
	/**
	 * 判断是否是手机号码
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
　　	 * 电信：133、153、180、189、（1349卫通）
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);
		return m.matches();
	} 
}
