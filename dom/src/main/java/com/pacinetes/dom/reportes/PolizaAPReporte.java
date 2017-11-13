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

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Join;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.siniestro.Siniestro;

public class PolizaAPReporte extends PolizaReporte{
	
	private float riesgoAPMuerte; 
	
	public float getRiesgoAPMuerte() {
		return riesgoAPMuerte;
	}

	public void setRiesgoAPMuerte(float riesgoAPMuerte) {
		this.riesgoAPMuerte = riesgoAPMuerte;
	}
	
	private float riesgoAPInvalidez;

	public float getRiesgoAPInvalidez() {
		return riesgoAPInvalidez;
	}

	public void setRiesgoAPInvalidez(float riesgoAPInvalidez) {
		this.riesgoAPInvalidez = riesgoAPInvalidez;
	}

	private float riesgoAPAMF;

	public float getRiesgoAPAMF() {
		return riesgoAPAMF;
	}

	public void setRiesgoAPAMF(float riesgoAPAMF) {
		this.riesgoAPAMF = riesgoAPAMF;
	}

}
