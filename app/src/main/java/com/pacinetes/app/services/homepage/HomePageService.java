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
package com.pacinetes.app.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.registry.ServiceRegistry;

import com.pacinetes.dom.mail.Mail;
import com.pacinetes.dom.mail.MailRepository;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY // trick to suppress the actions from the top-level menu
)
public class HomePageService {

    //region > homePage (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @HomePage
    public HomePageViewModel homePage() {
		List<Poliza> listPolizas = polizaRepository.listar();
		for (int i = 0; i < listPolizas.size(); i++) {
			listPolizas.get(i).actualizarPoliza();
		}
		List<Mail> mail = mailRepository.listar();
        return serviceRegistry.injectServicesInto(new HomePageViewModel());
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    ServiceRegistry serviceRegistry;
    
    @Inject
    PolizaRepository polizaRepository;
    
    @Inject
    MailRepository mailRepository;

    //endregion
}