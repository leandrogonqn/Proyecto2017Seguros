package domainapp.dom.polizaTRO;

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
        repositoryFor = PolizaTRO.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaTROListarMenu {
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas TRO")
	  @MemberOrder(sequence = "120")
	  public List<PolizaTRO> listar() {
			  List<PolizaTRO> listaPolizaRiesgosTRO = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosTRO.size(); i++) {
				  listaPolizaRiesgosTRO.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosTRO;
	    }
	  

	    @javax.inject.Inject
	    PolizaTRORepository polizasRepository;
	    
}