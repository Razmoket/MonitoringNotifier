package fr.afnic.notifier.mail.listener;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;

public class MailListener {

	private String server = "";
	private String userid = "	";
	private String password = "";

	public MailListener() {
	}

	public static void main(String arg[]) {
		MailListener mail = new MailListener();
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
			Store store = session.getStore("pop3s");

			System.out.println("" + store.getURLName());
			// Connect
			store.connect(this.server, userid, password);

			// Open a Folder
			Folder folder = store.getFolder("inbox");

			folder.open(Folder.READ_WRITE);

			// Add messageCountListener to listen for new messages
			folder.addMessageCountListener(new MessageCountAdapter() {

				public void messagesAdded(MessageCountEvent ev) {
					System.out.println("message listener invoked.");
					Message[] msgs = ev.getMessages();

					System.out.println("Got " + msgs.length + " new messages");

					// Just dump out the new messages
					for (int i = 0; i < msgs.length; i++) {
						try {
							System.out.println("-----");

							if (msgs[i].getSubject().toLowerCase().startsWith("effort sheet details")) {

								/*
								 * Connection con=null; try{ Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
								 * con=DriverManager.getConnection("jdbc:odbc:mailDS"); Statement stmt=con.createStatement(); String query =
								 * "insert into Messages(Message) Values("+msgs[i].getContent().toString()+")"; ResultSet rs=stmt.executeQuery(query);
								 * }catch(Exception e){ e.printStackTrace(); }
								 */
								System.out.println("Message " + msgs[i].getMessageNumber() + ":");
								msgs[i].writeTo(System.out);

							}
						} catch (IOException ioex) {
							ioex.printStackTrace();
						} catch (MessagingException mex) {
							mex.printStackTrace();
						}
					}
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see javax.mail.event.MessageCountListener#messagesRemoved(javax.mail.event.MessageCountEvent)
				 */
				public void messagesRemoved(MessageCountEvent arg0) {
					// TODO Auto-generated method stub

				}

			});

			// Check mail once in "freq" MILLIseconds
			int freq = Integer.parseInt("5000");

			for (;;) {
				System.out.println("Theread will sleep for 5 seconds");
				Thread.sleep(freq); // sleep for freq milliseconds
				System.out.println("Thread awake after 5 seconds");
				System.out.println("message count in folder is " + folder.getMessageCount());
				System.out.println();
				System.out.println();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
