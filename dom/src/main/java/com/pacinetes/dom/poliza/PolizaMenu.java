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
package com.pacinetes.dom.poliza;

import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.estado.Estado;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Poliza.class)
@DomainServiceLayout(named = "Polizas Listar", menuOrder = "28.10")
public class PolizaMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar todas las polizas")
	@MemberOrder(sequence = "4")
	public List<Poliza> listar() {
		List<Poliza> listaPolizas = polizasRepository.listar();
		return listaPolizas;
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar polizas por Numero")
	@MemberOrder(sequence = "1")
	public List<Poliza> buscarpolizaNumero(@ParameterLayout(named = "Numero") final String polizaNumero) {
		return polizasRepository.buscarpolizaNumero(polizaNumero);
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar polizas por Clientes")
	@MemberOrder(sequence = "2")
	public List<Poliza> buscarPorCliente(@ParameterLayout(named = "Cliente") final Persona polizaCliente) {
		return polizasRepository.buscarPorCliente(polizaCliente);
	}

	public List<Persona> choices0BuscarPorCliente() {
		return personaRepository.listarActivos();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar polizas por Companias")
	@MemberOrder(sequence = "3")
	public List<Poliza> buscarPorCompania(@ParameterLayout(named = "Compania") final Compania polizaCompania) {
		return polizasRepository.buscarPorCompania(polizaCompania);
	}

	public List<Compania> choices0BuscarPorCompania() {
		return companiaRepository.listarActivos();
	}

	@MemberOrder(sequence = "5")
	@ActionLayout(named = "Listar por Estados")
	public List<Poliza> listarPorEstado(final Estado estado) {
		return polizasRepository.listarPorEstado(estado);
	}

	@javax.inject.Inject
	PolizaRepository polizasRepository;
	@javax.inject.Inject
	PersonaRepository personaRepository;
	@Inject
	CompaniaRepository companiaRepository;
}
