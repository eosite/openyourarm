package com.glaf.maildata.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.security.RSAUtils;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.util.BASE64DecoderStream;

/**
 * <b>使用IMAP协议接收邮件</b><br/>
 * <p>
 * POP3和IMAP协议的区别:
 * </p>
 * <b>POP3</b>协议允许电子邮件客户端下载服务器上的邮件,但是在客户端的操作(如移动邮件、标记已读等)，不会反馈到服务器上，<br/>
 * 比如通过客户端收取了邮箱中的3封邮件并移动到其它文件夹，邮箱服务器上的这些邮件是没有同时被移动的。<br/>
 * <p>
 * <b>IMAP</b>协议提供webmail与电子邮件客户端之间的双向通信，客户端的操作都会同步反应到服务器上，对邮件进行的操作，服务
 * 上的邮件也会做相应的动作。比如在客户端收取了邮箱中的3封邮件，并将其中一封标记为已读，将另外两封标记为删除，这些操作会 即时反馈到服务器上。
 * </p>
 * <p>
 * 两种协议相比，IMAP 整体上为用户带来更为便捷和可靠的体验。POP3更易丢失邮件或多次下载相同的邮件，但IMAP通过邮件客户端
 * 与webmail之间的双向同步功能很好地避免了这些问题。
 * </p>
 */
public class MailIMAP {

	public static void main(String[] args) throws Exception {
		// 准备连接服务器的会话信息
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", "imap.163.com");
		props.setProperty("mail.imap.port", "143");
		props.setProperty("mail.imap.partialfetch", "false");

		// 创建Session实例对象
		Session session = Session.getDefaultInstance(props, null);

		// 创建IMAP协议的Store对象
		Store store = session.getStore("imap");

		// 连接邮件服务器
		store.connect("hmtdkfb@163.com", "mtxx87668438");
		/*
		 * Folder[] folders=store.getDefaultFolder().list(); for(Folder
		 * folder:folders){ folder.open(Folder.READ_WRITE); Message[] messages =
		 * folder.getMessages(); //System.out.println(folder.getFullName()+"收件箱中共"
		 * + messages.length + "封邮件!"); }
		 */
		// 获得收件箱
		Folder folder = store.getFolder("INBOX");
		// 以读写模式打开收件箱
		folder.open(Folder.READ_WRITE);

		// 获得收件箱的邮件列表
		Message[] messages = folder.getMessages();

		// 打印不同状态的邮件数量
		//System.out.println("收件箱中共" + messages.length + "封邮件!");
		//System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
		//System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
		//System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

		//System.out.println("------------------------开始解析邮件----------------------------------");

		// 解析邮件
		for (Message message : messages) {
			try {
				//System.out.println(message.getSubject());
			} catch (Exception e) {
				continue;
			}
			if (StringUtils.isEmpty(message.getSubject())) {
				continue;
			}
			Flags flags = message.getFlags(); 
			if(flags.contains(Flags.Flag.SEEN)){
				continue;
			}
			IMAPMessage msg = (IMAPMessage) message;
			String contentType = msg.getContentType();
			String subject = MimeUtility.decodeText(msg.getSubject());
			////System.out.println(contentType + "[" + subject + "]未读，是否需要阅读此邮件（yes/no）？");
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(System.in));
			// String answer = reader.readLine();
			// if ("yes".equalsIgnoreCase(answer)) {
			parseMessage(msg); // 解析邮件
			// 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
			msg.setFlag(Flag.SEEN, true); // 设置已读标志
			// }
		}

		// 关闭资源
		folder.close(false);
		store.close();
	}

	/**
	 * 解析邮件
	 * 
	 * @param messages
	 *            要解析的邮件列表
	 */
	public static void parseMessage(Message... messages) throws MessagingException, IOException {
		if (messages == null || messages.length < 1)
			throw new MessagingException("未找到要解析的邮件!");

		// 解析所有邮件
		for (int i = 0, count = messages.length; i < count; i++) {
			MimeMessage msg = (MimeMessage) messages[i];
			//System.out.println("------------------解析第" + msg.getMessageNumber() + "封邮件-------------------- ");
			//System.out.println("主题: " + getSubject(msg));
			//System.out.println("发件人: " + getFrom(msg));
			//try{
			  //System.out.println("收件人：" + getReceiveAddress(msg, null));
			//}
			//catch(MessagingException e){
				//System.out.println("没有收件人");
			//	continue;
			//}
			//System.out.println("发送时间：" + getSentDate(msg, null));
			//System.out.println("是否已读：" + isSeen(msg));
			//System.out.println("邮件优先级：" + getPriority(msg));
			//System.out.println("是否需要回执：" + isReplySign(msg));
			//System.out.println("邮件大小：" + msg.getSize() / 1024 + "kb");
			boolean isContainerAttachment = isContainAttachment(msg);
			//System.out.println("是否包含附件：" + isContainerAttachment);
			if (isContainerAttachment) {
				saveAttachment(msg, "d:\\mailtmp\\"); // 保存附件
			}
			StringBuffer content = new StringBuffer(30);
			getMailTextContent(msg, content);
			//System.out.println("邮件正文：" + (content.length() > 100 ? content.substring(0, 100) + "..." : content));
			//System.out.println("------------------第" + msg.getMessageNumber() + "封邮件解析结束-------------------- ");
			//System.out.println();
		}
	}

	/**
	 * 获得邮件主题
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 解码后的邮件主题
	 */
	public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/**
	 * 获得邮件发件人
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 姓名 <Email地址>
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		String from = "";
		Address[] froms = msg.getFrom();
		if (froms.length < 1)
			throw new MessagingException("没有发件人!");

		InternetAddress address = (InternetAddress) froms[0];
		String person = address.getPersonal();
		if (person != null) {
			person = MimeUtility.decodeText(person) + " ";
		} else {
			person = "";
		}
		from = person + "<" + address.getAddress() + ">";

		return from;
	}

	/**
	 * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
	 * <p>
	 * Message.RecipientType.TO 收件人
	 * </p>
	 * <p>
	 * Message.RecipientType.CC 抄送
	 * </p>
	 * <p>
	 * Message.RecipientType.BCC 密送
	 * </p>
	 * 
	 * @param msg
	 *            邮件内容
	 * @param type
	 *            收件人类型
	 * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
	 * @throws MessagingException
	 */
	public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			addresss = msg.getAllRecipients();
		} else {
			addresss = msg.getRecipients(type);
		}

		if (addresss == null || addresss.length < 1)
			throw new MessagingException("没有收件人!");
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append("<"+internetAddress.toUnicodeString()+">").append(",");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

		return receiveAddress.toString();
	}

	/**
	 * 获得邮件发送时间
	 * 
	 * @param msg
	 *            邮件内容
	 * @return yyyy年mm月dd日 星期X HH:mm
	 * @throws MessagingException
	 */
	public static String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null)
			return "";

		if (pattern == null || "".equals(pattern))
			pattern = "yyyy年MM月dd日 E HH:mm ";

		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/**
	 * 判断邮件中是否包含附件
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 邮件中存在附件返回true，不存在返回false
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean isContainAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("application") != -1) {
						flag = true;
					}

					if (contentType.indexOf("name") != -1) {
						flag = true;
					}
				}

				if (flag)
					break;
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**
	 * 判断邮件是否已读
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 如果邮件已读返回true,否则返回false
	 * @throws MessagingException
	 */
	public static boolean isSeen(MimeMessage msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/**
	 * 判断邮件是否需要阅读回执
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 需要回执返回true,否则返回false
	 * @throws MessagingException
	 */
	public static boolean isReplySign(MimeMessage msg) throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null)
			replySign = true;
		return replySign;
	}

	/**
	 * 获得邮件的优先级
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 1(High):紧急 3:普通(Normal) 5:低(Low)
	 * @throws MessagingException
	 */
	public static String getPriority(MimeMessage msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
				priority = "紧急";
			else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
				priority = "低";
			else
				priority = "普通";
		}
		return priority;
	}

	/**
	 * 获得邮件文本内容
	 * 
	 * @param part
	 *            邮件体
	 * @param content
	 *            存储邮件文本内容的字符串
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
		// 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType("text/*") && !isContainTextAttach) {
			try {
			   content.append(decodeText(part.getContent().toString()));
			}
			catch(Exception e)
			{
				//System.out.println("不支持的编码");
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	/**
	 * 保存附件
	 * 
	 * @param part
	 *            邮件中多个组合体中的其中一个组合体
	 * @param destDir
	 *            附件保存目录
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveAttachment(Part part, String destDir)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
			// 复杂体邮件包含多个邮件体
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				// 获得复杂体邮件中其中一个邮件体
				BodyPart bodyPart = multipart.getBodyPart(i);
				boolean base64Flag = false;
				BASE64DecoderStream bis = null;
				if (bodyPart instanceof MimeBodyPart) {
					//System.out.println("Processing MimeBodyPart...");
					MimeBodyPart mbp = (MimeBodyPart) bodyPart;
					//System.out.println(mbp.getContent().getClass());
					if (mbp.getContent() instanceof String) {
						String s = (String) mbp.getContent();
						//System.out.println("String Content: " + s);
					} else if (mbp.getContent() instanceof BASE64DecoderStream) {
						base64Flag = true;
						bis = (BASE64DecoderStream) mbp.getContent();
					}
				}
				// 某一个邮件体也有可能是由多个邮件体组成的复杂体
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					InputStream is = bodyPart.getInputStream();
					if (!base64Flag) {
						saveFile(is, destDir, decodeText(bodyPart.getFileName()));
					} else {
						saveBase64File(bis, destDir, decodeText(bodyPart.getFileName()));
					}
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart, destDir);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {

						if (!base64Flag) {
							InputStream is = bodyPart.getInputStream();
							saveFile(is, destDir, decodeText(bodyPart.getFileName()));
						} else {
							saveBase64File(bis, destDir, decodeText(bodyPart.getFileName()));
						}
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), destDir);
		}
	}

	/**
	 * 读取输入流中的数据保存至指定目录
	 * 
	 * @param is
	 *            输入流
	 * @param fileName
	 *            文件名
	 * @param destDir
	 *            文件存储目录
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void saveFile(InputStream is, String destDir, String fileName)
			throws FileNotFoundException, IOException {
		if(StringUtils.isEmpty(fileName)){
			return;
		}
		if(fileName.indexOf(".")<0){
			return;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destDir + fileName)));
		int len = -1;
		while ((len = bis.read()) != -1) {
			bos.write(len);
			bos.flush();
		}
		bos.close();
		bis.close();
	}

	private static void saveBase64File(BASE64DecoderStream bis, String destDir, String fileName) throws IOException {

		BufferedOutputStream bos = null;
		int len = -1;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(new File(destDir + fileName)));
			while ((len = bis.read()) != -1) {
				bos.write(len);
				bos.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			try {
				if(bos!=null)
				bos.close();
				if(bis!=null)
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw e;
			}
		}

	}

	/**
	 * 文本解码
	 * 
	 * @param encodeText
	 *            解码MimeUtility.encodeText(String text)方法编码后的文本
	 * @return 解码后的文本
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeText(String encodeText) throws UnsupportedEncodingException {
		if (encodeText == null || "".equals(encodeText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodeText);
		}
	}
	/**
	 * 获取邮件的ID
	 * @param mimeMessage
	 * @return
	 * @throws MessagingException
	 */
	public static String getMessageId(MimeMessage mimeMessage) throws MessagingException {
        String messageID = mimeMessage.getMessageID();
        //System.out.println("邮件ID: " + messageID);
        return messageID;
    }
	
}