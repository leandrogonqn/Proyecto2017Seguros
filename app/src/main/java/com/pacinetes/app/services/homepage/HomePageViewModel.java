package com.pacinetes.app.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.ViewModel;

import com.pacinetes.app.viewmodel.ClientesCumpleañosViewModel;
import com.pacinetes.app.viewmodel.ClientesPorCumpleaños;
import com.pacinetes.app.viewmodel.FacturacionAnualPorCompania;
import com.pacinetes.app.viewmodel.FacturacionCompaniasViewModel;
import com.pacinetes.app.viewmodel.VencimientoPolizasGeneral;
import com.pacinetes.app.viewmodel.VencimientosPolizaCompaniaViewModel;

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