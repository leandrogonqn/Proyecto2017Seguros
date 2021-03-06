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
package com.pacinetes.dom.polizaautomotor;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import com.pacinetes.dom.adjunto.Adjunto;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.tipodecobertura.TipoDeCobertura;
import com.pacinetes.dom.vehiculo.Vehiculo;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = PolizaAutomotor.class)
public class PolizaAutomotoresRepository {

	public List<PolizaAutomotor> listar() {
		return repositoryService.allInstances(PolizaAutomotor.class);
	}

	public PolizaAutomotor crear(final String polizaNumero, final Persona polizaCliente, final Compania polizaCompanias,
			final List<Vehiculo> riesgoAutomotorListaVehiculos, final TipoDeCobertura riesgoAutomotorTiposDeCoberturas,
			final Date polizaFechaEmision, final Date polizaFechaVigencia, final Date polizaFechaVencimiento,
			final TipoPago polizaTipoDePago, final DetalleTipoPago polizaPago, final double polizaImporteTotal) {
		final PolizaAutomotor object = new PolizaAutomotor(polizaNumero, polizaCliente, polizaCompanias,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public PolizaAutomotor renovacion(final String polizaNumero, final Persona polizaCliente,
			final Compania polizaCompanias, final List<Vehiculo> riesgoAutomotorListaVehiculos,
			final TipoDeCobertura riesgoAutomotorTiposDeCoberturas, final Date polizaFechaEmision,
			final Date polizaFechaVigencia, final Date polizaFechaVencimiento, final TipoPago polizaTipoDePago,
			final DetalleTipoPago polizaPago, final double polizaImporteTotal,
			final List<Adjunto> riesgoAutomotorAdjunto, final Poliza riesgoAutomotor) {
		final PolizaAutomotor object = new PolizaAutomotor(polizaNumero, polizaCliente, polizaCompanias,
				riesgoAutomotorListaVehiculos, riesgoAutomotorTiposDeCoberturas, polizaFechaEmision,
				polizaFechaVigencia, polizaFechaVencimiento, polizaTipoDePago, polizaPago, polizaImporteTotal,
				riesgoAutomotorAdjunto, riesgoAutomotor);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	public List<PolizaAutomotor> listarPorEstado(final Estado polizaEstado) {
		return repositoryService
				.allMatches(new QueryDefault<>(PolizaAutomotor.class, "listarPorEstado", "polizaEstado", polizaEstado));
	}

	public boolean validar(Vehiculo vehiculo, Date fechaVigencia) {

		boolean validador = true;

		List<PolizaAutomotor> listaPolizas = listarPorEstado(Estado.vigente);
		listaPolizas.addAll(listarPorEstado(Estado.previgente));

		for (PolizaAutomotor r : listaPolizas) {
			for (Vehiculo v : r.getRiesgoAutomotorListaVehiculos()) {
				if (vehiculo.equals(v)) {
					if (fechaVigencia.before(r.getPolizaFechaVencimiento())) {
						validador = false;
					}
				}
			}
		}

		return validador;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
