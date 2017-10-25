package domainapp.dom.poliza;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JOptionPane;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.registry.ServiceRegistry2;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.compania.Compania;
import domainapp.dom.estado.Estado;
import domainapp.dom.persona.Persona;
import domainapp.dom.vehiculo.Vehiculo;


@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Poliza.class
)
public class PolizaRepository {

    public List<Poliza> listar() {
        return repositoryService.allInstances(Poliza.class);
    }

    public List<Poliza> buscarpolizaNumero(final String polizaNumero) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorNumeroPoliza",
                        "polizaNumero", polizaNumero.toLowerCase()));
    }
    
    public List<Poliza> listarPorEstado(final Estado polizaEstado) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                		Poliza.class,
                        "listarPorEstado",
                        "polizaEstado", polizaEstado));
    }
    
    public List<Poliza> buscarPorCliente(final Persona polizaCliente) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorCliente",
                        "polizaCliente", polizaCliente));
    }
    
    //Este metodo te lista las polizas vigentes que aun no tienen renovacion
    //ordenadas por fecha de vencimiento (de la que falta menos a la que falta mas)
    public List<Poliza> listarPolizasPorVencimiento(){
    	List<Poliza> listaPolizas = listarPorEstado(Estado.vigente);
    	Iterator<Poliza> it = listaPolizas.iterator();
    	while (it.hasNext()) {
			Poliza lista = it.next();
			if (lista.polizaRenovacion != null) {
				it.remove();
			}
		}
    	Collections.sort(listaPolizas);
    	return listaPolizas;
    }
    
    public List<Poliza> buscarPorCompania(final Compania polizaCompania) {
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Poliza.class,
                        "buscarPorCompania",
                        "polizaCompania", polizaCompania));
    }
  
    @javax.inject.Inject
    RepositoryService repositoryService;
    @javax.inject.Inject
    ServiceRegistry2 serviceRegistry;
    @Inject
    PolizaRepository polizaRepository;
}
