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
package com.pacinetes.dom.vehiculo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import com.pacinetes.dom.modelo.Modelo;
import com.pacinetes.dom.modelo.ModeloRepository;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = Vehiculo.class)
@DomainServiceLayout(named = "Vehiculos", menuOrder = "50.1")
public class VehiculoMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Vehiculos")
	@MemberOrder(sequence = "2")
	public List<Vehiculo> listar() {
		return vehiculosRepository.listar();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Vehiculo por Dominio")
	@MemberOrder(sequence = "5")
	public List<Vehiculo> buscarPorDominio(@ParameterLayout(named = "Dominio") final String vehiculoDominio) {
		return vehiculosRepository.buscarPorDominio(vehiculoDominio);
	}

	public List<Modelo> choices4Crear() {
		return modelosRepository.listarActivos();
	}

	@Action(semantics = SemanticsOf.IDEMPOTENT)
	@MemberOrder(sequence = "1.2")
	@ActionLayout(named = "Crear Vehiculo")
	public Vehiculo crear(@ParameterLayout(named = "Dominio") final String vehiculoDominio,
			@ParameterLayout(named = "Año") final int vehiculoAnio,
			@ParameterLayout(named = "Numero de Motor") final String vehiculoNumeroMotor,
			@ParameterLayout(named = "Numero de Chasis") final String vehiculoNumeroChasis,
			@ParameterLayout(named = "Modelo") final Modelo vehiculoModelo) {
		return vehiculosRepository.crear(vehiculoDominio, vehiculoAnio, vehiculoNumeroMotor, vehiculoNumeroChasis,
				vehiculoModelo);
	}

	public Collection<Integer> choices1Crear() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Calendar hoy = Calendar.getInstance();
		int año = hoy.get(Calendar.YEAR);
		for (int i = 1980; i <= año; i++) {
			numbers.add(i);
		}
		return numbers;
	}

	@javax.inject.Inject
	VehiculoRepository vehiculosRepository;

	@javax.inject.Inject
	ModeloRepository modelosRepository;

}
