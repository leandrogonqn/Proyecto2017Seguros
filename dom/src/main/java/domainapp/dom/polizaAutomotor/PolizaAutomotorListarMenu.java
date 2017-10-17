package domainapp.dom.polizaAutomotor;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, repositoryFor = PolizaAutomotor.class)
@DomainServiceLayout(named = "Polizas Listar", menuOrder = "29.1")
public class PolizaAutomotorListarMenu {

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Polizas Auto")
	@MemberOrder(sequence = "10.1")
	public List<PolizaAutomotor> listar() {
		List<PolizaAutomotor> listaPolizasRiesgoAutomotores = riesgoAutomotorRepository.listar();
		for (int i = 0; i < listaPolizasRiesgoAutomotores.size(); i++) {
			listaPolizasRiesgoAutomotores.get(i).actualizarPoliza();
		}
		return listaPolizasRiesgoAutomotores;
	}


	@Inject
	PolizaAutomotoresRepository riesgoAutomotorRepository;
}