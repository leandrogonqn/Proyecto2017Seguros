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
package com.pacinetes.app.viewmodel;

import java.util.Date;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

import com.pacinetes.dom.cliente.Cliente;

@DomainObjectLayout(named = "Lista de clientes que estan por cumplir años", bookmarking = BookmarkPolicy.AS_ROOT)
@DomainObject(nature = Nature.VIEW_MODEL)
public class ClientesCumpleañosViewModel implements Comparable<ClientesCumpleañosViewModel> {

	public ClientesCumpleañosViewModel() {
	}

	public ClientesCumpleañosViewModel(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public TranslatableString title() {
		return TranslatableString.tr("Cliente: {clienteNombre}", "clienteNombre",
				getCliente() + ". Días restantes: " + getDiasRestantes());
	}

	public String cssClass() {
		String a = null;
		if (this.getDiasRestantes() > 5) {
			a = "vigenteSinRenovacionConVencimientoMayor30Dias";
		} else {
			if (this.getDiasRestantes() > 2) {
				a = "vigenteSinRenovacionConVencimientoMenor30Dias";
			} else {
				a = "vigenteSinRenovacionConVencimientoMenor15Dias";
			}
		}
		return a;
	}

	@PropertyLayout(named = "Cliente")
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@PropertyLayout(named = "Fecha de Nacimiento")
	public Date getFechaNacimiento() {
		return cliente.getClienteFechaNacimiento();
	}

	@PropertyLayout(named = "Días restantes")
	public long getDiasRestantes() {
		return cliente.calcularDiasRestantesParaCumpleaños();
	}

	@Override
	public int compareTo(ClientesCumpleañosViewModel o) {
		if (getDiasRestantes() < o.getDiasRestantes()) {
			return -1;
		}
		if (getDiasRestantes() > o.getDiasRestantes()) {
			return 1;
		}
		return 0;
	}
}
