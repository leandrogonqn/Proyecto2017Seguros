package domainapp.dom.pagoEfectivo;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.detalleTipoPago.DetalleTipoPagos;
import domainapp.dom.detalleTipoPago.DetalleTipoPagosRepository;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = PagosEfectivo.class
)
public class PagosEfectivoRepository {

    public PagosEfectivo crear() {
        final PagosEfectivo object = new PagosEfectivo();
        serviceRegistry.injectServicesInto(object);
        repositoryService.persist(object);
        return object;
    }
    
	public List<DetalleTipoPagos> listarEfectivo(){
	List<DetalleTipoPagos> listaEfectivo = detalleTipoPagosRepository.listarActivos();
	Iterator<DetalleTipoPagos> it = listaEfectivo.iterator();
	while (it.hasNext()) {
	    DetalleTipoPagos lista = it.next();
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
    DetalleTipoPagosRepository detalleTipoPagosRepository;
    
}
