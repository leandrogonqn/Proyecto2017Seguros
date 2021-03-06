/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.pacinetes.dom.compania;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Companias")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "companiaId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Companias "
				+ "WHERE companiaNombre.toLowerCase().indexOf(:companiaNombre) >= 0 "),
		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Companias " + "WHERE companiaActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Companias " + "WHERE companiaActivo == false ") })
@javax.jdo.annotations.Unique(name = "Companias_companiaNombre_UNQ", members = { "companiaNombre" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Compania implements Comparable<Compania> {

	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name", getCompaniaNombre());
	}
	// endregion

	public String cssClass() {
		return (getCompaniaActivo() == true) ? "activo" : "inactivo";
	}

	public static final int NAME_LENGTH = 200;

	// Constructor
	public Compania() {
	}

	public Compania(String companiaNombre, String companiaDireccion, String companiaTelefono) {
		setCompaniaNombre(companiaNombre);
		setCompaniaDireccion(companiaDireccion);
		setCompaniaTelefono(companiaTelefono);
		setCompaniaActivo(true);
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String companiaNombre;

	public String getCompaniaNombre() {
		return companiaNombre;
	}

	public void setCompaniaNombre(String companiaNombre) {
		this.companiaNombre = companiaNombre;
	}

	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Direccion")
	private String companiaDireccion;

	public String getCompaniaDireccion() {
		return companiaDireccion;
	}

	public void setCompaniaDireccion(String companiaDireccion) {
		this.companiaDireccion = companiaDireccion;
	}

	@javax.jdo.annotations.Column(length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Telefono")
	private String companiaTelefono;

	public String getCompaniaTelefono() {
		return companiaTelefono;
	}

	public void setCompaniaTelefono(String companiaTelefono) {
		this.companiaTelefono = companiaTelefono;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	private boolean companiaActivo;

	public boolean getCompaniaActivo() {
		return companiaActivo;
	}

	public void setCompaniaActivo(boolean companiaActivo) {
		this.companiaActivo = companiaActivo;
	}

	// endregion

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarCompania() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setCompaniaActivo(false);
	}

	public Compania actualizarCompaniaNombre(@ParameterLayout(named = "Nombre") final String companiaNombre) {
		setCompaniaNombre(companiaNombre);
		return this;
	}

	public String default0ActualizarCompaniaNombre() {
		return getCompaniaNombre();
	}

	public Compania actualizarCompaniaDireccion(@ParameterLayout(named = "Direccion") final String companiaDireccion) {
		setCompaniaDireccion(companiaDireccion);
		return this;
	}

	public String default0ActualizarCompaniaDireccion() {
		return getCompaniaDireccion();
	}

	public Compania actualizarCompaniaTelefono(@ParameterLayout(named = "Telefono") final String companiaTelefono) {
		setCompaniaTelefono(companiaTelefono);
		return this;
	}

	public String default0ActualizarCompaniaTelefono() {
		return getCompaniaTelefono();
	}

	public Compania actualizarCompaniaActivo(@ParameterLayout(named = "Activo") final boolean companiaActivo) {
		setCompaniaActivo(companiaActivo);
		return this;
	}

	public boolean default0ActualizarCompaniaActivo() {
		return getCompaniaActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getCompaniaNombre();
	}

	@Override
	public int compareTo(final Compania compania) {
		return this.companiaNombre.compareTo(compania.companiaNombre);
	}

	// endregion

	// acciones
	@ActionLayout(named = "Listar Todas las Compañias")
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@MemberOrder(sequence = "2")
	public List<Compania> listar() {
		return companiaRepository.listar();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Activos")
	@MemberOrder(sequence = "3")
	public List<Compania> listarActivos() {
		return companiaRepository.listarActivos();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Inactivos")
	@MemberOrder(sequence = "4")
	public List<Compania> listarInactivos() {
		return companiaRepository.listarInactivos();
	}

	@ActionLayout(named = "Prima Total", cssClassFa = "fa-usd")
	public BigDecimal calcularPrimaTotalPorCompañia() {
		List<Poliza> listaPolizas = polizaRepository.listarPorEstado(Estado.vigente);
		double suma = 0; // suma de primas
		for (Poliza p : listaPolizas) {
			if (p.getPolizaCompania() == this) {
				suma = suma + p.getPolizaImporteTotal();
			}
		}

		BigDecimal var = new BigDecimal(suma);

		return var;
	}

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	CompaniaRepository companiaRepository;

	@Inject
	PolizaRepository polizaRepository;

	@Inject
	Poliza poliza;

	// endregion
}
