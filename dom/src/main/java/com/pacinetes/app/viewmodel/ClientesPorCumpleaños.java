package com.pacinetes.app.viewmodel;

import java.util.Collections;
import java.util.Iterator;
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
import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.cliente.ClienteRepository;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
		named = "Clientes",
        menuOrder = "10.2"
)
public class ClientesPorCumpleaños {
	
	public ClientesPorCumpleaños() {}

	@Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
	@ActionLayout(
            cssClassFa = "fa-birthday-cake",
            named = "Cumpleaños Clientes"            
    )
	public List<ClientesCumpleañosViewModel> cumpleaños(){
		
		final List<Cliente> clientes= clienteRepository.listarActivos();
    	Iterator<Cliente> it = clientes.iterator();
    	while (it.hasNext()) {
    		Cliente item = it.next();
    		if (item.getClienteFechaNacimiento()==null)
    			it.remove();
    	}
		
		List<ClientesCumpleañosViewModel> listaClientes = Lists.newArrayList(Iterables.transform(clientes, byPosiciones()));
		
		Collections.sort(listaClientes);
		
		return listaClientes;
	}
	
	private Function<Cliente, ClientesCumpleañosViewModel> byPosiciones() {
		
		return new Function<Cliente, ClientesCumpleañosViewModel>(){
			
			@Override
	        public ClientesCumpleañosViewModel apply(final Cliente client) {
				
				return new ClientesCumpleañosViewModel(client);
	        }
	     };
	 }	
	
	@Inject
	ClienteRepository clienteRepository;
	
}
