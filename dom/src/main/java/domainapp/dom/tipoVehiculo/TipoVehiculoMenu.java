/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.tipoVehiculo;

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
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = TipoVehiculo.class
)
@DomainServiceLayout(
        named = "Tipo Vehiculo",
        menuOrder = "4"
)
public class TipoVehiculoMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<TipoVehiculo> listar() {
        return tipoVehiculoRepository.listar();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<TipoVehiculo> listarActivos() {
        return tipoVehiculoRepository.listarActivos();
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<TipoVehiculo> listarInactivos() {
        return tipoVehiculoRepository.listarInactivos();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
    @MemberOrder(sequence = "5")
    public List<TipoVehiculo> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String tipoVehiculoNombre
    ) {
        return tipoVehiculoRepository.buscarPorNombre(tipoVehiculoNombre);
    }


    public static class CreateDomainEvent extends ActionDomainEvent<TipoVehiculoMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    @ActionLayout(cssClassFa="fa-plus")
    public TipoVehiculo crear(
            @ParameterLayout(named="Nombre")
            final String tipoVehiculoNombre) {
        return tipoVehiculoRepository.crear(tipoVehiculoNombre);
    }


    @javax.inject.Inject
    TipoVehiculoRepository tipoVehiculoRepository;

}
