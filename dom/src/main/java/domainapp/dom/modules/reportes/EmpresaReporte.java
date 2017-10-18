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
package domainapp.dom.modules.reportes;

import java.util.Date;

import domainapp.dom.localidad.Localidad;

public class EmpresaReporte {
	
	private String empresaRazonSocial;
    
    public String getEmpresaRazonSocial() {
        return empresaRazonSocial;
    }
    public void setEmpresaRazonSocial(final String empresaRazonSocial) {
        this.empresaRazonSocial = empresaRazonSocial;
    }
	
	private String personaCuitCuil;
	
	public String getPersonaCuitCuil() {
		return personaCuitCuil;
	}

	public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	}

    private String personaLocalidad;

	public void setPersonaLocalidad(String personaLocalidad) {
		this.personaLocalidad = personaLocalidad;
	}

	public String getPersonaLocalidad() {
		return personaLocalidad;
	}
	
	private String personaProvincia;

	public String getPersonaProvincia() {
		return personaProvincia;
	}

	public void setPersonaProvincia(String personaProvincia) {
		this.personaProvincia = personaProvincia;
	}
	
	private String personaDireccion;

    public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}	

    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
	
    private Date clienteFechaNacimiento;

    public Date getClienteFechaNacimiento() {
		return clienteFechaNacimiento;
	}
	public void setClienteFechaNacimiento(Date clienteFechaNacimiento) {
		this.clienteFechaNacimiento = clienteFechaNacimiento;
	}	
	

}
