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
package com.pacinetes.dom.empresa;

import java.io.IOException;
import java.util.ArrayList;
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
import org.apache.isis.applib.value.Blob;

import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.localidad.LocalidadRepository;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.provincia.Provincia;
import com.pacinetes.dom.reportes.EmpresaReporte;
import com.pacinetes.dom.reportes.GenerarReporte;
import com.pacinetes.dom.reportes.ReporteRepository;

import net.sf.jasperreports.engine.JRException;

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
		                + "FROM com.pacinetes.dom.simple.Empresas "
		                + "WHERE empresaRazonSocial.toLowerCase().indexOf(:empresaRazonSocial) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.Empresas "
                        + "WHERE personaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM com.pacinetes.dom.simple.Empresas "
                        + "WHERE personaActivo == false ")
		})
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
    
    public Blob imprimirEmpresa() throws JRException, IOException{
    	
		List<Object> objectsReport = new ArrayList<Object>();
		
		EmpresaReporte empresaReporte = new EmpresaReporte();
		
		empresaReporte.setEmpresaRazonSocial(getEmpresaRazonSocial());
		empresaReporte.setPersonaLocalidad(getPersonaLocalidad().getLocalidadesNombre());
		empresaReporte.setPersonaProvincia(getPersonaLocalidad().getLocalidadProvincia().getProvinciasNombre().toString());
		empresaReporte.setPersonaCuitCuil(getPersonaCuitCuil());
		empresaReporte.setPersonaDireccion(getPersonaDireccion());
		empresaReporte.setPersonaTelefono(getPersonaTelefono());
		
		objectsReport.add(empresaReporte);
		String jrxml = "Empresa.jrxml";
		String nombreArchivo = "Empresa_"+getEmpresaRazonSocial()+"_"+getPersonaCuitCuil();
		
		return reporteRepository.imprimirReporteIndividual(objectsReport,jrxml, nombreArchivo);
    }
	
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
    	return getEmpresaRazonSocial();
    }
    @Override
    public int compareTo(final Empresa other) {
        return ObjectContracts.compare(this, other, "empresaRazonSocial");
    }

    //endregion

    //accion
    @ActionLayout(named="Listar todos los empresas")
    @MemberOrder(sequence = "2")
    public List<Empresa> listar() {
        return empresasRepository.listar();
    }
    
    @ActionLayout(named="Listar Clientes Activos")
    @MemberOrder(sequence = "3")
    public List<Empresa> listarEmpresaActivos() {
        return empresasRepository.listarActivos();
    }
    
    @ActionLayout(named="Listar Clientes Inactivos")
    @MemberOrder(sequence = "4")
    public List<Empresa> listarEmpresaInactivos() {
        return empresasRepository.listarInactivos();
    }
    
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
    
    @Inject
    ReporteRepository reporteRepository;

    //endregion

}
