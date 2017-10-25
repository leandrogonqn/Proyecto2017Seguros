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
package com.pacinetes.dom.reportes;

import com.pacinetes.dom.persona.Persona;

public class EmpresasActivasReporte extends Persona {
	
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

    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
	
    private String personaMail;

    public String getPersonaMail() {
		return personaMail;
	}
	public void setPersonaMail(String personaMail) {
		this.personaMail = personaMail;
	}
}
