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
package domainapp.dom.ocupacion;

import java.util.List;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Ocupacion.class
)
public class OcupacionRepository {

    public List<Ocupacion> listar() {
        return repositoryService.allInstances(Ocupacion.class);
    }

    public List<Ocupacion> buscarPorNombre(final String ocupacionNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Ocupacion.class,
                        "findByName",
                        "ocupacionNombre", ocupacionNombre.toLowerCase()));
    }

    public List<Ocupacion> listarActivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                		   Ocupacion.class,
                           "listarActivos"));
      }
       
       public List<Ocupacion> listarInactivos(){
         	 return repositoryService.allMatches(
                      new QueryDefault<>(
                    		  Ocupacion.class,
                              "listarInactivos"));
         }
    
    public Ocupacion crear(final String ocupacionNombre) {
        final Ocupacion object = new Ocupacion(ocupacionNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
