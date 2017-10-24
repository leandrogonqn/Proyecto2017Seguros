package com.pacinetes.app.services.homepage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;

import com.pacinetes.dom.cliente.Cliente;
import com.pacinetes.dom.cliente.ClienteRepository;
import com.pacinetes.dom.empresas.Empresa;
import com.pacinetes.dom.empresas.EmpresaRepository;
import com.pacinetes.dom.persona.Persona;
import com.pacinetes.dom.persona.PersonaRepository;
import com.pacinetes.dom.poliza.Poliza;
import com.pacinetes.dom.poliza.PolizaRepository;

@ViewModel
public class HomePageViewModel {

    //region > title
    public String title() {
        return "SiGeSe -- Sistema de Gestion de Seguros";
    }
    //endregion

    //region > object (collection)
    @HomePage
    @CollectionLayout(named="Clientes")
    public List<Cliente> getClientes() {
        return clienteRepository.listar();
    }
    
    @HomePage
    @CollectionLayout(named="Empresas")
    public List<Empresa> getEmpresas(){
    	return empresaRepository.listar();
    }
    
    @HomePage
    @CollectionLayout(named="Polizas")
    public List<Poliza> getPolizas(){
    	return polizaRepository.listarPolizasPorVencimiento();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    ClienteRepository clienteRepository;
    
    @Inject
    EmpresaRepository empresaRepository;
    
    @Inject
    PolizaRepository polizaRepository;

    //endregion
}