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
package domainapp.dom.polizaCombinadoFamiliar;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaCombinadoFamiliar.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "29.2"
)
public class PolizaCombinadoFamiliarListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Combinados Familiares")
	  @MemberOrder(sequence = "20")
	  public List<PolizaCombinadoFamiliar> listar() {
			  List<PolizaCombinadoFamiliar> listaPolizaRiesgoCombinadosFamiliares = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoCombinadosFamiliares.size(); i++) {
				  listaPolizaRiesgoCombinadosFamiliares.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoCombinadosFamiliares;
	    }
	  
	    @javax.inject.Inject
	    PolizaCombinadoFamiliarRepository polizasRepository;
}
