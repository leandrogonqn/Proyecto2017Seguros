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
package domainapp.dom.adjunto;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;

import domainapp.dom.poliza.Poliza;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Adjunto.class
)
public class AdjuntoRepository {

    public List<Adjunto> listar() {
        return repositoryService.allInstances(Adjunto.class);
    }


    public List<Adjunto> buscarPorDescripcion(final String adjuntoDescripcion) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                		Adjunto.class,
                        "buscarPorDescripcion",
                        "adjuntoDescripcion", adjuntoDescripcion));
    }
    
    public List<Adjunto> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                		Adjunto.class,
                        "listarActivos"));
   }
    
    public List<Adjunto> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Adjunto.class,
                           "listarInactivos"));
      }
    
    public Adjunto crear(final String adjuntoDescripcion, final Blob imagen) {
        final Adjunto object = new Adjunto(adjuntoDescripcion, imagen);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
