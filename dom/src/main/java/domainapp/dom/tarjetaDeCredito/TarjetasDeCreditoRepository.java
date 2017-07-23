package domainapp.dom.tarjetaDeCredito;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Bancos;
import domainapp.dom.tipoTarjeta.TiposTarjetas;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TarjetasDeCredito.class
)
public class TarjetasDeCreditoRepository {

    public TarjetasDeCredito crear(String tipoPagoTitular, TiposTarjetas tipoTarjeta, Bancos banco, long tarjetaDeCreditoNumero, int tarjetaDeCreditoMesVencimiento, int tarjetaDeCreditoAnioVencimiento) {
        final TarjetasDeCredito object = new TarjetasDeCredito(tipoPagoTitular, tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    public TarjetasDeCredito crear() {
        final TarjetasDeCredito object = new TarjetasDeCredito();
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
