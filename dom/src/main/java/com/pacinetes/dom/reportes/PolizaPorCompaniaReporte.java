package com.pacinetes.dom.reportes;


import java.util.Date;

import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.persona.Persona;

public class PolizaPorCompaniaReporte {
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

	public void setPolizasCliente(String string) {
		this.polizaCliente = string;
	}
	
	private String polizaCompania;

	public String getPolizaCompania() {
		return polizaCompania;
	}

	public void setPolizasCompania(String polizaCompania) {
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
	
	private double polizaImporteTotal; 
	
	public double getPolizaImporteTotal() {
		return polizaImporteTotal;
	}

	public void setPolizaImporteTotal(double polizaImporteTotal) {
		this.polizaImporteTotal = polizaImporteTotal;
	}
}
