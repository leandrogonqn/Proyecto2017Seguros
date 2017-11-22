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
package com.pacinetes.dom.polizaintegralcomercio;

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

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PolizaIntegralComercio.class)
public class PolizaIntegralComercioRepository {

	public List<PolizaIntegralComercio> listar() {
		return repositoryService.allInstances(PolizaIntegralComercio.class);
	}

	public PolizaIntegralComercio crear(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompanias, final Date polizaFechaEmision, final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			float riesgoIntegralComercioRCC, final String riesgoIntegralComercioOtrosNombre,
			final double riesgoIntegralComercioOtrosMonto) {
		final PolizaIntegralComercio object = new PolizaIntegralComercio(polizaNumero, polizaCliente, polizaCompanias,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago,
				polizaImporteTotal, riesgoIntegralComercioRobo, riesgoIntegralComercioCristales,
				riesgoIntegralComercioIncendioEdificio, riesgoIntegralComercioIncendioContenido,
				riesgoIntegralComercioRc, riesgoIntegralComercioRcl, riesgoIntegralComercioDanioPorAgua,
				riesgoIntegralComercioRCC, riesgoIntegralComercioOtrosNombre, riesgoIntegralComercioOtrosMonto);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public PolizaIntegralComercio renovacion(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompanias, final Date polizaFechaEmision, final Date polizaFechaVigencia,
			final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago,
			final double polizaImporteTotal, float riesgoIntegralComercioRobo, float riesgoIntegralComercioCristales,
			float riesgoIntegralComercioIncendioEdificio, float riesgoIntegralComercioIncendioContenido,
			float riesgoIntegralComercioRc, float riesgoIntegralComercioRcl, float riesgoIntegralComercioDanioPorAgua,
			float riesgoIntegralComercioRCC, final String riesgoIntegralComercioOtrosNombre,
			final double riesgoIntegralComercioOtrosMonto, final Poliza riesgoAutomotor) {
		final PolizaIntegralComercio object = new PolizaIntegralComercio(polizaNumero, polizaCliente, polizaCompanias,
				polizaFechaEmision, polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago,
				polizaImporteTotal, riesgoIntegralComercioRobo, riesgoIntegralComercioCristales,
				riesgoIntegralComercioIncendioEdificio, riesgoIntegralComercioIncendioContenido,
				riesgoIntegralComercioRc, riesgoIntegralComercioRcl, riesgoIntegralComercioDanioPorAgua,
				riesgoIntegralComercioRCC, riesgoIntegralComercioOtrosNombre, riesgoIntegralComercioOtrosMonto,
				riesgoAutomotor);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
