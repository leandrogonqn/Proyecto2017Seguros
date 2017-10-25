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
package com.pacinetes.dom.empresa;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import com.pacinetes.dom.banco.Banco;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.TipoPago;
import com.pacinetes.dom.localidad.Localidad;
import com.pacinetes.dom.localidad.LocalidadRepository;
import com.pacinetes.dom.tipodecobertura.TipoDeCobertura;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Empresa.class
)
@DomainServiceLayout(
        named = "Clientes",
        menuOrder = "10.3"
)
public class EmpresaMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
    		named="Listar Todas las Empresas")
    @MemberOrder(sequence = "2")
    public List<Empresa> listar() {
        return empresasRepository.listar();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, 
    	cssClassFa="fa-search", 
    	named="Buscar Por RazonSocial")
    @MemberOrder(sequence = "5")
    public List<Empresa> buscarPorRazonSocial(
            @ParameterLayout(named="RazonSocial")
            final String empresaRazonSocial
    ) {
        return empresasRepository.buscarPorRazonSocial(empresaRazonSocial);
    }
    
    public List<Localidad> choices2Crear(){
    	return localidadesRepository.listarActivos();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<EmpresaMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named="Crear Empresa")
    @MemberOrder(sequence = "1")
    public Empresa crear(
            @ParameterLayout(named="RazonSocial") final String empresaRazonSocial, 
            @ParameterLayout(named="Cuit") final String personaCuitCuil, 
    		@ParameterLayout(named="Localidad") final Localidad personaLocalidad,
            @ParameterLayout(named="Dirección") final String personaDireccion,
            @Nullable @ParameterLayout(named="Teléfono") @Parameter(optionality =Optionality.OPTIONAL)  final String personaTelefono,
            @Nullable @ParameterLayout(named="E-Mail") @Parameter(optionality =Optionality.OPTIONAL)  final String personaMail){
        return empresasRepository.crear(empresaRazonSocial, personaCuitCuil, personaLocalidad, personaDireccion, personaTelefono, personaMail);
    }
    
    @javax.inject.Inject
    EmpresaRepository empresasRepository;
    
    @javax.inject.Inject
    LocalidadRepository localidadesRepository;

}
