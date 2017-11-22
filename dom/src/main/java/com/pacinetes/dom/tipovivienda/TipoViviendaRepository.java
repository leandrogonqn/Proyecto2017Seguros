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
package com.pacinetes.dom.tipovivienda;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = TipoVivienda.class)
public class TipoViviendaRepository {

	public List<TipoVivienda> listar() {
		return repositoryService.allInstances(TipoVivienda.class);
	}

	public List<TipoVivienda> buscarPorNombre(final String tipoViviendaNombre) {
		return repositoryService.allMatches(new QueryDefault<>(TipoVivienda.class, "findByName", "tipoViviendaNombre",
				tipoViviendaNombre.toLowerCase()));
	}

	public List<TipoVivienda> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoVivienda.class, "listarActivos"));
	}

	public List<TipoVivienda> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(TipoVivienda.class, "listarInactivos"));
	}

	public TipoVivienda crear(final String tipoViviendaNombre) {
		final TipoVivienda object = new TipoVivienda(tipoViviendaNombre);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
