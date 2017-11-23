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
import java.util.Date;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;

import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;

@DomainObjectLayout(named = "Vencimiento Polizas por Compañia", bookmarking = BookmarkPolicy.AS_ROOT)
@DomainObject(nature = Nature.VIEW_MODEL)
public class VencimientosPolizaCompaniaViewModel implements Comparable<VencimientosPolizaCompaniaViewModel> {

	public VencimientosPolizaCompaniaViewModel() {
	}

	public VencimientosPolizaCompaniaViewModel(Poliza poliza, Compania compania) {
		this.compania = compania;
		this.poliza = poliza;
	}

	public VencimientosPolizaCompaniaViewModel(Poliza poliza) {
		this.poliza = poliza;
		this.compania = poliza.getPolizaCompania();
	}
	
	public TranslatableString title() {
		return TranslatableString.tr("Cliente: {persona}", "persona", getCliente() + ". "
				+ "Poliza : " + getPoliza() + ". Días para el vencimiento: " + getDiasRestantes());
	}

	public String cssClass() {

		String a = null;

		if (this.getDiasRestantes() > 30) {
			a = "vigenteSinRenovacionConVencimientoMayor30Dias";
		} else {
			if (this.getDiasRestantes() > 15) {
				a = "vigenteSinRenovacionConVencimientoMenor30Dias";
			} else {
				a = "vigenteSinRenovacionConVencimientoMenor15Dias";
			}
		}

		return a;
	}

	@PropertyLayout(named = "Compania")
	private Compania compania;

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	@PropertyLayout(named = "Cliente")
	public Persona getCliente() {
		return poliza.getPolizaCliente();
	}

	@PropertyLayout(named = "Poliza")
	private Poliza poliza;

	@Title
	public Poliza getPoliza() {
		return poliza;
	}

	public void setPoliza(Poliza poliza) {
		this.poliza = poliza;
	}

	@PropertyLayout(named = "Fecha Vencimiento")
	public Date getFechaVencimiento() {
		return poliza.getPolizaFechaVencimiento();
	}

	@PropertyLayout(named = "Dias para el vencimiento")
	public long getDiasRestantes() {
		return poliza.contarCantidadDiasHastaVencimiento(compania);
	}

	@Override
	public int compareTo(VencimientosPolizaCompaniaViewModel o) {
		if (getDiasRestantes() < o.getDiasRestantes()) {
			return -1;
		}
		if (getDiasRestantes() > o.getDiasRestantes()) {
			return 1;
		}
		return 0;
	}

}