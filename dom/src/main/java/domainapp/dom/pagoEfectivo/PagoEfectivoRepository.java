package domainapp.dom.pagoEfectivo;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.detalleTipoPago.DetalleTipoPago;
import domainapp.dom.detalleTipoPago.DetalleTipoPagoRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PagoEfectivo.class
)
public class PagoEfectivoRepository {

    public PagoEfectivo crear() {
        final PagoEfectivo object = new PagoEfectivo();
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
	public List<DetalleTipoPago> listarEfectivo(){
	List<DetalleTipoPago> listaEfectivo = detalleTipoPagosRepository.listarActivos();
	Iterator<DetalleTipoPago> it = listaEfectivo.iterator();
	while (it.hasNext()) {
	    DetalleTipoPago lista = it.next();
	    if (!lista.getTipoPagoNombre().equals("Efectivo")) {
	    	it.remove();
	    }
	}
	return listaEfectivo;
	}

    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
    @Inject
    DetalleTipoPagoRepository detalleTipoPagosRepository;
    
}
