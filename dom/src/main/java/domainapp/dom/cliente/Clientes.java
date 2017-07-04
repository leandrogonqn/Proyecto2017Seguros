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
import org.apache.isis.applib.annotation.Parameter;
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
         column="id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "listarActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE activo == true "),
        @javax.jdo.annotations.Query(
                name = "listarInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE activo == false "),
        @javax.jdo.annotations.Query(
                name = "buscarPorDNI", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Clientes "
                        + "WHERE dni == :dni"),
})
@javax.jdo.annotations.Unique(name="Clientes_cuitcuil_UNQ", members = {"cuitcuil"})
@DomainObject(
        publishing = Publishing.ENABLED,
        auditing = Auditing.ENABLED
)
public class Clientes implements Comparable<Clientes> {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("Cliente: {nombre}", "nombre", getNombre());
    }
    //endregion

    //region > constructor
    public Clientes(final String nombre) {
        setNombre(nombre);
    }
    
    public Clientes(String nombre, String apellido, Sexo sexo, int dni, String direccion, String telefono, String mail,
			Date fechaNacimiento, boolean notificacionCumpleanios) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono = telefono;
		this.mail = mail;
		this.cuitcuil = GenerarCuit.generar(sexo, dni);
		this.fechaNacimiento = fechaNacimiento;
		this.notificacionCumpleanios = notificacionCumpleanios;
		this.activo = true;
	}


	//endregion

    //region > name (read-only property)
    public static final int NAME_LENGTH = 40;

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String nombre;
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String apellido;
    public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	 @javax.jdo.annotations.Column(allowsNull = "false")
	private Sexo sexo;

    public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}


	@javax.jdo.annotations.Column(allowsNull = "false")
    private int dni;

    public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String direccion;

    public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String telefono;

    public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private String mail;
//    @Parameter(
//            maxLength=30,
//            mustSatisfy=EmailSpecification.class,
//
//        )
    public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false")
    private String cuitcuil;
    @Property(
            editing = Editing.DISABLED
    )
    public String getCuitcuil() {
		return cuitcuil;
	}
	public void setCuitcuil(String cuitcuil) {
		this.cuitcuil = cuitcuil;
	}	
	
    @javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    private Date fechaNacimiento;

    public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}		
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean notificacionCumpleanios;

    public boolean getNotificacionCumpleanios() {
		return notificacionCumpleanios;
	}
	public void setNotificacionCumpleanios(boolean notificacionCumpleanios) {
		this.notificacionCumpleanios = notificacionCumpleanios;
	}	
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    private boolean activo;
    @Property(
            editing = Editing.DISABLED
    )
    public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}	
	
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
        setActivo(false);
//        repositoryService.remove(this);
    }
    
//    public void borrarCliente(){
//    	setActivo(false);
//    }

    //endregion

    //region > toString, compareTo
    @Override
    public String toString() {
        return ObjectContracts.toString(this, "name");
    }
    @Override
    public int compareTo(final Clientes other) {
        return ObjectContracts.compare(this, other, "nombre");
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
