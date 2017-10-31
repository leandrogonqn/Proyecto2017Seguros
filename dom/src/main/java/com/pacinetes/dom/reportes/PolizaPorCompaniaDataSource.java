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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class PolizaPorCompaniaDataSource implements JRDataSource{
	
private List<PolizaPorCompaniaReporte> listado=new ArrayList<PolizaPorCompaniaReporte>();
	
	private int indiceActual = -1;
	
	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		
		Object valor = null;
		
		if("polizaNumero".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPolizaNumero();
			
		}else if ("polizaCliente".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPolizaCliente();
			
		}else if ("polizaFechaEmision".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPolizaFechaEmision();			
		}else if ("polizaFechaVigencia".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPolizaFechaVigencia();			
		}else if ("polizaFechaVencimiento".equals(jrf.getName())){
					
			valor=listado.get(indiceActual).getPolizaFechaVencimiento();			
		}else if ("polizaImporteTotal".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPolizaImporteTotal();			
		}
		return valor;
	}

	@Override
	public boolean next() throws JRException {
		
		return ++indiceActual<listado.size();
		
	}
	
	public void addParticipante(PolizaPorCompaniaReporte polizaPorCompania) {
		this.listado.add(polizaPorCompania);
	}

}
