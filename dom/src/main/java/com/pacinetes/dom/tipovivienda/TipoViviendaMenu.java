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
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = TipoVivienda.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "40.5"
)
public class TipoViviendaMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tipos de Viviendas")
    @MemberOrder(sequence = "2")
    public List<TipoVivienda> listar() {
        return tipoViviendasRepository.listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", named="Buscar Tipos de Viviendas")
    @MemberOrder(sequence = "5")
    public List<TipoVivienda> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String tipoViviendaNombre
    ) {
        return tipoViviendasRepository.buscarPorNombre(tipoViviendaNombre);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<TipoVivienda> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named="Crear Tipo de Vivienda")
    @MemberOrder(sequence = "1.2")
    public TipoVivienda crear(@ParameterLayout(named="Nombre",labelPosition=LabelPosition.TOP)final String tipoViviendaNombre) {
        return tipoViviendasRepository.crear(tipoViviendaNombre);
    }


    @javax.inject.Inject
    TipoViviendaRepository tipoViviendasRepository;
}
