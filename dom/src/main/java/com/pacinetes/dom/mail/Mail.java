package com.pacinetes.dom.mail;

import java.util.List;
import java.util.Properties;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.persona.Persona;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Mail")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "mailId")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Mail {

	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getMail());
	}

	public Mail() {
	}

	public Mail(boolean mailAuth, boolean starttlsEnable, String smtphost, int smtpPort, String nombre, String mail,
			String contraseña) {
		setMailAuth(mailAuth);
		setStarttlsEnable(starttlsEnable);
		setSmtphost(smtphost);
		setSmtpPort(smtpPort);
		setNombre(nombre);
		setMail(mail);
		setContraseña(contraseña);
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Auth", describedAs = "Dejar true para usar autenticación mediante usuario y clave")
	private boolean mailAuth;

	public boolean getMailAuth() {
		return mailAuth;
	}

	public void setMailAuth(boolean mailAuth) {
		this.mailAuth = mailAuth;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Starttls Enable", describedAs = "Dejar true para conectar de manera segura al servidor SMTP")
	private boolean starttlsEnable;

	public boolean getStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Smtp Host", describedAs = "Servidor SMTP que vas a usar")
	private String smtphost;

	public String getSmtphost() {
		return smtphost;
	}

	public void setSmtphost(String smtphost) {
		this.smtphost = smtphost;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Smtp Port", describedAs = "Puerto SMTP que vas a usar")
	private int smtpPort;

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Nombre del remitente", describedAs = "Ingrese el nombre del remitente")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Mail", describedAs = "Ingrese su direccion de mail")
	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	@PropertyLayout(named = "Contraseña", describedAs = "Ingrese la contraseña de su mail")
	private String contraseña;

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	// acciones
	private static Mail obtenerMailEmisor() {
		List<Mail> lista = mailRepository.listar();
		Mail mail = lista.get(0);
		return mail;
	}

	private static Properties conectarse(Mail mail) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", mail.getMailAuth());
		props.put("mail.smtp.starttls.enable", mail.getStarttlsEnable());
		props.put("mail.smtp.host", mail.getSmtphost());
		props.put("mail.smtp.port", mail.getSmtpPort());
		return props;
	}

	private static Session autentificar(Mail mail, Properties props) {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail.getMail(), mail.getContraseña());
			}
		});
		return session;
	}

	public static void enviarMailPoliza(Persona cliente) {
		if (cliente.getPersonaMail() != null) {
			Mail mail = obtenerMailEmisor();
			Properties props = conectarse(mail);
			Session session = autentificar(mail, props);

			String asunto = "Emisión de Póliza";

			String mensaje = "Buenos días " + cliente.toString()
					+ ", \r\nSu poliza ya esta emitida, en los proximos días se la estaremos acercando a su domicilio.\r\nSaludos cordiales.\r\n"
					+ "Pacinetes S.R.L.";

			try {
				BodyPart texto = new MimeBodyPart();

				// Texto del mensaje
				texto.setText(mensaje);

				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);

				MimeMessage message = new MimeMessage(session);

				// Se rellena el From
				InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
				message.setFrom(emisor);

				// Se rellenan los destinatarios
				InternetAddress receptor = new InternetAddress();
				receptor.setAddress(cliente.getPersonaMail());
				message.addRecipient(Message.RecipientType.TO, receptor);

				// Se rellena el subject
				message.setSubject(asunto);

				// Se mete el texto y la foto adjunta.
				message.setContent(multiParte);

				Transport.send(message);

			} catch (MessagingException e) {
				messageService.informUser("Poliza creada, falló envío de mail");
			}
		}
	}

	public static void enviarMail(Persona cliente, String asunto, String mensaje) {
		if (cliente.getPersonaMail() != null) {
			Mail mail = obtenerMailEmisor();
			Properties props = conectarse(mail);
			Session session = autentificar(mail, props);

			try {
				BodyPart texto = new MimeBodyPart();

				// Texto del mensaje
				texto.setText(mensaje);

				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);

				MimeMessage message = new MimeMessage(session);

				// Se rellena el From
				InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
				message.setFrom(emisor);

				// Se rellenan los destinatarios
				InternetAddress receptor = new InternetAddress();
				receptor.setAddress(cliente.getPersonaMail());
				message.addRecipient(Message.RecipientType.TO, receptor);

				// Se rellena el subject
				message.setSubject(asunto);

				// Se mete el texto y la foto adjunta.
				message.setContent(multiParte);

				Transport.send(message);

			} catch (MessagingException e) {
				messageService.informUser("Poliza creada, falló envío de mail");
			}

		}
	}

	public static void enviarMailCumpleaños(Cliente cliente) {
		if (cliente.getPersonaMail() != null) {
			Mail mail = obtenerMailEmisor();
			Properties props = conectarse(mail);
			Session session = autentificar(mail, props);

			String asunto = "Feliz Cumpleaños " + cliente.getClienteNombre() + "!!!";

			String mensaje = "Queremos saludarlo en el mes de su cumpleaños, "
					+ "espero que tenga una gran celebración y un día maravilloso.\r\n\r\nFeliz cumpleaños "
					+ cliente.getClienteNombre() + ".\r\nDe parte del equipo de Pacinetes S.R.L.";

			try {
				BodyPart texto = new MimeBodyPart();

				// Texto del mensaje
				texto.setText(mensaje);

				MimeMultipart multiParte = new MimeMultipart();
				multiParte.addBodyPart(texto);

				MimeMessage message = new MimeMessage(session);

				// Se rellena el From
				InternetAddress emisor = new InternetAddress(mail.getNombre() + " <" + mail.getMail() + ">");
				message.setFrom(emisor);

				// Se rellenan los destinatarios
				InternetAddress receptor = new InternetAddress();
				receptor.setAddress(cliente.getPersonaMail());
				message.addRecipient(Message.RecipientType.TO, receptor);

				// Se rellena el subject
				message.setSubject(asunto);

				// Se mete el texto y la foto adjunta.
				message.setContent(multiParte);

				Transport.send(message);

			} catch (MessagingException e) {
				messageService.informUser("Poliza creada, falló envío de mail");
			}

		}
	}

	@Inject
	static MailRepository mailRepository;
	@Inject
	static MessageService messageService;

}
