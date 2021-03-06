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
package com.pacinetes.dom.polizaap;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaAP.class)
@DomainServiceLayout(named = "Polizas Listar", menuOrder = "30")
public class PolizaAPListarMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Polizas AP")
	@MemberOrder(sequence = "40")
	public List<PolizaAP> listar() {
		List<PolizaAP> listaPolizaRiesgoAP = polizasRepository.listar();
		for (int i = 0; i < listaPolizaRiesgoAP.size(); i++) {
			listaPolizaRiesgoAP.get(i).actualizarPoliza();
		}
		return listaPolizaRiesgoAP;
	}

	@javax.inject.Inject
	PolizaAPRepository polizasRepository;

}
