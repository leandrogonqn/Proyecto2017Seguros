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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;

@DomainObjectLayout(named = "Cantidad de Siniestros por Polizas", bookmarking = BookmarkPolicy.AS_ROOT)
@DomainObject(nature = Nature.VIEW_MODEL)
public class SiniestroClienteViewModel implements Comparable<SiniestroClienteViewModel> {

	public TranslatableString title() {
		return TranslatableString.tr("Cliente: {persona}", "persona", getPersona() + ". "
				+ "Poliza : " + getPoliza() + ". Siniestros: " + getSiniestros());
	}

	public SiniestroClienteViewModel() {
	}

	public SiniestroClienteViewModel(Persona persona, Poliza poliza) {
		this.persona = persona;
		this.poliza = poliza;
	}

	@PropertyLayout(named = "Cliente")
	private Persona persona;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@PropertyLayout(named = "Poliza")
	private Poliza poliza;

	public Poliza getPoliza() {
		return poliza;
	}

	public void setPoliza(Poliza poliza) {
		this.poliza = poliza;
	}

	@PropertyLayout(named = "Siniestros")
	public int getSiniestros() {
		return poliza.cantidadDeSiniestrosPorCliente(persona);

	}

	@Override
	public int compareTo(SiniestroClienteViewModel o) {
		if (getSiniestros() < o.getSiniestros()) {
			return -1;
		}
		if (getSiniestros() > o.getSiniestros()) {
			return 1;
		}
		return 0;
	}
}