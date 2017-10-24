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
package com.pacinetes.dom.persona;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Persona.class
)
public class PersonaRepository {

    public List<Persona> listar() {
        return repositoryService.allInstances(Persona.class);
    }
	
	public List<Persona> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  Persona.class,
	                  "listarActivos"));
	  }
	
	public List<Persona> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  Persona.class,
	                  "listarInactivos"));
	  }
	
	@Inject
	RepositoryService repositoryService;
	
}
