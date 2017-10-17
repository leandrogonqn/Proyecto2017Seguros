package domainapp.dom.polizaART;

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
        repositoryFor = PolizaART.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaARTListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas ART")
	  @MemberOrder(sequence = "50")
	  public List<PolizaART> listar() {
			  List<PolizaART> listaPolizaRiesgoART = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoART.size(); i++) {
				  listaPolizaRiesgoART.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoART;
	    }
	  
	    @javax.inject.Inject
	    PolizaARTRepository polizasRepository;

}