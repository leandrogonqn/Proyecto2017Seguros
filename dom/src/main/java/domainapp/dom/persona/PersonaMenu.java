package domainapp.dom.persona;

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

import domainapp.dom.detalleTipoPago.DetalleTipoPago;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DetalleTipoPago.class
)
@DomainServiceLayout(
        named = "Clientes",
        menuOrder = "1.1"
)
public class PersonaMenu {
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
    		named="Listar Todos los Clientes")
    @MemberOrder(sequence = "2")
    public List<Persona> listarPersona() {
        return personaRepository.listar();
    }
    
    @Inject
    PersonaRepository personaRepository;

}
