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
package domainapp.dom.tipoTarjeta;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.modelo.Modelos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TiposTarjetas.class
)
public class TiposTarjetasRepository {

    public List<TiposTarjetas> listar() {
        return repositoryService.allInstances(TiposTarjetas.class);
    }

    public List<TiposTarjetas> buscarPorNombre(final String tipoTarjetaNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        TiposTarjetas.class,
                        "findByName",
                        "tipoTarjetaNombre", tipoTarjetaNombre.toLowerCase()));
    }

    public List<TiposTarjetas> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   TiposTarjetas.class,
                           "listarActivos"));
      }
       
       public List<TiposTarjetas> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  TiposTarjetas.class,
                              "listarInactivos"));
         }
    
    public TiposTarjetas crear(final String tipoTarjetaNombre) {
        final TiposTarjetas object = new TiposTarjetas(tipoTarjetaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
