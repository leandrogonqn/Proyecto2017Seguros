package com.pacinetes.app.viewmodel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.pacinetes.dom.compania.Compania;
import com.pacinetes.dom.compania.CompaniaRepository;
import com.pacinetes.dom.poliza.PolizaRepository;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "40.11"
)
public class FacturacionAnualPorCompania {

	public FacturacionAnualPorCompania() {
	}

	@Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
	@ActionLayout(
            cssClassFa = "fa-usd",
            named = "Facturacion por compania"            
    )
	public List<FacturacionCompaniasViewModel> facturacion(){
		
		final List<Compania> companias= companiaRepository.listarActivos();
		
		List<FacturacionCompaniasViewModel> listaCompanias = Lists.newArrayList(Iterables.transform(companias, byPosiciones()));
		
		Collections.sort(listaCompanias);
		
		return listaCompanias;
	}
	
	private Function<Compania, FacturacionCompaniasViewModel> byPosiciones() {
		
		return new Function<Compania, FacturacionCompaniasViewModel>(){
			
			@Override
	        public FacturacionCompaniasViewModel apply(final Compania c) {
				
				return new FacturacionCompaniasViewModel(c);
	        }
	     };
	 }	
	
    @javax.inject.Inject
    PolizaRepository polizaRepository;
    @Inject
    CompaniaRepository companiaRepository;
    
}