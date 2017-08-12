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
import domainapp.dom.tarjetaDeCredito.TarjetasDeCredito;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = DebitosAutomaticos.class
)
public class DebitosAutomaticosRepository {

    public DebitosAutomaticos crear(String tipoPagoTitular, Bancos debitoAutomaticoBanco, BigInteger debitoAutomaticoCbu) {
        final DebitosAutomaticos object = new DebitosAutomaticos(tipoPagoTitular, debitoAutomaticoBanco, debitoAutomaticoCbu);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    public List<DebitosAutomaticos> listar() {
        return repositoryService.allInstances(DebitosAutomaticos.class);
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
