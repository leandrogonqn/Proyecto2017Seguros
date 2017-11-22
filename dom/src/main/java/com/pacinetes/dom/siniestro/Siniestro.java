package com.pacinetes.dom.siniestro;

import java.util.Date;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import com.pacinetes.dom.poliza.Poliza;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Siniestro")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "siniestroId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.siniestro " + "WHERE siniestroActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.siniestro " + "WHERE siniestroActivo == false ") })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Siniestro {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getSiniestroDescripcion());
	}
	// endregion

	// region > constructor
	public Siniestro(final String siniestroDescripcion, Poliza siniestroPoliza, Date siniestroFecha) {
		setSiniestroDescripcion(siniestroDescripcion);
		setSiniestroPoliza(siniestroPoliza);
		setSiniestroFecha(siniestroFecha);
		setSiniestroActivo(true);
	}
	// endregion

	public String cssClass() {
		return (isSiniestroActivo() == true) ? "activo" : "inactivo";
	}

	// region > name (read-only property)
	public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Descripcion")
	private String siniestroDescripcion;

	public String getSiniestroDescripcion() {
		return siniestroDescripcion;
	}

	public void setSiniestroDescripcion(String siniestroDescripcion) {
		this.siniestroDescripcion = siniestroDescripcion;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Poliza")
	private Poliza siniestroPoliza;

	public Poliza getSiniestroPoliza() {
		return siniestroPoliza;
	}

	public void setSiniestroPoliza(Poliza siniestroPoliza) {
		this.siniestroPoliza = siniestroPoliza;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Fecha del siniestro")
	private Date siniestroFecha;

	public Date getSiniestroFecha() {
		return siniestroFecha;
	}

	public void setSiniestroFecha(Date siniestroFecha) {
		this.siniestroFecha = siniestroFecha;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean siniestroActivo;

	public boolean isSiniestroActivo() {
		return siniestroActivo;
	}

	public void setSiniestroActivo(boolean siniestroActivo) {
		this.siniestroActivo = siniestroActivo;
	}

	// endregion

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)

	public void borrarSiniestro() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setSiniestroActivo(false);
	}

	public Siniestro actualizarDescripcion(@ParameterLayout(named = "Descripcion") final String siniestroDescripcion) {
		setSiniestroDescripcion(siniestroDescripcion);
		return this;
	}

	public String default0ActualizarDescripcion() {
		return getSiniestroDescripcion();
	}

	public Siniestro actualizarSiniestroFecha(
			@ParameterLayout(named = "Fecha del siniestro") final Date siniestroFecha) {
		setSiniestroFecha(siniestroFecha);
		return this;
	}

	public Date default0ActualizarSiniestroFecha() {
		return getSiniestroFecha();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getSiniestroDescripcion();
	}

	// endregion

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	SiniestroRepository siniestroesRepository;

	// endregion
}
