package domainapp.dom.polizaLCT;

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
        repositoryFor = PolizaLCT.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaLCTListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas LCT")
	  @MemberOrder(sequence = "90")
	  public List<PolizaLCT> listar() {
			  List<PolizaLCT> listaPolizaRiesgoLCT = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoLCT.size(); i++) {
				  listaPolizaRiesgoLCT.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoLCT;
	    }

	    @javax.inject.Inject
	    PolizaLCTRepository polizasRepository;
}