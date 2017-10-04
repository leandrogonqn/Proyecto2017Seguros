package domainapp.dom.persona;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Persona.class
)
public class PersonaRepository {

    public List<Persona> listar() {
        return repositoryService.allInstances(Persona.class);
    }
	
	public List<Persona> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  Persona.class,
	                  "listarActivos"));
	  }
	
	public List<Persona> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  Persona.class,
	                  "listarInactivos"));
	  }
	
	@Inject
	RepositoryService repositoryService;
	
}
