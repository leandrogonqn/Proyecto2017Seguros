package domainapp.dom.polizaIncendio;

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
        repositoryFor = PolizaIncendio.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaIncendioListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Incendio")
	  @MemberOrder(sequence = "80")
	  public List<PolizaIncendio> listar() {
			  List<PolizaIncendio> listaPolizaRiesgoIncendio = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoIncendio.size(); i++) {
				  listaPolizaRiesgoIncendio.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoIncendio;
	    }

	    @javax.inject.Inject
	    PolizaIncendioRepository polizasRepository;
	    
}