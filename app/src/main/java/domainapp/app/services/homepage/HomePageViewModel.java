package domainapp.app.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.app.viewmodel.ClientesCumpleañosViewModel;
import domainapp.app.viewmodel.ClientesPorCumpleaños;
import domainapp.app.viewmodel.FacturacionAnualPorCompania;
import domainapp.app.viewmodel.FacturacionCompaniasViewModel;
import domainapp.app.viewmodel.VencimientoPolizasGeneral;
import domainapp.app.viewmodel.VencimientosPolizaCompaniaViewModel;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.empresa.Empresa;
import domainapp.dom.empresa.EmpresaRepository;
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
    @CollectionLayout(named="Lista de Clientes por cumpleaños")
    public List<ClientesCumpleañosViewModel> getClientes() {
        return clientesPorCumpleaños.cumpleaños();
    }
    
    @HomePage
    @CollectionLayout(named="Facturacion Anual Por Compania")
    public List<FacturacionCompaniasViewModel> getFacturacion(){
    	return facturacionAnualPorComania.facturacion();
    }
    
    @HomePage
    @CollectionLayout(named="Polizas que se estan por vencer y aun no tienen renovacion")
    public List<VencimientosPolizaCompaniaViewModel> getPolizas(){
    	return vencimientoPolizasGeneral.vencimiento();
    }
    //endregion

    //region > injected services
    @Inject
    ClientesPorCumpleaños clientesPorCumpleaños;
    
    @Inject
    FacturacionAnualPorCompania facturacionAnualPorComania;
    
    @Inject
    VencimientoPolizasGeneral vencimientoPolizasGeneral;

    //endregion
}