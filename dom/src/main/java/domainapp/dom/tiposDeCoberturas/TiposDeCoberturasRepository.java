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
        repositoryFor = TiposDeCoberturas.class
)
public class TiposDeCoberturasRepository {

    public List<TiposDeCoberturas> listar() {
        return repositoryService.allInstances(TiposDeCoberturas.class);
    }


    public List<TiposDeCoberturas> buscarPorNombre(final String tipoDeCoberturaNombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        TiposDeCoberturas.class,
                        "buscarPorNombre",
                        "tipoDeCoberturaNombre", tipoDeCoberturaNombre.toLowerCase()));

    }
    
    public List<TiposDeCoberturas> listarActivos(){
   	 return repositoryService.allMatches(
                new QueryDefault<>(
                        TiposDeCoberturas.class,
                        "listarActivos"));
   }
    
    public List<TiposDeCoberturas> listarInactivos(){
      	 return repositoryService.allMatches(
                   new QueryDefault<>(
                           TiposDeCoberturas.class,
                           "listarInactivos"));
      }
    
  

    public TiposDeCoberturas crear(final String tipoDeCoberturaNombre) {
        final TiposDeCoberturas object = new TiposDeCoberturas(tipoDeCoberturaNombre);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
