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

import java.util.Date;
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

public abstract class PolizaReporte{
	
	private String polizaNumero;
	
    public String getPolizaNumero() {
		return polizaNumero;
	}

	public void setPolizaNumero(String polizaNumero) {
		this.polizaNumero = polizaNumero;
	}
	
	private String polizaCliente;
	
	public String getPolizaCliente() {
		return polizaCliente;
	}

	public void setPolizaCliente(String polizaCliente) {
		this.polizaCliente = polizaCliente;
	}

	private String polizaCompania;

	public String getPolizaCompania() {
		return polizaCompania;
	}

	public void setPolizaCompania(String polizaCompania) {
		this.polizaCompania = polizaCompania;
	}

	private Date polizaFechaEmision;
	
	public Date getPolizaFechaEmision() {
		return polizaFechaEmision;
	}

	public void setPolizaFechaEmision(Date polizaFechaEmision) {
		this.polizaFechaEmision = polizaFechaEmision;
	}

	private Date polizaFechaVigencia;
	
	public Date getPolizaFechaVigencia() {
		return polizaFechaVigencia;
	}

	public void setPolizaFechaVigencia(Date polizaFechaVigencia) {
		this.polizaFechaVigencia = polizaFechaVigencia;
	}
	
	private Date polizaFechaVencimiento;
	
	public Date getPolizaFechaVencimiento() {
		return polizaFechaVencimiento;
	}

	public void setPolizaFechaVencimiento(Date polizaFechaVencimiento) {
		this.polizaFechaVencimiento = polizaFechaVencimiento;
	}
	
	private TipoPago polizaTipoDePago;

	public TipoPago getPolizaTipoDePago() {
		return polizaTipoDePago;
	}

	public void setPolizaTipoDePago(TipoPago polizaTipoDePago) {
		this.polizaTipoDePago = polizaTipoDePago;
	}
	
	private DetalleTipoPago polizaPago;

	public DetalleTipoPago getPolizaPago() {
		return polizaPago;
	}

	public void setPolizaPago(DetalleTipoPago polizaPago) {
		this.polizaPago = polizaPago;
	}
	
	private Date polizaFechaBaja;
	
	public Date getPolizaFechaBaja() {
		return polizaFechaBaja;
	}

	public void setPolizaFechaBaja(Date polizaFechaBaja) {
		this.polizaFechaBaja = polizaFechaBaja;
	}

	private double polizaImporteTotal; 
	
	public double getPolizaImporteTotal() {
		return polizaImporteTotal;
	}

	public void setPolizaImporteTotal(double polizaImporteTotal) {
		this.polizaImporteTotal = polizaImporteTotal;
	}
	
	protected String polizaEstado; 
	
	public String getPolizaEstado() {
		return polizaEstado;
	}

	public void setPolizaEstado(String polizaEstado) {
		this.polizaEstado = polizaEstado;
	}	
	
}
