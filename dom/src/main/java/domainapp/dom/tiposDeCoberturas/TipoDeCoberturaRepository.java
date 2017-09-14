package domainapp.dom.tiposDeCoberturas;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TipoDeCobertura.class
)
public class TipoDeCoberturaRepository {

    public List<TipoDeCobertura> listar() {
        return repositoryService.allInstances(TipoDeCobertura.class);
    }


    public List<TipoDeCobertura> buscarPorNombre(final String tipoDeCoberturaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        TipoDeCobertura.class,
                        "buscarPorNombre",
                        "tipoDeCoberturaNombre", tipoDeCoberturaNombre.toLowerCase()));

    }
    
    public List<TipoDeCobertura> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        TipoDeCobertura.class,
                        "listarActivos"));
   }
    
    public List<TipoDeCobertura> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           TipoDeCobertura.class,
                           "listarInactivos"));
      }
    
  

    public TipoDeCobertura crear(final String tipoDeCoberturaNombre) {
        final TipoDeCobertura object = new TipoDeCobertura(tipoDeCoberturaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
