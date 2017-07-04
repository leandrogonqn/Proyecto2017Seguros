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

import java.util.Date;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Clientes"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="clienteId")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE clienteNombre.indexOf(:clienteNombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE clienteActivo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE clienteActivo == false "),
        @javax.jdo.annotations.Query(
                name = "buscarPorDNI", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE clienteDni == :clienteDni"),
})
@javax.jdo.annotations.Unique(name="Clientes_clienteCuitCuil_UNQ", members = {"clienteCuitCuil"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Clientes implements Comparable<Clientes> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Cliente: {clienteNombre}", "clienteNombre", getClienteNombre());
    }
    //endregion

    //region > constructor
    public Clientes(final String clienteNombre) {
        setClienteNombre(clienteNombre);
    }
    
    public Clientes(String clienteNombre, String clienteApellido, Sexo clienteSexo, int clienteDni, String clienteDireccion, String clienteTelefono, String clienteMail,
			Date clienteFechaNacimiento, boolean clienteNotificacionCumpleanios) {
		super();
		this.clienteNombre = clienteNombre;
		this.clienteApellido = clienteApellido;
		this.clienteSexo = clienteSexo;
		this.clienteDni = clienteDni;
		this.clienteDireccion = clienteDireccion;
		this.clienteTelefono = clienteTelefono;
		this.clienteMail = clienteMail;
		this.clienteCuitCuil = GenerarCuit.generar(clienteSexo, clienteDni);
		this.clienteFechaNacimiento = clienteFechaNacimiento;
		this.clienteNotificacionCumpleanios = clienteNotificacionCumpleanios;
		this.clienteActivo = true;
	}


	//endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String clienteNombre;
    
    public String getClienteNombre() {
        return clienteNombre;
    }
    public void setClienteNombre(final String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
    
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String clienteApellido;
    public String getClienteApellido() {
		return clienteApellido;
	}
	public void setClienteApellido(String clienteApellido) {
		this.clienteApellido = clienteApellido;
	}
	 @javax.jdo.annotations.Column(allowsNull = "false")
	private Sexo clienteSexo;

    public Sexo getClienteSexo() {
		return clienteSexo;
	}

	public void setClienteSexo(Sexo clienteSexo) {
		this.clienteSexo = clienteSexo;
	}


	@javax.jdo.annotations.Column(allowsNull = "false")
    private int clienteDni;

    public int getClienteDni() {
		return clienteDni;
	}
	public void setClienteDni(int clienteDni) {
		this.clienteDni = clienteDni;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String clienteDireccion;

    public String getClienteDireccion() {
		return clienteDireccion;
	}
	public void setClienteDireccion(String clienteDireccion) {
		this.clienteDireccion = clienteDireccion;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String clienteTelefono;

    public String getClienteTelefono() {
		return clienteTelefono;
	}
	public void setClienteTelefono(String clienteTelefono) {
		this.clienteTelefono = clienteTelefono;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String clienteMail;

    public String getClienteMail() {
		return clienteMail;
	}
	public void setClienteMail(String clienteMail) {
		this.clienteMail = clienteMail;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false")
    private String clienteCuitCuil;
    @Property(
            editing = Editing.DISABLED
    )
    public String getClienteCuitCuil() {
		return clienteCuitCuil;
	}
	public void setClienteCuitCuil(String clienteCuitCuil) {
		this.clienteCuitCuil = clienteCuitCuil;
	}	
	
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private Date clienteFechaNacimiento;

    public Date getClienteFechaNacimiento() {
		return clienteFechaNacimiento;
	}
	public void setClienteFechaNacimiento(Date clienteFechaNacimiento) {
		this.clienteFechaNacimiento = clienteFechaNacimiento;
	}		
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean clienteNotificacionCumpleanios;

    public boolean getClienteNotificacionCumpleanios() {
		return clienteNotificacionCumpleanios;
	}
	public void setClienteNotificacionCumpleanios(boolean clienteNotificacionCumpleanios) {
		this.clienteNotificacionCumpleanios = clienteNotificacionCumpleanios;
	}	
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean clienteActivo;
//    @Property(
//            editing = Editing.DISABLED
//    )
    public boolean getClienteActivo() {
		return clienteActivo;
	}
	public void setClienteActivo(boolean clienteActivo) {
		this.clienteActivo = clienteActivo;
	}	
	
    //endregion

	
	//region > updateName (action)
//    public static class UpdateNameDomainEvent extends ActionDomainEvent<Clientes> {}
//    @Action(
//            command = CommandReification.ENABLED,
//            publishing = Publishing.ENABLED,
//            semantics = SemanticsOf.IDEMPOTENT,
//            domainEvent = UpdateNameDomainEvent.class
//    )
//    public Clientes updateName(@ParameterLayout(named="clienteNombre") final String clienteNombre) {
//        setClienteNombre(clienteNombre);
//        return this;
//    }
//    public String default0UpdateName() {
//        return getClienteNombre();
//    }
//    public TranslatableString validate0UpdateName(final String name) {
//        return name != null && name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
//    }

    //endregion
    
    //region > delete (action)
    public static class DeleteDomainEvent extends ActionDomainEvent<Clientes> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void borrarCliente() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        setClienteActivo(false);
//        repositoryService.remove(this);
    }
    
//    public void borrarCliente(){
//    	setClienteActivo(false);
//    }

    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "clienteNombre");
    }
    @Override
    public int compareTo(final Clientes other) {
        return ObjectContracts.compare(this, other, "clienteNombre");
    }

    //endregion

    //region > injected dependencies

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    TitleService titleService;

    @javax.inject.Inject
    MessageService messageService;

    //endregion

}
