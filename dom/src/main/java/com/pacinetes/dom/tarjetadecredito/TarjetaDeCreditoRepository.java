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
package com.pacinetes.dom.tarjetadecredito;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.banco.Banco;
import com.pacinetes.dom.debitoautomatico.DebitoAutomatico;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.tipotarjeta.TipoTarjeta;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TarjetaDeCredito.class
)
public class TarjetaDeCreditoRepository {

    public TarjetaDeCredito crear(String tipoPagoTitular, TipoTarjeta tipoTarjeta, Banco banco, long tarjetaDeCreditoNumero, int tarjetaDeCreditoMesVencimiento, int tarjetaDeCreditoAnioVencimiento) {
        final TarjetaDeCredito object = new TarjetaDeCredito(tipoPagoTitular, tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    public List<TarjetaDeCredito> listar() {
        return repositoryService.allInstances(TarjetaDeCredito.class);
    }
    
	public List<TarjetaDeCredito> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  TarjetaDeCredito.class,
	                  "listarActivos"));
	  }
	
	public List<TarjetaDeCredito> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  TarjetaDeCredito.class,
	                  "listarInactivos"));
	  }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
