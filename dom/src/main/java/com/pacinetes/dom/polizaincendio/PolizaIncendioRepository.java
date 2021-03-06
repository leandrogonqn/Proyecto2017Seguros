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
package com.pacinetes.dom.polizaincendio;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PolizaIncendio.class)
public class PolizaIncendioRepository {

	public List<PolizaIncendio> listar() {
		return repositoryService.allInstances(PolizaIncendio.class);
	}

	public PolizaIncendio crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompania,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal,
			final float riesgoIncendioMonto) {
		final PolizaIncendio object = new PolizaIncendio(polizaNumero, polizaCliente, polizaCompania,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago,
				polizaImporteTotal, riesgoIncendioMonto);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public PolizaIncendio renovacion(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompania, final Date polizaFechaEmision, final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, final float riesgoIncendioMonto, Poliza riesgoIncendio) {
		final PolizaIncendio object = new PolizaIncendio(polizaNumero, polizaCliente, polizaCompania,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago,
				polizaImporteTotal, riesgoIncendio, riesgoIncendioMonto);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
