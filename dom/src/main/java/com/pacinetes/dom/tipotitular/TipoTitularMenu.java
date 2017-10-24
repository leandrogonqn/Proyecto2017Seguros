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
package com.pacinetes.dom.tipotitular;

import java.util.List;

import javax.jdo.annotations.Column;

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
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import com.pacinetes.dom.ocupacion.Ocupacion;
import com.pacinetes.dom.ocupacion.OcupacionRepository;
import com.pacinetes.dom.ocupacion.OcupacionMenu.CreateDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = TipoTitular.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "40.4"
)
public class TipoTitularMenu {
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Tipos de Titulares")
    @MemberOrder(sequence = "2")
    public List<TipoTitular> listar() {
        return TipoTitularesRepository.listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", named="Buscar Tipos de Titulares")
    @MemberOrder(sequence = "5")
    public List<TipoTitular> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String ocupacionNombre
    ) {
        return TipoTitularesRepository.buscarPorNombre(ocupacionNombre);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<TipoTitular> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1.2")
    @ActionLayout(named="Crear Tipo de Titular")
    public TipoTitular crear(@ParameterLayout(named="Nombre",labelPosition=LabelPosition.TOP)@Parameter(optionality=Optionality.OPTIONAL) final String tipoTitularNombre){
        return TipoTitularesRepository.crear(tipoTitularNombre);
    }

    @javax.inject.Inject
    TipoTitularRepository TipoTitularesRepository;
}
