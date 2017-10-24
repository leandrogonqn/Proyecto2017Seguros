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
package com.pacinetes.dom.cliente;

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
        repositoryFor = Cliente.class
)
public class ClienteRepository {

    public List<Cliente> listar() {
        return repositoryService.allInstances(Cliente.class);
    }

    public List<Cliente> buscarPorNombre(final String clienteNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Cliente.class,
                        "buscarPorNombre",
                        "clienteNombre", clienteNombre.toLowerCase()));
    }
    
    public List<Cliente> buscarPorDNI(final int clienteDni) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Cliente.class,
                        "buscarPorDNI",
                        "clienteDni", clienteDni));
    }

    public Cliente crear(final String clienteNombre, final String clienteApellido, Sexo clienteSexo, TipoDocumento clienteTipoDocumento, Localidad personaLocalidad, final int clienteDni, final String personaDireccion, final String personaTelefono, final String personaMail, final Date clienteFechaNacimiento, final boolean clienteNotificacionCumpleanios) {
        final Cliente object = new Cliente(clienteNombre, clienteApellido, clienteSexo,clienteTipoDocumento, personaLocalidad, clienteDni, personaDireccion, personaTelefono, personaMail, clienteFechaNacimiento, clienteNotificacionCumpleanios);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
