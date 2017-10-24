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
package com.pacinetes.dom.adjunto;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;

import com.pacinetes.dom.marca.Marca;
import com.pacinetes.dom.poliza.Poliza;

@PersistenceCapable(schema="simple")
@DatastoreIdentity(
		strategy=IdGeneratorStrategy.IDENTITY)
@Queries({
    @javax.jdo.annotations.Query(
            name = "listarActivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.Adjunto "
                    + "WHERE adjuntoActivo == true "),
    @javax.jdo.annotations.Query(
            name = "listarInactivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.Adjunto "
                    + "WHERE adjuntoActivo == false "),
    @javax.jdo.annotations.Query(
            name = "buscarPorDescripcion", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.Adjunto "
                    + "WHERE adjuntoDescripcion.toLowerCase().indexOf(:adjuntoDescripcion) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Adjunto_adjuntoDescripcion_UNQ", members = {"adjuntoDescripcion"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Adjunto{
	
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getAdjuntoDescripcion());
    }
    
    public String cssClass(){
    	return (getAdjuntoActivo()==true)? "activo":"inactivo";
    }
	
	//Constructor
	public Adjunto (String adjuntoDescripcion, Blob imagen){
		setAdjuntoDescripcion(adjuntoDescripcion);
		setImagen(imagen);
		setAdjuntoActivo(true);
	}
	
	@Column(allowsNull="true")
	@Property(editing=Editing.ENABLED)
	@PropertyLayout(named="Descripcion")
	private String adjuntoDescripcion;
	
	public String getAdjuntoDescripcion(){
		return adjuntoDescripcion;
	}
	
	public void setAdjuntoDescripcion(String adjuntoDescripcion){
		this.adjuntoDescripcion = adjuntoDescripcion;
	}
	
	@Column(allowsNull="false")
	@javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
	        @javax.jdo.annotations.Column(name = "someImage_name"),
	        @javax.jdo.annotations.Column(name = "someImage_mimetype"),
	        @javax.jdo.annotations.Column(name = "someImage_bytes", jdbcType = "BLOB", sqlType = "LONGVARBINARY")
	})
	@Property(editing=Editing.ENABLED)
	@PropertyLayout(named="Imagen")
	private Blob imagen;
	
	public Blob getImagen(){
		return imagen;
	}
	public void setImagen(Blob imagen){
		this.imagen = imagen;
	}
	
	@Column(allowsNull="false")
	@Property(editing=Editing.ENABLED)
	@PropertyLayout(named="Activo")
	private boolean adjuntoActivo;
	
	public boolean getAdjuntoActivo(){
		return adjuntoActivo;
	}
	
	public void setAdjuntoActivo(boolean adjuntoActivo){
		this.adjuntoActivo = adjuntoActivo;
	}
	
    @Action(
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarAdjunto(){
    	final String title = titleService.titleOf(this);
    	messageService.informUser(String.format("'%s' deleted", title));
    	setAdjuntoActivo(false);
    }
    
    
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar todos los Adjuntos")
	    @MemberOrder(sequence = "2")
	    public List<Adjunto> listar() {
	        return adjuntoRepository.listar();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Adjuntos Activos")
	    @MemberOrder(sequence = "3")
	    public List<Adjunto> listarActivos() {
	        return adjuntoRepository.listarActivos();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Adjuntos Inactivos")
	    @MemberOrder(sequence = "4")
	    public List<Adjunto> listarInactivos() {
	        return adjuntoRepository.listarInactivos();
	    }
    
    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    AdjuntoRepository adjuntoRepository;
	
}
