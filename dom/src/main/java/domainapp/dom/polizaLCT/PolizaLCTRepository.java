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
package domainapp.dom.polizaLCT;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.ocupacion.Ocupacion;
import domainapp.dom.persona.Persona;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.polizaAutomotor.PolizaAutomotor;
import domainapp.dom.tipoTitular.TipoTitular;
import domainapp.dom.tipoVivienda.TipoVivienda;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;
import domainapp.dom.vehiculo.Vehiculo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaLCT.class
)
public class PolizaLCTRepository {

    public List<PolizaLCT> listar() {
        return repositoryService.allInstances(PolizaLCT.class);
    }

	public PolizaLCT crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto) {
		final PolizaLCT object = new PolizaLCT(polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, riesgoLCTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaLCT renovacion(
    		final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal, final float riesgoLCTMonto,
    		Poliza riesgoLCT) {
        final PolizaLCT object = new PolizaLCT(
        		polizaNumero, polizaCliente, polizaCompania, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal, 
				riesgoLCT, riesgoLCTMonto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

