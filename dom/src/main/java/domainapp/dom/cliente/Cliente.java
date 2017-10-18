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
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.cliente;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.modules.reportes.ClienteReporte;
import domainapp.dom.modules.reportes.GenerarReporte;
import domainapp.dom.persona.Persona;
import domainapp.dom.provincia.Provincia;
import net.sf.jasperreports.engine.JRException;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Clientes"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="clienteId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(
		        name = "buscarPorNombre", language = "JDOQL",
		        value = "SELECT "
		                + "FROM domainapp.dom.simple.Clientes "
		                + "WHERE clienteNombre.toLowerCase().indexOf(:clienteNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "buscarPorDNI", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE clienteDni == :clienteDni"),
})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Cliente extends Persona implements Comparable<Cliente> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Cliente: {clienteNombre}", "clienteNombre", getClienteNombre()+" "+getClienteApellido()+" Cuit/Cuil: "+getPersonaCuitCuil());
    }
    //endregion
    
    public String iconName(){
    	return (getClienteSexo()==Sexo.Femenino)? "Femenino":"Masculino";
    }
    
    //region > constructor
    public Cliente(final String clienteNombre) {
        setClienteNombre(clienteNombre);
    }
    
    public Cliente(String clienteNombre, String clienteApellido, Sexo clienteSexo, TipoDocumento clienteTipoDocumento, Localidad personaLocalidad, int clienteDni, String personaDireccion, String personaTelefono, String personaMail,
			Date clienteFechaNacimiento, boolean clienteNotificacionCumpleanios) {
		super();
		setClienteNombre(clienteNombre);
		setClienteApellido(clienteApellido);
		setClienteSexo(clienteSexo);
		setClienteTipoDocumento(clienteTipoDocumento);
		setPersonaLocalidad(personaLocalidad);
		setClienteDni(clienteDni);
		setPersonaDireccion(personaDireccion);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaCuitCuil(GenerarCuit.generar(clienteSexo, clienteDni));
		setClienteFechaNacimiento(clienteFechaNacimiento);
		setClienteNotificacionCumpleanios(clienteNotificacionCumpleanios);
		setPersonaActivo(true);
	}


	//endregion
    
    //GenerarReportePDF
	public String imprimirCliente() throws JRException, FileNotFoundException{
			
			List<Object> objectsReport = new ArrayList<Object>();
			
			ClienteReporte clienteReporte = new ClienteReporte();
			
			clienteReporte.setClienteApellido(getClienteApellido());
			clienteReporte.setClienteNombre(getClienteNombre());
			clienteReporte.setClienteSexo(getClienteSexo().toString());
			clienteReporte.setClienteDni(getClienteDni());
			clienteReporte.setClienteFechaNacimiento(getClienteFechaNacimiento());
			clienteReporte.setPersonaLocalidad(getPersonaLocalidad().getLocalidadesNombre());
			clienteReporte.setPersonaProvincia(getPersonaLocalidad().getLocalidadProvincia().getProvinciasNombre().toString());
			clienteReporte.setPersonaCuitCuil(getPersonaCuitCuil());
			clienteReporte.setPersonaDireccion(getPersonaDireccion());
			clienteReporte.setPersonaTelefono(getPersonaTelefono());
			
			objectsReport.add(clienteReporte);
			
			String nombreArchivo ="c:/reportes/Cliente_" + Integer.valueOf(clienteReporte.clienteDni)+"_"+  String.valueOf(clienteReporte.getClienteApellido() + "_" +String.valueOf(clienteReporte.getClienteNombre())) ;
			
			GenerarReporte.generarReporte("C:/workspace/Proyecto2017Seguros/webapp/reportes/Cliente.jrxml", objectsReport, nombreArchivo);
			
			return "Reporte Generado.";
	}	

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String clienteNombre;
    
    public String getClienteNombre() {
        return clienteNombre;
    }
    public void setClienteNombre(final String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
    
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Apellido")
    private String clienteApellido;
    public String getClienteApellido() {
		return clienteApellido;
	}
	public void setClienteApellido(String clienteApellido) {
		this.clienteApellido = clienteApellido;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Sexo")
	private Sexo clienteSexo;

    public Sexo getClienteSexo() {
		return clienteSexo;
	}

	public void setClienteSexo(Sexo clienteSexo) {
		this.clienteSexo = clienteSexo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Tipo Documento")
	private TipoDocumento clienteTipoDocumento;
	
	
	public TipoDocumento getClienteTipoDocumento() {
		return clienteTipoDocumento;
	}

	public void setClienteTipoDocumento(TipoDocumento clienteTipoDocumento) {
		this.clienteTipoDocumento = clienteTipoDocumento;
	}


	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="DNI")
	private int clienteDni;

    public int getClienteDni() {
		return clienteDni;
	}
	public void setClienteDni(int clienteDni) {
		this.clienteDni = clienteDni;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Fecha de Nacimiento")
    private Date clienteFechaNacimiento;

    public Date getClienteFechaNacimiento() {
		return clienteFechaNacimiento;
	}
	public void setClienteFechaNacimiento(Date clienteFechaNacimiento) {
		this.clienteFechaNacimiento = clienteFechaNacimiento;
	}		
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Notificacion cumpleaños")
    private boolean clienteNotificacionCumpleanios;

    public boolean getClienteNotificacionCumpleanios() {
		return clienteNotificacionCumpleanios;
	}
	public void setClienteNotificacionCumpleanios(boolean clienteNotificacionCumpleanios) {
		this.clienteNotificacionCumpleanios = clienteNotificacionCumpleanios;
	}	
	
    //endregion
	
	public List<Localidad> choices0ActualizarLocalidad(){
    	return localidadRepository.listarActivos();
    }
      
    public Localidad default0ActualizarLocalidad() {
    	return getPersonaLocalidad();
    }
    
    public Cliente actualizarLocalidad(@ParameterLayout(named="Localidades") final Localidad name) {
        setPersonaLocalidad(name);
        return this;
    }
	
	public Cliente actualizarSexo(@ParameterLayout(named="Sexo") final Sexo clienteSexo){
		setPersonaCuitCuil(GenerarCuit.generar(clienteSexo, getClienteDni()));
		setClienteSexo(clienteSexo);
        return this;
	}
	
	public Cliente actualizarDni(@ParameterLayout(named="Numero de Documento") final int clienteDni){
		setPersonaCuitCuil(GenerarCuit.generar(getClienteSexo(), clienteDni));
		setClienteDni(clienteDni);
        return this;
	}
	
	public Sexo default0ActualizarSexo(){
		return getClienteSexo();
	}
	
	public int default0ActualizarDni(){
		return getClienteDni();
	}
	
	public Cliente actualizarNombre(@ParameterLayout(named="Nombre") final String clienteNombre){
		setClienteNombre(clienteNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getClienteNombre();
	}

	public Cliente actualizarApellido(@ParameterLayout(named="Apellido") final String clienteApellido){
		setClienteApellido(clienteApellido);
		return this;
	}
	
	public String default0ActualizarApellido(){
		return getClienteApellido();
	}
	
	
	public Cliente actualizarTipoDocumento(@ParameterLayout(named="Tipo de Documento") final TipoDocumento clienteTipoDocumento){
		setClienteTipoDocumento(clienteTipoDocumento);
		return this;
	}
	
	public TipoDocumento default0ActualizarTipoDocumento(){
		return getClienteTipoDocumento();
	}
	
	public Cliente actualizarDireccion(@ParameterLayout(named="Direccion") final String personaDireccion){
		setPersonaDireccion(personaDireccion);
		return this;
	}
	
	public String default0ActualizarDireccion(){
		return getPersonaDireccion();
	}
	
	public Cliente actualizarTelefono(@ParameterLayout(named="Telefono") final String personaTelefono){
		setPersonaTelefono(personaTelefono);
		return this;
	}	
	
	public String default0ActualizarTelefono(){
		return getPersonaTelefono();
	}
	
	public Cliente actualizarMail(@ParameterLayout(named="Mail") final String personaMail){
		setPersonaMail(personaMail);
		return this;
	}	
	
	public String default0ActualizarMail(){
		return getPersonaMail();
	}
	
	public Cliente actualizarFechaNacimiento(@Nullable @ParameterLayout(named="Fecha de Nacimiento") @Parameter(optionality = Optionality.OPTIONAL) final Date clienteFechaNacimiento){
		if ((clienteFechaNacimiento == null)){
					setClienteNotificacionCumpleanios(false);
		}
		setClienteFechaNacimiento(clienteFechaNacimiento);
		return this;
	}

	public Date default0ActualizarFechaNacimiento(){
		return getClienteFechaNacimiento();
	}
	
	public Cliente actualizarNotificacionCumpleanios(@ParameterLayout(named="Notificacion Cumpleaños") final boolean clienteNotificacionCumpleanios){
		setClienteNotificacionCumpleanios(clienteNotificacionCumpleanios);
		return this;
	}
	
	
	
	public String validateActualizarNotificacionCumpleanios(final boolean clienteNotificacionCumpleanios){
		if ((clienteFechaNacimiento == null)&(clienteNotificacionCumpleanios == true)){
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		return "";
	}
	
	public boolean default0ActualizarNotificacionCumpleanios(){
		return getClienteNotificacionCumpleanios();
	}
	
	public Cliente actualizarActivo(@ParameterLayout(named="Activo") final boolean personaActivo){
		setPersonaActivo(personaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getPersonaActivo();
	}
	
    //region > delete (action)
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarCliente() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setPersonaActivo(false);
    }
   
    //endregion
    
    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "clienteNombre");
    }
    @Override
    public int compareTo(final Cliente other) {
        return ObjectContracts.compare(this, other, "clienteNombre");
    }

    //endregion

    //accion
    @ActionLayout(named="Listar todos los clientes")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @MemberOrder(sequence = "2")
    public List<Cliente> listar() {
        return clientesRepository.listar();
    }
    
//    @ActionLayout(named="Listar Clientes Activos")
//    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
//    @MemberOrder(sequence = "3")
//    public List<Cliente> listarActivos() {
//        return clientesRepository.listarActivos();
//    }
//    
//    @ActionLayout(named="Listar Clientes Inactivos")
//    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
//    @MemberOrder(sequence = "4")
//    public List<Cliente> listarInactivos() {
//        return clientesRepository.listarInactivos();
//    }
    
    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    LocalidadRepository localidadRepository;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    ClienteRepository clientesRepository;

    //endregion

}
