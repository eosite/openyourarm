package com.glaf.maildata.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.Database;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.maildata.domain.EmailRec;
import com.glaf.maildata.domain.EmailRecAtt;
import com.glaf.maildata.mapper.EmailRecAttMapper;
import com.glaf.maildata.mapper.EmailRecMapper;
import com.glaf.maildata.query.EmailRecQuery;
import com.glaf.maildata.util.MailIMAP;
import com.glaf.sys.domain.ProjMuiprojlist;
import com.glaf.sys.mapper.ProjMuiprojlistMapper;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.util.BASE64DecoderStream;

import sun.misc.BASE64Decoder;

@Service("com.glaf.maildata.service.emailRecService")
@Transactional(readOnly = true)
public class EmailRecServiceImpl implements EmailRecService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EmailRecMapper emailRecMapper;

	protected EmailRecAttMapper emailRecAttMapper;

	protected ProjMuiprojlistMapper projMuiprojlistMapper;

	public EmailRecServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			emailRecMapper.deleteEmailRecById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> iDs) {
		if (iDs != null && !iDs.isEmpty()) {
			for (String id : iDs) {
				emailRecMapper.deleteEmailRecById(id);
			}
		}
	}

	public int count(EmailRecQuery query) {
		return emailRecMapper.getEmailRecCount(query);
	}

	public List<EmailRec> list(EmailRecQuery query) {
		List<EmailRec> list = emailRecMapper.getEmailRecs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEmailRecCountByQueryCriteria(EmailRecQuery query) {
		return emailRecMapper.getEmailRecCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EmailRec> getEmailRecsByQueryCriteria(int start, int pageSize, EmailRecQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EmailRec> rows = sqlSessionTemplate.selectList("getEmailRecs", query, rowBounds);
		return rows;
	}

	public EmailRec getEmailRec(String id) {
		if (id == null) {
			return null;
		}
		EmailRec emailRec = emailRecMapper.getEmailRecById(id);
		return emailRec;
	}

	@Transactional
	public void save(EmailRec emailRec) {
		if (StringUtils.isEmpty(emailRec.getID())) {
			emailRec.setID(UUID32.getUUID());
			// emailRec.setCreateDate(new Date());
			// emailRec.setDeleteFlag(0);
			try {
				emailRecMapper.insertEmailRec(emailRec);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			emailRecMapper.updateEmailRec(emailRec);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	/**
	 * 对复杂邮件的解析
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void parseMultipart(Multipart multipart) throws MessagingException, IOException {
		int count = multipart.getCount();
		// System.out.println("couont = " + count);
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			// System.out.println(bodyPart.getContentType());
			if (bodyPart.isMimeType("text/plain")) {
				// System.out.println("plain................." +
				// bodyPart.getContent());
			} else if (bodyPart.isMimeType("text/html")) {
				// System.out.println("html..................." +
				// bodyPart.getContent());
			} else if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(mpart);

			} else if (bodyPart.isMimeType("application/octet-stream")) {
				String disposition = bodyPart.getDisposition();
				// System.out.println(disposition);
				if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
					String fileName = bodyPart.getFileName();
					// InputStream is = bodyPart.getInputStream();
					// copy(is, new FileOutputStream("D:\\" + fileName));
				}
			}
		}
	}

	/**
	 * 获取邮箱配置
	 * 
	 * @param sysIndexId
	 * @return
	 */
	public Properties getMailPrroperties(String sysIndexId) {
		ProjMuiprojlist projMuiprojlist = projMuiprojlistMapper.getLocalProjMuiprojlist();
		String userName = projMuiprojlist.getEmail();
		String password = projMuiprojlist.getEmailPsw();
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		String host = "imap." + userName.substring(userName.indexOf("@") + 1, userName.length());
		Properties props = new Properties();
		props.setProperty("userName", userName);
		props.setProperty("password", password);
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", "143");
		props.setProperty("mail.imap.partialfetch", "false");
		return props;
	}

	@Transactional(propagation = Propagation.NEVER)
	public void readMail() {

		// 获取邮箱配置
		Properties props = getMailPrroperties(null);
		if (props == null)
			return;
		// 创建Session实例对象
		Session session = Session.getDefaultInstance(props, null);
		String userName = props.getProperty("userName");
		String password = props.getProperty("password");
		// 创建IMAP协议的Store对象
		Store store;
		try {
			store = session.getStore("imap");
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			return;
		}

		// 连接邮件服务器
		try {
			store.connect(userName, password);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			return;
		}
		/*
		 * Folder[] folders=store.getDefaultFolder().list(); for(Folder
		 * folder:folders){ folder.open(Folder.READ_WRITE); Message[] messages =
		 * folder.getMessages();
		 * //System.out.println(folder.getFullName()+"收件箱中共" + messages.length +
		 * "封邮件!"); }
		 */
		// 获得收件箱
		Folder folder = null;
		try {
			folder = store.getFolder("INBOX");
			// 以读写模式打开收件箱
			folder.open(Folder.READ_WRITE);

			// 获得收件箱的邮件列表
			Message[] messages = folder.getMessages();
			// 打印不同状态的邮件数量
			// System.out.println("收件箱中共" + messages.length + "封邮件!");
			// System.out.println("收件箱中共" + folder.getUnreadMessageCount() +
			// "封未读邮件!");
			// System.out.println("收件箱中共" + folder.getNewMessageCount() +
			// "封新邮件!");
			// System.out.println("收件箱中共" + folder.getDeletedMessageCount() +
			// "封已删除邮件!");

			// System.out.println("------------------------开始解析邮件----------------------------------");

			// 解析邮件
			for (Message message : messages) {
				try {
					message.getSubject();
				} catch (Exception e) {
					continue;
				}
				if (StringUtils.isEmpty(message.getSubject())) {
					continue;
				}
				Flags flags = message.getFlags();
				if (flags.contains(Flags.Flag.SEEN)) {
					continue;
				}
				IMAPMessage msg = (IMAPMessage) message;
				try {
					String subject = MimeUtility.decodeText(msg.getSubject());
					// //System.out.println(contentType + "[" + subject +
					// "]未读，是否需要阅读此邮件（yes/no）？");
					// BufferedReader reader = new BufferedReader(new
					// InputStreamReader(System.in));
					// String answer = reader.readLine();
					// if ("yes".equalsIgnoreCase(answer)) {
					parseMessage(msg); // 解析邮件
					// 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
					msg.setFlag(Flag.SEEN, true); // 设置已读标志
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					continue;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					continue;
				}

				// }
			}

		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			// 关闭资源
			try {
				folder.close(false);
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Transactional
	/**
	 * 解析邮件
	 * 
	 * @param messages
	 *            要解析的邮件列表
	 */
	public void parseMessage(Message... messages) throws MessagingException, IOException {
		if (messages == null || messages.length < 1)
			throw new MessagingException("未找到要解析的邮件!");

		// 解析所有邮件
		for (int i = 0, count = messages.length; i < count; i++) {
			MimeMessage msg = (MimeMessage) messages[i];
			// System.out.println("------------------解析第" +
			// msg.getMessageNumber() + "封邮件-------------------- ");
			// System.out.println("主题: " + MailIMAP.getSubject(msg));
			// System.out.println("发件人: " + MailIMAP.getFrom(msg));
			// try {
			// System.out.println("收件人：" + MailIMAP.getReceiveAddress(msg,
			// null));
			// } catch (MessagingException e) {
			// System.out.println("没有收件人");
			// continue;
			// }
			EmailRec emailRec = new EmailRec();
			emailRec.setFrom(MailIMAP.getFrom(msg));
			emailRec.setTo(MailIMAP.getReceiveAddress(msg, null));
			emailRec.setEmail(MailIMAP.getReceiveAddress(msg, null));
			emailRec.setIntFlag(1);
			emailRec.setMsgId(MailIMAP.getMessageId(msg));
			emailRec.setIntOperat(0);
			emailRec.setSubject(MailIMAP.getSubject(msg));
			emailRec.setDate(msg.getSentDate());
			// System.out.println("发送时间：" + MailIMAP.getSentDate(msg, null));
			// System.out.println("是否已读：" + MailIMAP.isSeen(msg));
			// System.out.println("邮件优先级：" + MailIMAP.getPriority(msg));
			// System.out.println("是否需要回执：" + MailIMAP.isReplySign(msg));
			// System.out.println("邮件大小：" + msg.getSize() / 1024 + "kb");
			boolean isContainerAttachment = MailIMAP.isContainAttachment(msg);
			// System.out.println("是否包含附件：" + isContainerAttachment);

			StringBuffer content = new StringBuffer(30);
			MailIMAP.getMailTextContent(msg, content);
			String mailText = content.toString();
			if (msg.isMimeType("text/plain")) {
				emailRec.setText(mailText);
			} else {
				// 获取有效内容
				int startIndex = content.indexOf("<?xml");
				int endIndex = content.indexOf("</文件交换信息>");
				if (endIndex > 0) {
					mailText = content.substring(startIndex, endIndex) + "</文件交换信息>";
					emailRec.setText(mailText);
				} else {
					emailRec.setHtml(mailText);
				}
			}
			if (mailText.indexOf("</文件交换信息>") > 0) {
				try {
					emailRec = getEmailRecFromMailText(emailRec);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					logger.error("从邮件内容中获取信息出错" + e.getMessage());
					return;
				}
			}
			save(emailRec);
			// System.out.println("邮件正文：" + (content.length() > 100 ?
			// content.substring(0, 100) + "..." : content));
			String mailId = emailRec.getID();
			if (isContainerAttachment) {
				saveAttachment(msg, mailId); // 保存附件
			}
			// System.out.println("------------------第" + msg.getMessageNumber()
			// + "封邮件解析结束-------------------- ");
			// System.out.println();
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
	public void saveAttachment(Part part, String mailId)
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
					// System.out.println("Processing MimeBodyPart...");
					MimeBodyPart mbp = (MimeBodyPart) bodyPart;
					// System.out.println(mbp.getContent().getClass());
					if (mbp.getContent() instanceof String) {
						String s = (String) mbp.getContent();
						// System.out.println("String Content: " + s);
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
						saveAtt(mailId, is, MailIMAP.decodeText(bodyPart.getFileName()));
					} else {
						saveBase64Att(mailId, bis, MailIMAP.decodeText(bodyPart.getFileName()));
					}
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart, mailId);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {

						if (!base64Flag) {
							InputStream is = bodyPart.getInputStream();
							saveAtt(mailId, is, MailIMAP.decodeText(bodyPart.getFileName()));
						} else {
							saveBase64Att(mailId, bis, MailIMAP.decodeText(bodyPart.getFileName()));
						}
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), mailId);
		}
	}

	/**
	 * 从邮件内容中获取信息
	 * 
	 * @param emailRec
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws DocumentException
	 */
	public EmailRec getEmailRecFromMailText(EmailRec emailRec) throws UnsupportedEncodingException, DocumentException {
		// 分析mailText 获取邮件fromsysid intaction类型 listno tosysid
		SAXReader reader = new SAXReader();
		InputStream in = null;
		Document doc = null;
		try {
			in = new ByteArrayInputStream(emailRec.getText().getBytes("GB2312"));
			doc = reader.read(in);
			Element root = doc.getRootElement();
			// 获取送出单位
			String sendSystemId = null;
			List<Element> childElements = root.elements("送出单位");
			sendSystemId = childElements.get(0).attributeValue("系统ID");
			emailRec.setFromSysId(sendSystemId);
			// 接收单位
			String recSystemId = null;
			childElements = root.elements("接收单位");
			recSystemId = childElements.get(0).attributeValue("系统ID");
			emailRec.setToSysId(recSystemId);
			// 如果当前接收单位为空 则设置为当前库所在的单位
			if (StringUtils.isEmpty(recSystemId)) {
				ProjMuiprojlist projMuiprojlist = projMuiprojlistMapper.getLocalProjMuiprojlist();
				emailRec.setToSysId(projMuiprojlist.getSysId());
			}
			childElements = root.elements("邮件作用");
			String processType = childElements.get(0).attributeValue("类型");
			emailRec.setIntAction(Integer.parseInt(processType));
			// String from =
			// doc.selectSingleNode("/文件交换信息/送出单位/电子邮箱").getText();
			// String sendSysName =
			// doc.selectSingleNode("/文件交换信息/送出单位/单位").getText();
			// String to = doc.selectSingleNode("/文件交换信息/接收单位/电子邮箱").getText();
			// String recSysName =
			// doc.selectSingleNode("/文件交换信息/接收单位/单位").getText();
			String maillistNo = doc.selectSingleNode("/文件交换信息/送出单位/邮件序号").getText();
			emailRec.setListNo(Integer.parseInt(maillistNo));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw e;
		} finally {
			doc = null;
			reader = null;
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return emailRec;
	}

	public void saveAtt(String mailId, InputStream is, String fileName) {
		EmailRecAtt emailRecAtt = new EmailRecAtt();
		emailRecAtt.setFileId(UUID32.getUUID());
		emailRecAtt.setCTime(new Date());
		emailRecAtt.setTopId(mailId);
		emailRecAtt.setFileName(fileName);
		byte[] fileContent = null;
		try {
			fileContent = IOUtils.toByteArray(is);
			if (fileContent == null || fileContent.length == 0)
				return;
			emailRecAtt.setFileSize(fileContent.length);
			emailRecAtt.setFileContent(fileContent);
			emailRecAttMapper.insertEmailRecAtt(emailRecAtt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void saveBase64Att(String mailId, BASE64DecoderStream bis, String fileName) {
		EmailRecAtt emailRecAtt = new EmailRecAtt();
		emailRecAtt.setFileId(UUID32.getUUID());
		emailRecAtt.setCTime(new Date());
		emailRecAtt.setTopId(mailId);
		emailRecAtt.setFileName(fileName);
		byte[] fileContent = null;
		try {
			fileContent = IOUtils.toByteArray(bis);
			if (fileContent == null || fileContent.length == 0)
				return;
			emailRecAtt.setFileSize(fileContent.length);
			emailRecAtt.setFileContent(fileContent);
			emailRecAttMapper.insertEmailRecAtt(emailRecAtt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入数据到数据库
	 * 
	 * @param database
	 * @param data
	 * @throws IOException
	 */
	public void saveDataToDb(byte[] xmlData) throws DocumentException, IOException {
		// 分析mailText 获取邮件fromsysid intaction类型 listno tosysid
		SAXReader reader = new SAXReader();
		InputStream in = null;
		Document doc = null;
		EmailRec emailRec = new EmailRec();
		emailRec.setIntFlag(0);
		emailRec.setIntOperat(0);
		emailRec.setDate(new Date());
		try {
			in = new ByteArrayInputStream(xmlData);
			reader.setEncoding("UTF-8");
			doc = reader.read(in);
			doc.setXMLEncoding("GB2312");
			Element root = doc.getRootElement();
			//主题
			String subject=doc.selectSingleNode("/文件交换信息/主题").getText();
			emailRec.setSubject(subject);
			// 获取送出单位
			String sendSystemId = null;
			List<Element> childElements = root.elements("送出单位");
			sendSystemId = childElements.get(0).attributeValue("系统ID");
			emailRec.setFromSysId(sendSystemId);
			// 接收单位
			String recSystemId = null;
			childElements = root.elements("接收单位");
			recSystemId = childElements.get(0).attributeValue("系统ID");
			emailRec.setToSysId(recSystemId);
			childElements = root.elements("邮件作用");
			String processType = childElements.get(0).attributeValue("类型");
			emailRec.setIntAction(Integer.parseInt(processType));
			String processDesc = doc.selectSingleNode("/文件交换信息/邮件作用/类型说明").getText();
			//emailRec.setSubject(processDesc);
			String from = doc.selectSingleNode("/文件交换信息/送出单位/电子邮箱").getText();
			emailRec.setFrom(from);
			String sendSysName = doc.selectSingleNode("/文件交换信息/送出单位/单位").getText();
			String to = doc.selectSingleNode("/文件交换信息/接收单位/电子邮箱")!=null?doc.selectSingleNode("/文件交换信息/接收单位/电子邮箱").getText():"";
			emailRec.setTo(to);
			emailRec.setEmail(to);
			String recSysName = doc.selectSingleNode("/文件交换信息/接收单位/单位")!=null?doc.selectSingleNode("/文件交换信息/接收单位/单位").getText():"";
			String maillistNo = doc.selectSingleNode("/文件交换信息/送出单位/邮件序号")!=null?doc.selectSingleNode("/文件交换信息/送出单位/邮件序号").getText():"";
			emailRec.setListNo(Integer.parseInt(maillistNo));
			save(emailRec);
			// 获取附件
			Element attRootElement = (Element) root.elements("附件列表").get(0);
			if (attRootElement != null) {
				List<Element> attrElements = attRootElement.elements("附件");
				String attrBase64 = null;
				byte[] attrContent = null;
				for (Element attrElement : attrElements) {
					attrBase64 = attrElement.getText();
					attrContent = new BASE64Decoder().decodeBuffer(attrBase64);
					EmailRecAtt emailRecAtt = new EmailRecAtt();
					emailRecAtt.setFileId(UUID32.getUUID());
					emailRecAtt.setCTime(new Date());
					emailRecAtt.setTopId(emailRec.getID());
					emailRecAtt.setFileName("~emailfatt.zip");
					emailRecAtt.setFileSize(attrContent.length);
					emailRecAtt.setFileContent(attrContent);
					emailRecAttMapper.insertEmailRecAtt(emailRecAtt);
				}
			}
			root.remove(attRootElement);
			emailRec.setText(doc.asXML());
			emailRec.setIntFlag(1);
			save(emailRec);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		} finally {
			doc = null;
			reader = null;
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				throw e;
			}

		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.mapper.EmailRecMapper")
	public void setEmailRecMapper(EmailRecMapper emailRecMapper) {
		this.emailRecMapper = emailRecMapper;
	}

	@javax.annotation.Resource
	public void setEmailRecAttMapper(EmailRecAttMapper emailRecAttMapper) {
		this.emailRecAttMapper = emailRecAttMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setProjMuiprojlistMapper(ProjMuiprojlistMapper projMuiprojlistMapper) {
		this.projMuiprojlistMapper = projMuiprojlistMapper;
	}

}
