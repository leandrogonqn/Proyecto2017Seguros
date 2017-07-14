package domainapp.dom.debitoAutomatico;

import java.math.BigInteger;
import java.util.List;

import javax.xml.ws.Action;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Bancos;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DebitosAutomaticos.class
)
public class DebitosAutomaticosRepository {

    public DebitosAutomaticos crear(Bancos debitoAutomaticoBanco, BigInteger debitoAutomaticoCbu, float tipoPagoImporte) {
        final DebitosAutomaticos object = new DebitosAutomaticos(debitoAutomaticoBanco, debitoAutomaticoCbu, tipoPagoImporte);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
