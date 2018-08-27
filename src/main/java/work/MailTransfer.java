package work;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author ZJY
 * @ClassName: MailTransfer
 * @Description: MailTransfer
 * @date 2018/6/5 11:40
 */
public class MailTransfer {
    public void sendMail() {

        final Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtpdm.aliyun.com");// SMTP服务器地址
        props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.auth", "true");

        props.setProperty("mail.user", "zhangjunyang@deepwise.com");
        props.setProperty("mail.password", "Zjy12345+");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(props, authenticator);// 开启与邮件服务器交互的会话
//        session.setDebug(true);

        MimeMessage message = null;
        try {
            message = this.createMail(session, "zhangjunyang@deepwise.com", "jyzhang1994@163.com");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MimeMessage createMail(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "深睿客户支持", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        message.setSubject("客户邮件需求");
        message.setContent("帮我改个密码", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
    }

    public static void main(String[] args) {
        new MailTransfer().sendMail();
    }
}
