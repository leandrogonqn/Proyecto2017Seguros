package domainapp.dom.polizaCombinadoFamiliar;

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
        repositoryFor = PolizaCombinadoFamiliar.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "29.2"
)
public class PolizaCombinadoFamiliarListarMenu {
	
	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Combinados Familiares")
	  @MemberOrder(sequence = "20")
	  public List<PolizaCombinadoFamiliar> listar() {
			  List<PolizaCombinadoFamiliar> listaPolizaRiesgoCombinadosFamiliares = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgoCombinadosFamiliares.size(); i++) {
				  listaPolizaRiesgoCombinadosFamiliares.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgoCombinadosFamiliares;
	    }
	  
	    @javax.inject.Inject
	    PolizaCombinadoFamiliarRepository polizasRepository;
}