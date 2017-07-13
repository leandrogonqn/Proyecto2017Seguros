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
package domainapp.dom.banco;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.modelo.Modelos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Bancos.class
)
public class BancosRepository {

    public List<Bancos> listar() {
        return repositoryService.allInstances(Bancos.class);
    }

    public List<Bancos> buscarPorNombre(final String bancoNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Bancos.class,
                        "findByName",
                        "bancoNombre", bancoNombre.toLowerCase()));
    }

    public List<Bancos> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Bancos.class,
                           "listarActivos"));
      }
       
       public List<Bancos> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  Bancos.class,
                              "listarInactivos"));
         }
    
    public Bancos crear(final String bancoNombre) {
        final Bancos object = new Bancos(bancoNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
