package domainapp.dom.polizaConvenioMercantil;

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
        repositoryFor = PolizaConvenioMercantil.class
)
@DomainServiceLayout(
        named = "Polizas Listar",
        menuOrder = "30"
)
public class PolizaConvenioMercantilListarMenu {

	  @Action(semantics = SemanticsOf.SAFE)
	  @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar Polizas Convenio Mercantil")
	  @MemberOrder(sequence = "70")
	  public List<PolizaConvenioMercantil> listar() {
			  List<PolizaConvenioMercantil> listaPolizaRiesgosConvenioMercantil = polizasRepository.listar();
			  for(int i=0; i< listaPolizaRiesgosConvenioMercantil.size(); i++) {
				  listaPolizaRiesgosConvenioMercantil.get(i).actualizarPoliza();
		        }
		      return listaPolizaRiesgosConvenioMercantil;
	    }
	  
	  @javax.inject.Inject
	    PolizaConvenioMercantilRepository polizasRepository;
	  
}