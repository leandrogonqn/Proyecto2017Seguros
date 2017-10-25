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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ClientesActivosDataSource implements JRDataSource{
	
private List<ClientesActivosReporte> listado=new ArrayList<ClientesActivosReporte>();
	
	private int indiceActual = -1;
	
	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		
		Object valor = null;
		
		if("clienteNombre".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getClienteNombre();
			
		}else if ("clienteApellido".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getClienteApellido();
			
		}else if ("clienteDni".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getClienteDni();			
		}else if ("clienteTipoDocumento".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getClienteTipoDocumento();			
		}else if ("personaTelefono".equals(jrf.getName())){
					
					valor=listado.get(indiceActual).getPersonaTelefono();			
		}else if ("personaMail".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getPersonaMail();			
		}
		return valor;
	}

	@Override
	public boolean next() throws JRException {
		
		return ++indiceActual<listado.size();
		
	}
	
	public void addParticipante(ClientesActivosReporte clientesActivos) {
		this.listado.add(clientesActivos);
	}

}
