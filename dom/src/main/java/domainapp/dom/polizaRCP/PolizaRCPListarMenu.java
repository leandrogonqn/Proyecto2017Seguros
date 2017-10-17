package domainapp.dom.polizaRCP;

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
        repositoryFor = PolizaRCP.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaRCPListarMenu {
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas RCP")
	  @MemberOrder(sequence = "110")
	  public List<PolizaRCP> listar() {
			  List<PolizaRCP> listaPolizaRiesgosRCP = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosRCP.size(); i++) {
				  listaPolizaRiesgosRCP.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosRCP;
	    }
	  

	    @javax.inject.Inject
	    PolizaRCPRepository polizasRepository;
	    
}