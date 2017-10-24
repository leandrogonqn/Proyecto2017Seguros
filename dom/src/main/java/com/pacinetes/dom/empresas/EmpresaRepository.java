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
package com.pacinetes.dom.empresas;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.localidad.Localidad;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Empresa.class
)
public class EmpresaRepository {

    public List<Empresa> listar() {
        return repositoryService.allInstances(Empresa.class);
    }

    public List<Empresa> buscarPorRazonSocial(final String empresaRazonSocial) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Empresa.class,
                        "buscarPorRazonSocial",
                        "empresaRazonSocial", empresaRazonSocial.toLowerCase()));
    }
    

    public Empresa crear(final String empresaRazonSocial, String personaCuitCuil, Localidad personaLocalidad, final String personaDireccion, final String personaTelefono, final String personaMail) {
        final Empresa object = new Empresa(empresaRazonSocial, personaCuitCuil, personaLocalidad, personaDireccion, personaTelefono, personaMail);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
