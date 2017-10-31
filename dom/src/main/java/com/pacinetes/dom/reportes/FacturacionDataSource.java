package com.pacinetes.dom.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FacturacionDataSource implements JRDataSource{
	
private List<FacturacionReporte> listado=new ArrayList<FacturacionReporte>();
	
	private int indiceActual = -1;
	
	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		
		Object valor = null;
		
		if("compania".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getCompania();
			
		}else if ("primaTotal".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPrimaTotal();
		}
		return valor;
	}

	@Override
	public boolean next() throws JRException {
		
		return ++indiceActual<listado.size();
		
	}
	
	public void addParticipante(FacturacionReporte facturacionCompania) {
		this.listado.add(facturacionCompania);
	}


}
