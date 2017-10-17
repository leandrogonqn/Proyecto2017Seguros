package domainapp.app.services.homepage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.empresa.Empresa;
import domainapp.dom.empresa.EmpresaRepository;
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;

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