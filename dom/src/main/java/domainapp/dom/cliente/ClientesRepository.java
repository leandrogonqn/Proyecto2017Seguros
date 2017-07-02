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
package domainapp.dom.cliente;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Clientes.class
)
public class ClientesRepository {

    public List<Clientes> listar() {
        return repositoryService.allInstances(Clientes.class);
    }

    public List<Clientes> buscarPorNombre(final String nombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Clientes.class,
                        "buscarPorNombre",
                        "nombre", nombre));
    }
    
    public List<Clientes> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Clientes.class,
                        "listarActivos"));
   }
    
    public List<Clientes> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Clientes.class,
                           "listarInactivos"));
      }
    
    public List<Clientes> buscarPorDNI(final int dni) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Clientes.class,
                        "buscarPorDNI",
                        "dni", dni));
    }

    public Clientes crear(final String nombre, final String apellido, Sexo sexo, final int dni, final String direccion, final String telefono, final String mail, final Date fechaNacimiento, final boolean notificacionCumpleanios) {
        final Clientes object = new Clientes(nombre, apellido, sexo, dni, direccion, telefono, mail, fechaNacimiento, notificacionCumpleanios);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
