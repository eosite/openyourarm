package com.glaf.maildata.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.maildata.query.EmailRecQuery;
import com.glaf.maildata.query.EmailSendQuery;
import com.glaf.maildata.service.EmailRecService;
import com.glaf.maildata.service.EmailSendService;

@Controller("/maildata/main")
@RequestMapping("/maildata/main")
public class MailDataController {
	protected EmailRecService emailRecService;
	protected EmailSendService emailSendService;
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
			throws UnsupportedEncodingException {
        String url="/maildata/main";
        //获取邮箱邮件数统计
        EmailRecQuery query=new EmailRecQuery();
        int inboxCount=emailRecService.getEmailRecCountByQueryCriteria(query);
        modelMap.put("inbox", inboxCount);
        query=new EmailRecQuery();
        query.setIntOperat(0);
        int important=emailRecService.getEmailRecCountByQueryCriteria(query);
        modelMap.put("important", important);
        query=new EmailRecQuery();
        Date currDate = new Date();
		Calendar cr = Calendar.getInstance();
		cr.setTime(currDate);
		cr.add(Calendar.DATE, -7);
		query.setDateGreaterThanOrEqual(cr.getTime());
		query.setDateLessThanOrEqual(currDate);
        int news=emailRecService.getEmailRecCountByQueryCriteria(query);
        modelMap.put("last", news);
        
        //发件箱邮件数统计
        EmailSendQuery sendquery=new EmailSendQuery();
        sendquery.setIntOperat(1);
        int sent=emailSendService.getEmailSendCountByQueryCriteria(sendquery);
        modelMap.put("sent", sent);
        //草稿箱
        sendquery=new EmailSendQuery();
        sendquery.setIntOperat(0);
        int trash=emailSendService.getEmailSendCountByQueryCriteria(sendquery);
        modelMap.put("trash", trash);
		return new ModelAndView(url, modelMap);
	}
	@Resource
	public void setEmailRecService(EmailRecService emailRecService) {
		this.emailRecService = emailRecService;
	}
	@Resource
	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}
}
