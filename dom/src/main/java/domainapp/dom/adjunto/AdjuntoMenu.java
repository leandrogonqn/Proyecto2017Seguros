package domainapp.dom.adjunto;

import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Adjunto.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "3.1"
)
public class AdjuntoMenu {

		
		  @Action(semantics = SemanticsOf.SAFE)
		    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todos los Adjuntos")
		    @MemberOrder(sequence = "2")
		    public List<Adjunto> listar() {
		        return adjuntosRepository.listar();
		    }

		    
		    @ActionLayout(named="Crear Adjunto")
		    @MemberOrder(sequence = "1.2")
		    public Adjunto crear(
		    		@ParameterLayout(named="Descripcion") final String adjuntoDescripcion,
		            @ParameterLayout(named="Imagen") final Blob adjuntoImagen){
		        return adjuntosRepository.crear(adjuntoDescripcion, adjuntoImagen);
		    }

		    @Action(semantics = SemanticsOf.SAFE)
		    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Adjunto por descripcion")
		    @MemberOrder(sequence = "5")
		    public List<Adjunto> buscarPorDescripcion(
		            @ParameterLayout(named="Descripcion")
		            final String adjuntoDescripcion){
		        return adjuntosRepository.buscarPorDescripcion(adjuntoDescripcion);

		    }

		    @javax.inject.Inject
		    AdjuntoRepository adjuntosRepository;

}
