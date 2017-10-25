package domainapp.app.viewmodel;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import domainapp.dom.compania.Compania;
import domainapp.dom.estado.Estado;
import domainapp.dom.poliza.Poliza;
import domainapp.dom.poliza.PolizaRepository;

@Mixin
public class VencimientoPolizasPorCompania {
	
	private Compania compania;

	public VencimientoPolizasPorCompania(Compania compania) {
		this.compania = compania;
	}
	
	@Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
	@ActionLayout(
            cssClassFa = "fa-calendar",
            named = "Lista de Polizas de la compañia que se estan por vencer"            
    )
	public List<VencimientosPolizaCompaniaViewModel> vencimiento(){
		
		final List<Poliza> polizas=polizaRepository.listarPorEstado(Estado.vigente);
		
		List<VencimientosPolizaCompaniaViewModel>listaPolizas=Lists.newArrayList(Iterables.transform(polizas, byPosiciones()));
		
		Collections.sort(listaPolizas);
		
		return listaPolizas;
	}
	
	private Function<Poliza, VencimientosPolizaCompaniaViewModel> byPosiciones() {
		
		return new Function<Poliza, VencimientosPolizaCompaniaViewModel>(){
			
			@Override
	        public VencimientosPolizaCompaniaViewModel apply(final Poliza poliza) {
				
				return new VencimientosPolizaCompaniaViewModel(poliza, compania);
	        }
	     };
	 }	
	
    @javax.inject.Inject
    PolizaRepository polizaRepository;
}