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
package com.pacinetes.dom.debitoautomatico;

import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.pacinetes.dom.banco.Banco;
import com.pacinetes.dom.banco.BancoRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = DebitoAutomatico.class)
@DomainServiceLayout(named = "Tipo de pago", menuOrder = "60.2")
public class DebitoAutomaticoMenu {

	@ActionLayout(named = "Crear Debito Automatico")
	@MemberOrder(sequence = "1.2")
	public DebitoAutomatico crear(@ParameterLayout(named = "Titular") final String tipoPagoTitular,
			@ParameterLayout(named = "Banco") final Banco debitoAutomaticoBanco,
			@ParameterLayout(named = "CBU") final BigInteger debitoAutomaticoCbu) {
		return debitoAutomaticoRepository.crear(tipoPagoTitular, debitoAutomaticoBanco, debitoAutomaticoCbu);
	}

	public List<Banco> choices1Crear() {
		return debitoAutomaticoBancoRepository.listarActivos();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Debitos Automaticos")
	@MemberOrder(sequence = "2")
	public List<DebitoAutomatico> listarPagos() {
		return debitoAutomaticoRepository.listar();
	}

	@javax.inject.Inject
	DebitoAutomaticoRepository debitoAutomaticoRepository;

	@Inject
	BancoRepository debitoAutomaticoBancoRepository;
}
