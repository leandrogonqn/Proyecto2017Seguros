package com.pacinetes.dom.reportes;

import java.math.BigDecimal;

public class FacturacionReporte {
	
	private String compania;
	
	public String getCompania() {
		return compania;
	}

	public void setCompania(String compania) {
		this.compania = compania;
	}

	private BigDecimal primaTotal;

	public BigDecimal getPrimaTotal() {
		return primaTotal;
	}

	public void setPrimaTotal(BigDecimal primaTotal) {
		this.primaTotal = primaTotal;
	}
	

}
