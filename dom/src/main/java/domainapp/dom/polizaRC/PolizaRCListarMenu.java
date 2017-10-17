package domainapp.dom.polizaRC;

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
        repositoryFor = PolizaRC.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaRCListarMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas RC")
	  @MemberOrder(sequence = "100")
	  public List<PolizaRC> listar() {
			  List<PolizaRC> listaPolizaRiesgosRC = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosRC.size(); i++) {
				  listaPolizaRiesgosRC.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosRC;
	    }

	    @javax.inject.Inject
	    PolizaRCRepository polizasRepository;
	    
}