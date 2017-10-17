package domainapp.dom.polizaAP;

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
        repositoryFor = PolizaAP.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaAPListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas AP")
	  @MemberOrder(sequence = "40")
	  public List<PolizaAP> listar() {
			  List<PolizaAP> listaPolizaRiesgoAP = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoAP.size(); i++) {
				  listaPolizaRiesgoAP.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoAP;
	    }

	    @javax.inject.Inject
	    PolizaAPRepository polizasRepository;

}