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
package domainapp.dom.modelo;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteMenu;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.cliente.Sexo;
import domainapp.dom.tipoVehiculo.TipoVehiculo;
import domainapp.dom.tipoVehiculo.TipoVehiculoRepository;
import domainapp.dom.cliente.ClienteMenu.CreateDomainEvent;
import domainapp.dom.marca.Marca;
import domainapp.dom.marca.MarcaRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Modelo.class
)
@DomainServiceLayout(
        named = "Vehiculos",
        menuOrder = "50.3"
)
public class ModeloMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todos los Modelos")
	    @MemberOrder(sequence = "2")
	    public List<Modelo> listar() {
	        return modelosRepository.listar();
	    }

	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Modelos")
	    @MemberOrder(sequence = "5")
	    public List<Modelo> buscarPorNombre(
	            @ParameterLayout(named="Nombre")
	            final String modeloNombre
	    ) {
	        return modelosRepository.buscarPorNombre(modeloNombre);
	    }
	    
	    public List<TipoVehiculo> choices1Crear(){
	    	return tipoVehiculoRepository.listarActivos();
	    }
	    
	     public List<Marca> choices2Crear(){
	    	return  marcaRepository.listarActivos();
	    }

	    @MemberOrder(sequence = "1.2")
	    @ActionLayout(named="Crear Modelo")
	    public Modelo crear(
	            @ParameterLayout(named="Nombre") final String modeloNombre,
	            @ParameterLayout(named="Tipo Vehiculo")final TipoVehiculo modeloTipoVehiculo,
	            @ParameterLayout(named="Marca") final Marca modeloMarca){
	        return modelosRepository.crear(modeloNombre, modeloTipoVehiculo, modeloMarca);
	    }


	    @javax.inject.Inject
	    ModeloRepository modelosRepository;
	    

	    @javax.inject.Inject
	    MarcaRepository marcaRepository;
	    
	    @javax.inject.Inject
	    TipoVehiculoRepository tipoVehiculoRepository;

	    
	    @javax.inject.Inject
	    TipoVehiculoRepository vehiculoRepository;
}
