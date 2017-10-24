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
package com.pacinetes.dom.poliza;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.vehiculo.Vehiculo;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Poliza.class
)
public class PolizaRepository {

    public List<Poliza> listar() {
        return repositoryService.allInstances(Poliza.class);
    }

    public List<Poliza> buscarpolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorNumeroPoliza",
                        "polizaNumero", polizaNumero.toLowerCase()));
    }
    
    public List<Poliza> listarPorEstado(final Estado polizaEstado) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Poliza.class,
                        "listarPorEstado",
                        "polizaEstado", polizaEstado));
    }
    
    public List<Poliza> buscarPorCliente(final Persona polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
    
    public List<Poliza> listarPolizasPorVencimiento(){
    	List<Poliza> listaPolizas = listarPorEstado(Estado.vigente);
    	Iterator<Poliza> it = listaPolizas.iterator();
    	while (it.hasNext()) {
			Poliza lista = it.next();
			if (lista.polizaRenovacion != null) {
				it.remove();
			}
		}
    	Collections.sort(listaPolizas);
    	return listaPolizas;
    }
  
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
