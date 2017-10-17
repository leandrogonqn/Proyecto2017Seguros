package domainapp.dom.polizaIntegralComercio;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = PolizaIntegralComercio.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "29.3"
)
public class PolizaIntegralComercioListarMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Poliza Integral Comercio")
	  @MemberOrder(sequence = "30")
	  public List<PolizaIntegralComercio> listar() {
			  List<PolizaIntegralComercio> listaPolizasRiesgoIntegralComercio = polizasRepository.listar();
			  for(int i=0; i< listaPolizasRiesgoIntegralComercio.size(); i++) {
				  listaPolizasRiesgoIntegralComercio.get(i).actualizarPoliza();
		        }
		      return listaPolizasRiesgoIntegralComercio;
	    }

	    @javax.inject.Inject
	    PolizaIntegralComercioRepository polizasRepository;
	    
}