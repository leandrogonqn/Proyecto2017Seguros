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

package domainapp.fixture.dom.cliente;

import java.util.Date;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.cliente.Clientes;
import domainapp.dom.cliente.ClientesMenu;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.cliente.TipoDocumento;
import domainapp.dom.localidad.Localidades;




public class ClienteCreate extends FixtureScript {

    //region > name (input)
    private String name;
    /**
     * Name of the object (required)
     */
    public String getName() {
        return name;
    }

    public ClienteCreate setName(final String name) {
        this.name = name;
        return this;
    }
    
    private String apellido;
    
    public String getApellido(){
    	return apellido;
    }
    
    public ClienteCreate setApellido(final String apellido){
    	this.apellido = apellido;
    	return this;
    }
    
    private TipoDocumento tipoDocumento;
    
    public TipoDocumento getTipoDocumento(){
    	return tipoDocumento;
    }
    
    public ClienteCreate setTipoDocumento(final TipoDocumento tipoDocumento){
    	this.tipoDocumento = tipoDocumento;
    	return this;
    }
    
    private int dni;
    
    public int getDni() {
		return dni;
	}
    
    public ClienteCreate setDni(final int dni){
    	this.dni = dni;
    	return this;
    }
    
    private Sexo sexo;
    
    public Sexo getSexo(){
    	return sexo;
    }
    
    public ClienteCreate setSexo(final Sexo sexo){
    	this.sexo = sexo;
    	return this;
    }
    
    private Localidades localidad;
    
    public Localidades getLocalidad(){
    	return localidad;
    }
    
    public ClienteCreate setLocalidad(final Localidades localidad){
    	this.localidad = localidad;
    	return this;
    }
    
    private String direccion;

   	public String getDireccion() {
   		return direccion;
   	}

   	public ClienteCreate setDireccion(String direccion) {
   		this.direccion = direccion;
   		return this;
   	}
   	
   	private String telefono;
   	public String getTelefono() {
   		return telefono;
   	}

   	public void setTelefono(String telefono) {
   		this.telefono = telefono;
   	}
   	
   	private String email;

   	public String getEmail() {
   		return email;
   	}

   	public ClienteCreate setEmail(String email) {
   		this.email = email;
   		return this;
   	}
   	
   	private Date fechaNacimiento;

   	public Date getFechaNacimiento() {
   		return fechaNacimiento;
   	}

   	public ClienteCreate setFechaNacimiento(Date fechaNacimiento) {
   		this.fechaNacimiento = fechaNacimiento;
   		return this;
   	}
    
    //endregion


    

	//region > simpleObject (output)
    private Clientes simpleObject;

    /**
     * The created simple object (output).
     * @return
     */
    public Clientes getSimpleObject() {
        return simpleObject;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);
        String apellido = checkParam("apellido", ec, String.class);
        int dni = checkParam("dni", ec, Integer.class);

        this.simpleObject = wrap(simpleObjectMenu).crear(name,apellido,TipoDocumento.DNI,dni,sexo,localidad.listarActivos().get(0),direccion,telefono,email,fechaNacimiento,true);

        // also make available to UI
        ec.addResult(this, simpleObject);
    }

    @javax.inject.Inject
    private ClientesMenu simpleObjectMenu;

}
