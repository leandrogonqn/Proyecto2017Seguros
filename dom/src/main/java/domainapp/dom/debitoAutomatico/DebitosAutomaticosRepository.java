package domainapp.dom.debitoAutomatico;

import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DebitosAutomaticos.class
)
public class DebitosAutomaticosRepository {

    public DebitosAutomaticos crear(int debitoAutomaticoCbu, float tipoPagoImporte) {
        final DebitosAutomaticos object = new DebitosAutomaticos(debitoAutomaticoCbu, tipoPagoImporte);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
