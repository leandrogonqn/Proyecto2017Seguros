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
package com.pacinetes.dom.localidad;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.provincia.Provincia;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Localidad.class)
public class LocalidadRepository {

	public List<Localidad> listar() {
		return repositoryService.allInstances(Localidad.class);
	}

	public List<Localidad> buscarPorNombre(final String localidadNombre) {

		return repositoryService.allMatches(new QueryDefault<>(Localidad.class, "buscarPorNombre", "localidadesNombre",
				localidadNombre.toLowerCase()));

	}

	public List<Localidad> listarActivos() {
		return repositoryService.allMatches(new QueryDefault<>(Localidad.class, "listarActivos"));
	}

	public List<Localidad> listarInactivos() {
		return repositoryService.allMatches(new QueryDefault<>(Localidad.class, "listarInactivos"));
	}

	public Localidad crear(final String localidadNombre, Provincia localidadProvincia) {
		final Localidad object = new Localidad(localidadNombre, localidadProvincia);
		serviceRegistry.injectServicesInto(object);
		repositoryService.persist(object);
		return object;
	}

	@javax.inject.Inject
	RepositoryService repositoryService;
	@javax.inject.Inject
	ServiceRegistry2 serviceRegistry;
}
