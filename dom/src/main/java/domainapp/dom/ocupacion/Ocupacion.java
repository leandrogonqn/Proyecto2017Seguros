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
package domainapp.dom.ocupacion;

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
import org.datanucleus.enhancer.methods.GetTransactionalObjectId;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Ocupaciones"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="ocupacionId")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionNombre.toLowerCase().indexOf(:ocupacionNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.ocupacion "
                        + "WHERE ocupacionActivo == false ") 
})
@javax.jdo.annotations.Unique(name="ocupaciones_ocupacionNombre_UNQ", members = {"ocupacionNombre"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Ocupacion implements Comparable<Ocupacion>{

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Tipo: {name}", "name", getOcupacionNombre());
    }
    //endregion

    
    //region > constructor
    public Ocupacion(final String ocupacionNombre) {
    	setOcupacionNombre(ocupacionNombre);
    	setOcupacionActivo(true);
    }
    //endregion
    
    public String cssClass(){
    	return (isOcupacionActivo()==true)? "activo":"inactivo";
    }

	//region > name (read-only property)
    public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Nombre")
    private String ocupacionNombre;
	
    public String getOcupacionNombre() {
		return ocupacionNombre;
	}

	public void setOcupacionNombre(String ocupacionNombre) {
		this.ocupacionNombre = ocupacionNombre;
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
	private boolean ocupacionActivo;
	
	public boolean isOcupacionActivo() {
		return ocupacionActivo;
	}

	public void setOcupacionActivo(boolean ocupacionActivo) {
		this.ocupacionActivo = ocupacionActivo;
	}

    //endregion

    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Ocupacion> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    
    public void borrarOcupacion() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setOcupacionActivo(false);
    }
    
	public Ocupacion actualizarNombre(@ParameterLayout(named="Nombre") final String ocupacionNombre){
		setOcupacionNombre(ocupacionNombre);
		return this;
	}
	
	public String default0ActualizarNombre(){
		return getOcupacionNombre();
	}
	
    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "ocupacionNombre");
    }
    @Override
    public int compareTo(final Ocupacion other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion
    
    //acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Ocupaciones")
    @MemberOrder(sequence = "2")
    public List<Ocupacion> listar() {
        return ocupacionesRepository.listar();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Ocupaciones Activas")
    @MemberOrder(sequence = "3")
    public List<Ocupacion> listarActivos() {
        return ocupacionesRepository.listarActivos();
    }
    
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Ocupaciones Inactivas")
    @MemberOrder(sequence = "4")
    public List<Ocupacion> listarInactivos() {
        return ocupacionesRepository.listarInactivos();
    }

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;
    
    @Inject
    OcupacionRepository ocupacionesRepository;

    //endregion
}
