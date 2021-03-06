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
package com.pacinetes.dom.modelo;

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
import com.pacinetes.dom.marca.Marca;
import com.pacinetes.dom.marca.MarcaRepository;
import com.pacinetes.dom.tipovehiculo.TipoVehiculo;
import com.pacinetes.dom.tipovehiculo.TipoVehiculoRepository;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Modelos")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "modeloId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "buscarPorNombre", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Modelos "
				+ "WHERE modeloNombre.toLowerCase().indexOf(:modeloNombre) >= 0 "),
		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Modelos " + "WHERE modeloActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.Modelos " + "WHERE modeloActivo == false ") })
@javax.jdo.annotations.Unique(name = "Modelos_modeloNombre_UNQ", members = { "modeloNombre", "modeloMarcas" })
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
public class Modelo implements Comparable<Modelo> {
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{modeloNombre}", "modeloNombre", this.getModeloMarcas().getMarcasNombre() + "-"
				+ this.getModeloNombre() + "-" + this.getModeloTipoVehiculo().getTipoVehiculoNombre());
	}
	// endregion

	public String cssClass() {
		return (getModeloActivo() == true) ? "activo" : "inactivo";
	}

	public static final int NAME_LENGTH = 200;

	// Constructores
	public Modelo() {
	}

	public Modelo(String modeloNombre, TipoVehiculo modeloTipoVehiculo, Marca modeloMarcas) {
		setModeloNombre(modeloNombre);
		setModeloTipoVehiculo(modeloTipoVehiculo);
		setModeloMarcas(modeloMarcas);
		this.modeloActivo = true;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", name = "marcaId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Marca")
	private Marca modeloMarcas;

	public Marca getModeloMarcas() {
		return modeloMarcas;
	}

	public void setModeloMarcas(Marca modeloMarcas) {
		this.modeloMarcas = modeloMarcas;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", name = "tipoVehiculoId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo Vehiculo")
	private TipoVehiculo modeloTipoVehiculo;

	public TipoVehiculo getModeloTipoVehiculo() {
		return modeloTipoVehiculo;
	}

	public void setModeloTipoVehiculo(TipoVehiculo modeloTipoVehiculo) {
		this.modeloTipoVehiculo = modeloTipoVehiculo;
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Nombre")
	private String modeloNombre;

	public String getModeloNombre() {
		return modeloNombre;
	}

	public void setModeloNombre(final String modeloNombre) {
		this.modeloNombre = modeloNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	private boolean modeloActivo;

	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Activo")
	public boolean getModeloActivo() {
		return modeloActivo;
	}

	public void setModeloActivo(boolean modeloActivo) {
		this.modeloActivo = modeloActivo;
	}

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarModelo() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setModeloActivo(false);
	}

	public Modelo actualizarTipoVehiculo(@ParameterLayout(named = "Tipo Vehiculo") final TipoVehiculo name) {
		setModeloTipoVehiculo(name);
		return this;
	}

	public List<TipoVehiculo> choices0ActualizarTipoVehiculo() {
		return tipoVehiculoRepository.listarActivos();
	}

	public TipoVehiculo default0ActualizarTipoVehiculo() {
		return getModeloTipoVehiculo();
	}

	public Modelo actualizarMarca(@ParameterLayout(named = "Marca") final Marca name) {
		setModeloMarcas(name);
		return this;
	}

	public List<Marca> choices0ActualizarMarca() {
		return marcaRepository.listarActivos();
	}

	public Marca default0ActualizarMarca() {
		return getModeloMarcas();
	}

	public Modelo actualizarNombre(@ParameterLayout(named = "Nombre") final String modeloNombre) {
		setModeloNombre(modeloNombre);
		return this;
	}

	public String default0ActualizarNombre() {
		return getModeloNombre();
	}

	public Modelo actualizarActivo(@ParameterLayout(named = "Activo") final boolean modeloActivo) {
		setModeloActivo(modeloActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return getModeloActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return getModeloNombre();
	}

	@Override
	public int compareTo(final Modelo modelo) {
		return this.modeloNombre.compareTo(modelo.modeloNombre);
	}

	// endregion

	// acciones
	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar todos los Modelos")
	@MemberOrder(sequence = "2")
	public List<Modelo> listar() {
		return modelosRepository.listar();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Modelos Activos")
	@MemberOrder(sequence = "3")
	public List<Modelo> listarActivos() {
		return modelosRepository.listarActivos();
	}

	@Action(invokeOn = InvokeOn.OBJECT_ONLY)
	@ActionLayout(named = "Listar Modelos Inactivos")
	@MemberOrder(sequence = "4")
	public List<Modelo> listarInactivos() {
		return modelosRepository.listarInactivos();
	}

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@javax.inject.Inject
	TipoVehiculoRepository tipoVehiculoRepository;

	@javax.inject.Inject
	MarcaRepository marcaRepository;

	@Inject
	ModeloRepository modelosRepository;

	// endregion
}
