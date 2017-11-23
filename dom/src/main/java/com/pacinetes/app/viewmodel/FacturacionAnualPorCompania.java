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
package com.pacinetes.app.viewmodel;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.poliza.PolizaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY)
@DomainServiceLayout(named = "Polizas Extras", menuOrder = "40.11")
public class FacturacionAnualPorCompania {

	public FacturacionAnualPorCompania() {
	}

	@Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
	@ActionLayout(cssClassFa = "fa-usd", named = "Facturacion por compania")
	public List<FacturacionCompaniasViewModel> facturacion() {

		final List<Compania> companias = companiaRepository.listarActivos();

		List<FacturacionCompaniasViewModel> listaCompanias = Lists
				.newArrayList(Iterables.transform(companias, ordenar()));

		Collections.sort(listaCompanias);

		return listaCompanias;
	}

	private Function<Compania, FacturacionCompaniasViewModel> ordenar() {

		return new Function<Compania, FacturacionCompaniasViewModel>() {

			@Override
			public FacturacionCompaniasViewModel apply(final Compania c) {

				return new FacturacionCompaniasViewModel(c);
			}
		};
	}

	@javax.inject.Inject
	PolizaRepository polizaRepository;
	@Inject
	CompaniaRepository companiaRepository;

}