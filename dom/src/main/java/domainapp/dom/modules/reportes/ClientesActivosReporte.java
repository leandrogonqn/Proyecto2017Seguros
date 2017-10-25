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

import domainapp.dom.persona.Persona;

public class ClientesActivosReporte extends Persona {
	
	private String clienteNombre;

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}
	
	private String clienteApellido;

	public String getClienteApellido() {
		return clienteApellido;
	}

	public void setClienteApellido(String clienteApellido) {
		this.clienteApellido = clienteApellido;
	}
	
	public int clienteDni;

	public int getClienteDni() {
		return clienteDni;
	}

	public void setClienteDni(int clienteDni) {
		this.clienteDni = clienteDni;
	}
	
	private String clienteTipoDocumento;
	
	public String getClienteTipoDocumento() {
		return clienteTipoDocumento;
	}

	public void setClienteTipoDocumento(String clienteTipoDocumento) {
		this.clienteTipoDocumento = clienteTipoDocumento;
	}
	
    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
}
