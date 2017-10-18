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
package domainapp.dom.tiposDeCoberturas;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
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

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoVehiculo.TipoVehiculo;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "TiposDeCoberturas"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tipoDeCoberturaId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.TiposDeCoberturas "
                        + "WHERE tipoDeCoberturaNombre.toLowerCase().indexOf(:tipoDeCoberturaNombre) >= 0 "),

        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.TiposDeCoberturas "
                        + "WHERE tipoDeCoberturaActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.TiposDeCoberturas "
                        + "WHERE tipoDeCoberturaActivo == false ") 
})
@javax.jdo.annotations.Unique(name="TiposDeCoberturas_tipoDeCoberturaNombre_UNQ", members = {"tipoDeCoberturaNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class TipoDeCobertura implements Comparable<TipoDeCobertura> {
	 //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{name}", "name", getTipoDeCoberturaNombre());
    }
    //endregion
    
    public String iconName(){
    	String a;
    	
    	a = getTipoDeCoberturaNombre();
    	
    	return a;
    }

    public String cssClass(){
    	return (getTipoDeCoberturaActivo()==true)? "activo":"inactivo";
    }
    
    public static final int NAME_LENGTH = 200;
    // Constructor
    public TipoDeCobertura(String tipoDeCoberturaNombre) {
		setTipoDeCoberturaNombre(tipoDeCoberturaNombre);
		setTipoDeCoberturaActivo(true);
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
	private String tipoDeCoberturaNombre;
	
    public String getTipoDeCoberturaNombre() {
		return tipoDeCoberturaNombre;
	}
	public void setTipoDeCoberturaNombre(String tipoDeCoberturaNombre) {
		this.tipoDeCoberturaNombre = tipoDeCoberturaNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean tipoDeCoberturaActivo;

    public boolean getTipoDeCoberturaActivo() {
		return tipoDeCoberturaActivo;
	}
	public void setTipoDeCoberturaActivo(boolean tipoDeCoberturaActivo) {
		this.tipoDeCoberturaActivo = tipoDeCoberturaActivo;
	}	
	
    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<TipoDeCobertura> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarTipoDeCobertura() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setTipoDeCoberturaActivo(false);
    }
    
	public TipoDeCobertura actualizarNombre(@ParameterLayout(named="Nombre") final String tipoDeCoberturaNombre){
		setTipoDeCoberturaNombre(tipoDeCoberturaNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getTipoDeCoberturaNombre();
	}
	
	public TipoDeCobertura actualizarActivo(@ParameterLayout(named="Activo") final boolean tipoDeCoberturaActivo){
		setTipoDeCoberturaActivo(tipoDeCoberturaActivo);
		return this;
	}

	public boolean default0ActualizarActivo(){
		return getTipoDeCoberturaActivo();
	}
    
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "tipoDeCoberturaNombre");
    }
    @Override
    public int compareTo(final TipoDeCobertura other) {
        return ObjectContracts.compare(this, other, "tipoDeCoberturaNombre");
    }

    //endregion
    
    //acciones
	  @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Tipos de Coberturas")
	    @MemberOrder(sequence = "2")
	    public List<TipoDeCobertura> listar() {
	        return tiposDeCoberturasRepository.listar();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Tipos de Coberturas Activos")
	    @MemberOrder(sequence = "3")
	    public List<TipoDeCobertura> listarActivos() {
	        return tiposDeCoberturasRepository.listarActivos();
	    }
	    
	    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
	    @ActionLayout(named="Listar Tipos de Coberturas Inactivos")
	    @MemberOrder(sequence = "4")
	    public List<TipoDeCobertura> listarInactivos() {
	        return tiposDeCoberturasRepository.listarInactivos();
	    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    @Inject
    TipoDeCoberturaRepository tiposDeCoberturasRepository;

    //endregion
}
