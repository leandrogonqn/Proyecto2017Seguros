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
package com.pacinetes.dom.modelo;

import java.security.Timestamp;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.pacinetes.dom.marca.Marca;
import com.pacinetes.dom.marca.MarcaRepository;
import com.pacinetes.dom.tipovehiculo.TipoVehiculo;
import com.pacinetes.dom.tipovehiculo.TipoVehiculoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Modelo.class
)
public class ModeloRepository {

    public List<Modelo> listar() {
        return repositoryService.allInstances(Modelo.class);
    }

    public List<Modelo> buscarPorNombre(final String modeloNombre) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelo.class,
                        "buscarPorNombre",
                        "modeloNombre", modeloNombre.toLowerCase()));
    }
    
    public List<Modelo> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        Modelo.class,
                        "listarActivos"));
   }
    
    public List<Modelo> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           Modelo.class,
                           "listarInactivos"));
      }
    
    public Modelo crear(final String modeloNombre, TipoVehiculo modeloTipoVehiculo, Marca modeloMarcas) {
        final Modelo object = new Modelo(modeloNombre, modeloTipoVehiculo, modeloMarcas);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;

    @javax.inject.Inject
    MarcaRepository marcaRepository;

}
