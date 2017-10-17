package domainapp.dom.polizaVidaColectivo;

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
        repositoryFor = PolizaVidaColectivo.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaVidaColectivoListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Vida Colectivo")
	  @MemberOrder(sequence = "130")
	  public List<PolizaVidaColectivo> listar() {
			  List<PolizaVidaColectivo> listaPolizaRiesgoVidaColectivo = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoVidaColectivo.size(); i++) {
				  listaPolizaRiesgoVidaColectivo.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoVidaColectivo;
	    }
	  

	    @javax.inject.Inject
	    PolizaVidaColectivoRepository polizasRepository;
	    
}