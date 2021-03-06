package com.ssm.OaManager.mailfun;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.ssm.OaManager.entity.mail.Mail;
import com.ssm.OaManager.entity.mail.MailAddr;
import com.ssm.OaManager.entity.mail.UserAuthenticator;
import com.ssm.OaManager.utils.MailUtil;


/**
 * 
 * @author TH
 *
 */
public class SendMail{
	
	private  Session session = null;
	private  Transport transport = null;
	
	 
	
	/***
     * 连接SMTP服务器   
     * @param userAuth
     * @param keyword
	 * @throws MessagingException
	 * 返回SMTPSession
     */
	public Session connectSMTP(UserAuthenticator userAuth,String keyword) throws MessagingException{
		//获取服务
		System.out.println("SMTP来了。。。。。。。。。。。。。。1");
		Properties props = new Properties();
		
		System.out.println("SMTP来了。。。。。。。。。。。。。。2");
		//创建服务环境
		if(userAuth.getMailAddr().split("@")[1].startsWith("163")){ //这是163邮箱
			props.setProperty("mail.transport.protocol", "smtp");//使用协议
			props.setProperty("mail.smtp.host", "smtp.163.com");//发件人邮箱服务地址
			props.setProperty("mail.smtp.auth", "true");//需要请求认证
			props.setProperty("mail.smtp.port", "465");//ssl端口
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			session=Session.getDefaultInstance(props,userAuth);
			System.out.println("SMTP来了。。。。。。。。。。。。。。4");
		}else{
			  props=MailUtil.getProps(userAuth.getMailAddr(), keyword);
			  session =Session.getInstance(props,userAuth);
			  System.out.println("SMTP来了。。。。。。。。。。。。。。5");
		}
		//调试
		session.setDebug(true);
		System.out.println("SMTP来了。。。。。。。。。。。。。。6");
		
		//获取发送对象
	    transport=session.getTransport();  //这里获取连接失败？？
	    
	    System.out.println("SMTP来了。。。。。。。。。。。。。。7");
	    //System.out.println("打印账号和密码试试看："+userAuth.getMailAddr()+userAuth.getMailPass());
		//连接SMTP服务器
	    
		transport.connect(userAuth.getMailAddr(),userAuth.getMailPass());  //这里连接失败
		System.out.println("SMTP来了。。。。。。。。。。。。。。8");
		
		return session;
	}
	
	
	
	 /**
	  * 写邮件主体  返回邮件
	  * @param session
	  * @param userAuth
	  * @param mail
	  * @param mailAddr
	  * @param filePath
	  * @return
	  * @throws Exception
	  */
	 public Message createMessage(Session session,UserAuthenticator userAuth,
			 Mail mail,MailAddr mailAddr,String filePath) throws Exception{
		   
		   Message msg = new MimeMessage(session);//创建邮件对象
		 
		   msg.setFrom(new InternetAddress(MimeUtility.encodeText(mail.getSender())+"<"+userAuth.getMailAddr()+">"));//可随便取的发件人名字
		   msg.setSubject(mail.getSubject());//标题
		   msg.setSentDate(new Date());//设置发送时间
		 //收件人
		 if(MailUtil.isNotNull(mailAddr.getReveiceAddr())){
		   msg.setRecipients(RecipientType.TO,InternetAddress.parse(mailAddr.getReveiceAddr()));
		 }
		 //抄送人
		 if(MailUtil.isNotNull(mailAddr.getSereveicerAddr())){
		   msg.setRecipients(RecipientType.CC,InternetAddress.parse(mailAddr.getSereveicerAddr()));	 
		 }
		 //暗送人
		 if(MailUtil.isNotNull(mailAddr.getAnreveicerAddr())){
		   msg.setRecipients(RecipientType.BCC,InternetAddress.parse(mailAddr.getAnreveicerAddr()));	 
		 }
		 
		 // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		 Multipart multipart = new MimeMultipart();
		 
		 // 添加邮件正文
		 if(MailUtil.isNotNull(mail.getContent())){
		   MimeBodyPart contentBodyPart = new MimeBodyPart();
		   contentBodyPart.setContent(mail.getContent(),"text/html;charset=UTF-8");
		   multipart.addBodyPart(contentBodyPart);
		 }
		 //添加附件
		 if(MailUtil.isNotNull(filePath)){
			 MimeBodyPart attachmentBodyPart = new MimeBodyPart(); 
			 // 根据附件路径获取文件,
		     FileDataSource dataSource = new FileDataSource(filePath);
		     attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
		     attachmentBodyPart.setFileName(MimeUtility.encodeWord(dataSource.getFile().getName()));
		     multipart.addBodyPart(attachmentBodyPart);
		 }
		 
		  // 邮件的内容
		  msg.setContent(multipart);
		 
		 return msg;
	 }
	 
	 
	    /**
	     * 综上  发邮件
	     * @param userAuth
	     * @param mail
	     * @param mailAddr
	     * @param filePath
	     * @param keyword
	     * @return
	     */
		public boolean sendMails(Session session,UserAuthenticator userAuth,Mail mail,
				MailAddr mailAddr,String filePath){
			 
		try {
			   //写邮件
			   Message msg=createMessage(session,userAuth,mail,mailAddr,filePath);
			   //发送
			   transport.send(msg);
			   
			   if(transport!=null){
				 transport.close();  
			   }
			   
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	
}
