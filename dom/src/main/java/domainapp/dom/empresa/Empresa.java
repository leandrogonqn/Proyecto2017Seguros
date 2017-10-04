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
package domainapp.dom.empresa;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

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
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.persona.Persona;
import domainapp.dom.provincia.Provincia;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Empresas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="empresaId")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(
		        name = "buscarPorRazonSocial", language = "JDOQL",
		        value = "SELECT "
		                + "FROM domainapp.dom.simple.Empresas "
		                + "WHERE empresaRazonSocial.toLowerCase().indexOf(:empresaRazonSocial) >= 0 ")})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Empresa extends Persona implements Comparable<Empresa> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Empresa: {empresaRazonSocial}", "empresaRazonSocial", getEmpresaRazonSocial()+" Cuit/Cuil: "+getPersonaCuitCuil());
    }
    //endregion
    
    //region > constructor
    public Empresa(final String empresaRazonSocial) {
        setEmpresaRazonSocial(empresaRazonSocial);
    }
    
    public Empresa(
    		String empresaRazonSocial, 
    		String personaCuitCuil,
    		Localidad personaLocalidad, 
    		String personaDireccion, 
    		String personaTelefono, 
    		String personaMail) {
		setEmpresaRazonSocial(empresaRazonSocial);
		setPersonaCuitCuil(personaCuitCuil);
		setPersonaLocalidad(personaLocalidad);
		setPersonaDireccion(personaDireccion);
		setPersonaTelefono(personaTelefono);
		setPersonaMail(personaMail);
		setPersonaActivo(true);
	}


	//endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Razon Social")
    private String empresaRazonSocial;
    
    public String getEmpresaRazonSocial() {
        return empresaRazonSocial;
    }
    public void setEmpresaRazonSocial(final String empresaRazonSocial) {
        this.empresaRazonSocial = empresaRazonSocial;
    }
    
	
    //endregion
	
	public List<Localidad> choices0ActualizarLocalidad(){
    	return localidadRepository.listarActivos();
    }
      
    public Localidad default0ActualizarLocalidad() {
    	return getPersonaLocalidad();
    }
    
    public Empresa actualizarLocalidad(@ParameterLayout(named="Localidades") final Localidad name) {
        setPersonaLocalidad(name);
        return this;
    }
	
	public Empresa actualizarRazonSocial(@ParameterLayout(named="Razon Social") final String empresaRazonSocial){
		setEmpresaRazonSocial(empresaRazonSocial);
		return this;
	}
	
	public String default0ActualizarRazonSocial(){
		return getEmpresaRazonSocial();
	}
	
	public Empresa actualizarPersonaCuitCuil(@ParameterLayout(named="CUIT") final String personaCuitCuil){
		setPersonaCuitCuil(personaCuitCuil);
		return this;
	}
	
	public String default0ActualizarPersonaCuitCuil(){
		return getPersonaCuitCuil();
	}

	public Empresa actualizarDireccion(@ParameterLayout(named="Direccion") final String personaDireccion){
		setPersonaDireccion(personaDireccion);
		return this;
	}
	
	public String default0ActualizarDireccion(){
		return getPersonaDireccion();
	}
	
	public Empresa actualizarTelefono(@ParameterLayout(named="Telefono") final String personaTelefono){
		setPersonaTelefono(personaTelefono);
		return this;
	}	
	
	public String default0ActualizarTelefono(){
		return getPersonaTelefono();
	}
	
	public Empresa actualizarMail(@ParameterLayout(named="Mail") final String personaMail){
		setPersonaMail(personaMail);
		return this;
	}	
	
	public String default0ActualizarMail(){
		return getPersonaMail();
	}
	
	public Empresa actualizarActivo(@ParameterLayout(named="Activo") final boolean personaActivo){
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
    public void borrarEmpresa() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setPersonaActivo(false);
    }
   
    //endregion
    
    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "empresaRazonSocial");
    }
    @Override
    public int compareTo(final Empresa other) {
        return ObjectContracts.compare(this, other, "empresaRazonSocial");
    }

    //endregion

    //accion
    @ActionLayout(named="Listar todos los empresas")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @MemberOrder(sequence = "2")
    public List<Empresa> listar() {
        return empresasRepository.listar();
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
    EmpresaRepository empresasRepository;

    //endregion

}