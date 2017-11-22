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
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;

@Mixin
public class SiniestrosPorCliente {

	private Persona persona;

	public SiniestrosPorCliente(Persona persona) {
		this.persona = persona;
	}

	@Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
	@ActionLayout(cssClassFa = "fa-bomb", named = "Cantidad de Siniestros del cliente por Polizas")
	public List<SiniestroClienteViewModel> cantidad() {

		final List<Poliza> polizas = polizaRepository.listar();

		List<SiniestroClienteViewModel> listaSiniestros = Lists.newArrayList(Iterables.transform(polizas, ordenar()));

		Collections.sort(listaSiniestros);

		return listaSiniestros;
	}

	private Function<Poliza, SiniestroClienteViewModel> ordenar() {

		return new Function<Poliza, SiniestroClienteViewModel>() {

			@Override
			public SiniestroClienteViewModel apply(final Poliza poliza) {

				return new SiniestroClienteViewModel(persona, poliza);
			}
		};
	}

	@javax.inject.Inject
	PolizaRepository polizaRepository;
}