
package domainapp.app.viewmodel;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.dom.persona.Persona;
import domainapp.dom.poliza.Poliza;

@DomainObjectLayout(
        named="Cantidad de Siniestros por Polizas",
        bookmarking = BookmarkPolicy.AS_ROOT        
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class SiniestroClienteViewModel implements Comparable<SiniestroClienteViewModel>{
	
    public TranslatableString title() {
        return TranslatableString.tr("La poliza {poliza}", "poliza", getPoliza()+" tiene "+getSiniestros());
    }
    
	public SiniestroClienteViewModel() {}
		
	public SiniestroClienteViewModel(Persona persona, Poliza poliza) {
		this.persona = persona;
		this.poliza = poliza;
	}
	
	@PropertyLayout(named="Cliente")
	private Persona persona;
	public Persona getPersona() {return persona;}
	public void setPersona(Persona persona) {this.persona = persona;}
	
	@PropertyLayout(named="Poliza")
	private Poliza poliza;
	public Poliza getPoliza() {return poliza;}
	public void setPoliza(Poliza poliza) {this.poliza = poliza;}
	
	@PropertyLayout(named="Siniestros")
	public int getSiniestros(){
		return poliza.cantidadDeSiniestrosPorCliente(persona);
				
	}

    @Override
    public int compareTo(SiniestroClienteViewModel o) {
        if (getSiniestros()<o.getSiniestros()) {
            return -1;
        }
        if (getSiniestros()>o.getSiniestros()) {
            return 1;
        }	
        return 0;
    }
}