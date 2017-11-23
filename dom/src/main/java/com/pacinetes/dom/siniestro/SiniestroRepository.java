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
package com.pacinetes.dom.siniestro;

import java.util.Date;
import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import com.pacinetes.dom.poliza.Poliza;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Siniestro.class)
public class SiniestroRepository {

	public List<Siniestro> listar() {
		return repositoryService.allInstances(Siniestro.class);
	}

	public List<Siniestro> buscarPorNombre(final String siniestroDescripcion) {
		return repositoryService.allMatches(new QueryDefault<>(Siniestro.class, "findByName", "siniestroDescripcion",
				siniestroDescripcion.toLowerCase()));
	}

	public List<Siniestro> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Siniestro.class, "listarActivos"));
	}

	public List<Siniestro> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Siniestro.class, "listarInactivos"));
	}

	public Siniestro crear(final String siniestroDescripcion, Poliza siniestroPoliza, Date siniestroFecha) {
		final Siniestro object = new Siniestro(siniestroDescripcion, siniestroPoliza, siniestroFecha);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
