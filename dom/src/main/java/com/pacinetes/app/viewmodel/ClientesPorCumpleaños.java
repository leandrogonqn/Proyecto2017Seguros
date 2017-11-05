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
import org.apache.isis.applib.annotation.Where;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.cliente.ClienteRepository;
import com.pacinetes.dom.mail.Mail;


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
	
	@ActionLayout(hidden=Where.EVERYWHERE)
	public void enviarMailCumpleaños(){
		
		//obtengo la lista que se muestra en  el viewmodel 
		//(la cual ya viene filtrada con gente que no tiene fecha de Nacimiento cargada)
		List<ClientesCumpleañosViewModel> listaCliente = cumpleaños();
		
		//Con esto saco de la lista los que no quieren que le avisen para el cumpleaños (NotificacionCumpleanios==false)
    	Iterator<ClientesCumpleañosViewModel> it = listaCliente.iterator();
    	while (it.hasNext()) {
    		ClientesCumpleañosViewModel item = it.next();
    		if (item.getCliente().getClienteNotificacionCumpleanios()==false)
    			it.remove();
    	}
    	
    	//con esto recorro la lista obtenida
		for(ClientesCumpleañosViewModel c:listaCliente){
			//si al cliente aun no se les mando salutacion (Aviso==false) y los días restantes son menor o = a 15 
	    	//se les mande la salutacion y cambia el aviso a true
			if ((c.getCliente().getClienteAviso() == false) && (c.getDiasRestantes() <= 15)) {
				Mail.enviarMailCumpleaños(c.getCliente());
				c.getCliente().setClienteAviso(true);
			}
			//si al cliente ya se le mando salutacion y los dias restantes son mayor o = a 16,
			//se le cambia el aviso a false, para cuadno llegue el momento se le pueda mandar el mensaje
			if ((c.getCliente().getClienteAviso() == true) && (c.getDiasRestantes() >= 16)) {
				c.getCliente().setClienteAviso(false);
			}
		}
	}
	
	@Inject
	ClienteRepository clienteRepository;
}
