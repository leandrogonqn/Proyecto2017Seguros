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
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.pacinetes.dom.provincia.Provincia;
import com.pacinetes.dom.provincia.ProvinciaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Localidad.class)
@DomainServiceLayout(named = "Clientes", menuOrder = "10.4")
public class LocalidadMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las Localidades")
	@MemberOrder(sequence = "2")
	public List<Localidad> listar() {
		return localidadesRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Localidad Por Nombre")
	@MemberOrder(sequence = "5")
	public List<Localidad> buscarPorNombre(@ParameterLayout(named = "Nombre") final String localidadNombre) {
		return localidadesRepository.buscarPorNombre(localidadNombre);

	}

	public List<Provincia> choices1Crear() {
		return provinciasRepository.listarActivos();
	}

	@MemberOrder(sequence = "1.2")
	@ActionLayout(named = "Crear Localidad")
	public Localidad crear(@ParameterLayout(named = "Nombre") final String localidadNombre,
			@ParameterLayout(named = "Provincia") final Provincia localidadProvincia) {
		return localidadesRepository.crear(localidadNombre, localidadProvincia);
	}

	@javax.inject.Inject
	LocalidadRepository localidadesRepository;

	@javax.inject.Inject
	ProvinciaRepository provinciasRepository;

}
