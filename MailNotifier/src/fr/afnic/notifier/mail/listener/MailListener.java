package fr.afnic.notifier.mail.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class MailListener {

	private String server = "";
	private String userid = "";
	private String password = "";
	private static String refresh = "10000";

	public MailListener() {
	}

	public void init(String host, String user, String pwd) {
		server = host;
		userid = user;
		password = pwd;
		System.out.println("init ==> server:" + server + " userid:" + userid + " password:" + password);
	}

	public static void main(String arg[]) {
		MailListener mail = new MailListener();
		if (arg.length == 3) {
			mail.init(arg[0], arg[1], arg[2]);
		} else if (arg.length > 3) {
			mail.init(arg[0], arg[1], arg[2]);
			refresh = arg[3] + "000";
		} else {
			System.out.println("Usage: MailListener [ServerHostName] [userId] [password] <refresh default10s>");
			System.exit(0);
		}
		mail.addListener();
	}

	public void addListener() {
		try {
			Properties props = System.getProperties();
			props.put(this.server, "hostname");
			// Session session = Session.getInstance(props, null);
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userid, password);
				}
			});
			session.setDebug(true);
			// Get a Store object
			Store store = session.getStore("imap");
			System.out.println("" + store.getURLName());

			// Connect
			store.connect(this.server, userid, password);
			// Open a Folder
			Folder folder = store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);

			// Add messageCountListener to listen for new messages
			// folder.addMessageCountListener(getMessageCountAdapter()); // ne fonctionne pas en pop3
			for (;;) {

				Message[] message = folder.getMessages();
				System.out.println("UNREAD MESSAGES : " + folder.getUnreadMessageCount());
				int unreadLimit = folder.getUnreadMessageCount();
				int readCount = 0;

				for (int i = message.length - 1; 0 <= i && readCount < unreadLimit; i--) {
					if (!message[i].isSet(Flag.SEEN)) {
						System.out.println("------------ Message " + (i + 1) + " ------------");
						boolean isNeedAction = mailAnalyser(message[i]);

						// TODO effectuer les actions nécessaire sur réception d'un message
						if (isNeedAction) {
							// le mail est un message où l'on doit envoyer une alerte
							System.out.println("Générer une alerte avec un fichier");
							flushNoticeFile(message[i]);
							
						}
						readCount++;
						message[i].setFlag(Flag.SEEN, isNeedAction);
						
						// System.out.print("Message : ");
						// InputStream stream = message[i].getInputStream();
						// while (stream.available() != 0) {
						// System.out.print((char) stream.read());
						// }

					}
				}
				// Check mail once in "freq" MILLIseconds
				int freq = Integer.parseInt(refresh);

				System.out.println("Theread will sleep for " + freq / 1000 + " seconds");
				Thread.sleep(freq); // sleep for freq milliseconds
				System.out.println("Thread awake after " + freq / 1000 + " seconds");
			}
			// folder.close(true);
			// store.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void flushNoticeFile(Message mail) throws IOException, MessagingException{
		String fileName = mail.getSubject().trim();

		FileOutputStream fos = new FileOutputStream(new File("notice/"+fileName+".notice"));
		fos.write(("\nSentDate : " + mail.getSentDate()).getBytes());
		fos.write(("\nFrom : " + mail.getFrom()[0]).getBytes());
		fos.write(("\nSubject : " + mail.getSubject()).getBytes());
		
		fos.flush();
		fos.close();
	}

	/*
	 * Dans cette méthode on va vouloir régarder l'entete de mail pour
	 */
	private boolean mailAnalyser(Message mail) throws MessagingException{
		System.out.println("------------ mailAnalyser ------------");
		System.out.println("IS SEEN : " + mail.isSet(Flag.SEEN));
		System.out.println("SentDate : " + mail.getSentDate());
		System.out.println("From : " + mail.getFrom()[0]);
		System.out.println("Subject : " + mail.getSubject());
		String from = mail.getFrom()[0].toString();
		
		if(mail.getSubject().startsWith("[tld-monitor]") &&
				from.contains("tld-monitor-bounces@icann.org")){
			return true;
		}
		//TEST 
		if(mail.getSubject().startsWith("[tld-monitor]") &&
				from.contains("bourbour@free.fr")){
			return true;
		}
		return false;
	}
	
	
}
