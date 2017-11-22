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
package com.pacinetes.dom.persona;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.mail.Mail;

@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(
	        name = "listarActivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.pacinetes.dom.simple.Clientes "
	                + "WHERE personaActivo == true "),
	@javax.jdo.annotations.Query(
	        name = "listarInactivos", language = "JDOQL",
	        value = "SELECT "
	                + "FROM com.pacinetes.dom.simple.Clientes "
	                + "WHERE personaActivo == false ")
})
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="personaId")
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Unique(name="Persona_peronaCuitCuil_UNQ", members = {"personaCuitCuil"})
public abstract class Persona {
	
    public String cssClass(){
    	return (getPersonaActivo()==true)? "activo":"inactivo";
    }
	
	public static final int NAME_LENGTH = 40;

	@javax.jdo.annotations.Column(allowsNull = "false", name="localidadId")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Localidad")
    private Localidad personaLocalidad;

	public Localidad getPersonaLocalidad() {
		return personaLocalidad;
	}

	public void setPersonaLocalidad(Localidad personaLocalidad) {
		this.personaLocalidad = personaLocalidad;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Direccion")
    private String personaDireccion;

    public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}	

    @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Telefono")
    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	

    @javax.jdo.annotations.Column(allowsNull = "true", length = NAME_LENGTH)
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Mail")
    private String personaMail;

    public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}	

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Cuit/Cuil")
    private String personaCuitCuil;
    public String getPersonaCuitCuil() {
		return personaCuitCuil;
	}
	public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
            editing = Editing.DISABLED
    )
    @PropertyLayout(named="Activo")
    private boolean personaActivo;
 
    public boolean getPersonaActivo() {
		return personaActivo;
	}
	public void setPersonaActivo(boolean personaActivo) {
		this.personaActivo = personaActivo;
	}	
	
	@ActionLayout(named="Enviar Mail", cssClassFa="fa-envelope")
	public Persona enviarMail(
			@ParameterLayout(named="Asunto") final String asunto,
			@ParameterLayout(named="Mensaje", multiLine=10) final String mensaje){
		Mail.enviarMail(this, asunto, mensaje);
		return this;
	}
	
	@Inject
	PersonaRepository personaRepository;

}
