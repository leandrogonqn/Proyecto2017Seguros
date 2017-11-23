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

import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY)
@DomainServiceLayout(named = "Polizas Listar", menuOrder = "28.50")
public class VencimientoPolizasGeneral {

	public VencimientoPolizasGeneral() {
	}

	@Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
	@ActionLayout(cssClassFa = "fa-calendar", named = "Vencimiento de polizas")
	public List<VencimientosPolizaCompaniaViewModel> vencimiento() {

		final List<Poliza> polizas = polizaRepository.listarPolizasPorVencimiento();

		List<VencimientosPolizaCompaniaViewModel> listaPolizas = Lists
				.newArrayList(Iterables.transform(polizas, ordenar()));

		Collections.sort(listaPolizas);

		return listaPolizas;
	}

	private Function<Poliza, VencimientosPolizaCompaniaViewModel> ordenar() {

		return new Function<Poliza, VencimientosPolizaCompaniaViewModel>() {

			@Override
			public VencimientosPolizaCompaniaViewModel apply(final Poliza p) {

				return new VencimientosPolizaCompaniaViewModel(p);
			}
		};
	}

	@Inject
	PolizaRepository polizaRepository;

}
