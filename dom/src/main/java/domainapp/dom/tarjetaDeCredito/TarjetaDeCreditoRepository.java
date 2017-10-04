package domainapp.dom.tarjetaDeCredito;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.banco.Banco;
import domainapp.dom.debitoAutomatico.DebitoAutomatico;
import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.tipoTarjeta.TipoTarjeta;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = TarjetaDeCredito.class
)
public class TarjetaDeCreditoRepository {

    public TarjetaDeCredito crear(String tipoPagoTitular, TipoTarjeta tipoTarjeta, Banco banco, long tarjetaDeCreditoNumero, int tarjetaDeCreditoMesVencimiento, int tarjetaDeCreditoAnioVencimiento) {
        final TarjetaDeCredito object = new TarjetaDeCredito(tipoPagoTitular, tipoTarjeta, banco, tarjetaDeCreditoNumero, tarjetaDeCreditoMesVencimiento, tarjetaDeCreditoAnioVencimiento);
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }

    public List<TarjetaDeCredito> listar() {
        return repositoryService.allInstances(TarjetaDeCredito.class);
    }
    
	public List<TarjetaDeCredito> listarActivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  TarjetaDeCredito.class,
	                  "listarActivos"));
	  }
	
	public List<TarjetaDeCredito> listarInactivos(){
		 return repositoryService.allMatches(
	          new QueryDefault<>(
	        		  TarjetaDeCredito.class,
	                  "listarInactivos"));
	  }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
}
