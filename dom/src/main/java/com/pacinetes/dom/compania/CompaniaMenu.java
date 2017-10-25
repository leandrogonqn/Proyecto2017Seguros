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
package com.pacinetes.dom.compania;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Compania.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "40.10"
)
public class CompaniaMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Todas las Compañias")
	    @MemberOrder(sequence = "2")
	    public List<Compania> listar() {
	        return companiasRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Buscar Compañias por Nombre")
	    @MemberOrder(sequence = "5")
	    public List<Compania> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String companiaNombre){
	        return companiasRepository.buscarPorNombre(companiaNombre);

	    }

	    public static class CreateDomainEvent extends ActionDomainEvent<CompaniaMenu> {}
	    @Action(domainEvent = CreateDomainEvent.class)
	    @ActionLayout(named="Crear Compañias")
	    @MemberOrder(sequence = "1.2")
	    public Compania crear(
	            @ParameterLayout(named="Nombre") final String companiaNombre,
	    		@ParameterLayout(named="Direccion") final String companiaDireccion, 
	    		@ParameterLayout(named="Telefono") final String companiaTelefono){
	        return companiasRepository.crear(
	        		companiaNombre,
	        		companiaDireccion,
	        		companiaTelefono);
	    }


	    @javax.inject.Inject
	    CompaniaRepository companiasRepository;
}
