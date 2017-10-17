/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.cliente;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;

import domainapp.dom.banco.Banco;
import domainapp.dom.compania.Compania;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.TipoPago;
import domainapp.dom.localidad.Localidad;
import domainapp.dom.localidad.LocalidadRepository;
import domainapp.dom.tiposDeCoberturas.TipoDeCobertura;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Cliente.class
)
@DomainServiceLayout(
        named = "Clientes",
        menuOrder = "10"
)
public class ClienteMenu {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
    		named="Listar Todos los Clientes")
    @MemberOrder(sequence = "2")
    public List<Cliente> listar() {
        return clientesRepository.listar();
    }
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, 
    	cssClassFa="fa-search", 
    	named="Buscar Por Nombre")
    @MemberOrder(sequence = "5")
    public List<Cliente> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String clienteNombre
    ) {
        return clientesRepository.buscarPorNombre(clienteNombre);
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, 
    	cssClassFa="fa-search",
    	named="Buscar Por DNI")
    @MemberOrder(sequence = "6")
    public List<Cliente> buscarPorDNI(
            @ParameterLayout(named="DNI")
            final int clienteDni
    ) {
        return clientesRepository.buscarPorDNI(clienteDni);
    }
    
    public List<Localidad> choices5Crear(){
    	return localidadesRepository.listarActivos();
    }


    public static class CreateDomainEvent extends ActionDomainEvent<ClienteMenu> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named="Crear Cliente")
    @MemberOrder(sequence = "1")
    public Cliente crear(
            @ParameterLayout(named="Nombre") final String clienteNombre, 
            @ParameterLayout(named="Apellido") final String clienteApellido,
            @ParameterLayout(named="Tipo de Documento") final TipoDocumento clienteTipoDocumento,
    		@ParameterLayout(named="Numero de Documento") final int clienteDni,
    		@ParameterLayout(named="Sexo") final Sexo clienteSexo,
    		@ParameterLayout(named="Localidad") final Localidad personaLocalidad,
            @ParameterLayout(named="Dirección") final String personaDireccion, 
            @Nullable @ParameterLayout(named="Teléfono") @Parameter(optionality =Optionality.OPTIONAL)  final String personaTelefono,
            @Nullable @ParameterLayout(named="E-Mail") @Parameter(optionality =Optionality.OPTIONAL)  final String personaMail,
            @Nullable @ParameterLayout(named="Fecha de Nacimiento") @Parameter(optionality =Optionality.OPTIONAL)  final Date clienteFechaNacimiento, 
            @ParameterLayout(named="Notif. Cumpleaños") final boolean clienteNotificacionCumpleanios) {
        return clientesRepository.crear(clienteNombre, clienteApellido, clienteSexo,clienteTipoDocumento, personaLocalidad, clienteDni, personaDireccion, personaTelefono, personaMail, clienteFechaNacimiento, clienteNotificacionCumpleanios);
    }
    
	public String validateCrear(
            final String clienteNombre, 
            final String clienteApellido,
            final TipoDocumento clienteTipoDocumento,
    		final int clienteDni,
    		final Sexo clienteSexo,
    		final Localidad personaLocalidad,
            final String personaDireccion, 
            final String personaTelefono,
            final String personaMail,
            final Date clienteFechaNacimiento, 
            final boolean clienteNotificacionCumpleanios){
		if ((clienteFechaNacimiento == null)&(clienteNotificacionCumpleanios == true)){
			return "Se necesita cargar fecha de nacimiento para poder cargar el cumpleaños";
		}
		return "";
	}

    @javax.inject.Inject
    ClienteRepository clientesRepository;
    
    @javax.inject.Inject
    LocalidadRepository localidadesRepository;

}
