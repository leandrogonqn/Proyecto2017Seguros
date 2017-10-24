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
package com.pacinetes.dom.polizacombinadofamiliar;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.adjunto.Adjunto;
import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.ocupacion.Ocupacion;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.polizaautomotor.PolizaAutomotor;
import com.pacinetes.dom.tipodecobertura.TipoDeCobertura;
import com.pacinetes.dom.tipotitular.TipoTitular;
import com.pacinetes.dom.tipovivienda.TipoVivienda;
import com.pacinetes.dom.vehiculo.Vehiculo;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PolizaCombinadoFamiliar.class
)
public class PolizaCombinadoFamiliarRepository {

    public List<PolizaCombinadoFamiliar> listar() {
        return repositoryService.allInstances(PolizaCombinadoFamiliar.class);
    }

	public PolizaCombinadoFamiliar crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final String riesgoCombinadosFamiliaresDomicilio,
			final Localidad riesgoCombinadosFamiliaresLocalidad,
			final Ocupacion riesgoCombinadosFamiliaresOcupacion,
			final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
			final TipoTitular riesgoCombinadosFamiliaresTipoTitular, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, 
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, 
			final double polizaImporteTotal,
    		final List<Adjunto> riesgoAutomotorListaAdjunto) {
		final PolizaCombinadoFamiliar object = new PolizaCombinadoFamiliar(polizaNumero, polizaCliente,
				polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresLocalidad,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
        		polizaPago,
        		polizaImporteTotal, 
        		riesgoAutomotorListaAdjunto);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public PolizaCombinadoFamiliar renovacion(
    		final String polizaNumero, 
    		final Persona polizaCliente, 
    		final Compania polizaCompania,
    		final String riesgoCombinadosFamiliaresDomicilio,
    		final Localidad riesgoCombinadosFamiliaresLocalidad,
    		final Ocupacion riesgoCombinadosFamiliaresOcupacion,
    		final TipoVivienda riesgoCombinadosFamiliaresTipoVivienda,
    		final TipoTitular riesgoCombinadosFamiliaresTipoTitular,
    		final Date polizaFechaEmision, 
    		final Date polizaFechaVigencia, 
    		final Date polizaFechaVencimiento,
    		final TipoPago polizaTipoDePago,
    		final DetalleTipoPago polizaPago, 
    		final double polizaImporteTotal,
    		final List<Adjunto> riesgoAutomotorListaAdjunto,
    		Poliza riesgoCombinadosFamiliares) {
        final PolizaCombinadoFamiliar object = new PolizaCombinadoFamiliar(
        		polizaNumero,
        		polizaCliente,
        		polizaCompania,
        		riesgoCombinadosFamiliaresDomicilio,
        		riesgoCombinadosFamiliaresLocalidad,
        		riesgoCombinadosFamiliaresOcupacion,
        		riesgoCombinadosFamiliaresTipoVivienda,
        		riesgoCombinadosFamiliaresTipoTitular,
        		polizaFechaEmision,
        		polizaFechaVigencia, 
        		polizaFechaVencimiento,
        		polizaTipoDePago,
        		polizaPago,
        		polizaImporteTotal, 
        		riesgoAutomotorListaAdjunto,
        		riesgoCombinadosFamiliares);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}

