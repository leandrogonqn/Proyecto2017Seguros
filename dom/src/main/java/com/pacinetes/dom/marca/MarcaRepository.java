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
package com.pacinetes.dom.marca;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Marca.class
)
public class MarcaRepository {

    public List<Marca> listar() {
        return repositoryService.allInstances(Marca.class);
    }


    public List<Marca> buscarPorNombre(final String marcaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Marca.class,
                        "buscarPorNombre",
                        "marcasNombre", marcaNombre.toLowerCase()));

    }
    
    public List<Marca> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Marca.class,
                        "listarActivos"));
   }
    
    public List<Marca> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Marca.class,
                           "listarInactivos"));
      }
    
  

    public Marca crear(final String marcaNombre) {
        final Marca object = new Marca(marcaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
