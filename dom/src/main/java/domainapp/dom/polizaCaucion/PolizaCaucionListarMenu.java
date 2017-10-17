package domainapp.dom.polizaCaucion;

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
        repositoryFor = PolizaCaucion.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaCaucionListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Caucion")
	  @MemberOrder(sequence = "60")
	  public List<PolizaCaucion> listar() {
			  List<PolizaCaucion> listaPolizaRiesgoCaucion = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoCaucion.size(); i++) {
				  listaPolizaRiesgoCaucion.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoCaucion;
	    }

	  @javax.inject.Inject
	    PolizaCaucionRepository polizasRepository;
}