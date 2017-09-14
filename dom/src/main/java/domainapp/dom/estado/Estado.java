package domainapp.dom.estado;

import java.util.Date;
import javax.inject.Inject;
import org.apache.isis.applib.services.message.MessageService;
import domainapp.dom.estado.IEstado;
import domainapp.dom.poliza.Poliza;

public enum Estado implements IEstado{
	previgente("previgente"){
		@Override
		public void actualizarEstado(Poliza poliza) {
			// TODO Auto-generated method stub
			Date hoy = new Date();
			if (hoy.after(poliza.getPolizaFechaVigencia())){
				poliza.setPolizaEstado(vigente);
				poliza.getPolizaEstado().actualizarEstado(poliza);
			}
		}
	},
	vigente("vigente"){
		@Override
		public void actualizarEstado(Poliza poliza) {
			// TODO Auto-generated method stub
			Date hoy = new Date();
			if (hoy.before(poliza.getPolizaFechaVigencia())) {
				poliza.setPolizaEstado(previgente);
			}
			else
			{
				if (hoy.after(poliza.getPolizaFechaVencimiento())){
					poliza.setPolizaEstado(vencida);
				}
			}
		}
	},
	vencida("vencida"){
		@Override
		public void actualizarEstado(Poliza poliza) {
			// TODO Auto-generated method stub
			Date hoy = new Date();
			if (hoy.before(poliza.getPolizaFechaVencimiento())) {
				poliza.setPolizaEstado(vigente);
				poliza.getPolizaEstado().actualizarEstado(poliza);
			}
		}
	},
	anulada("anulada"){
		@Override
		public void actualizarEstado(Poliza poliza) {
			// TODO Auto-generated method stub
		}
	
		@Override
		public void anulacion(Poliza poliza, Date polizaFechaBaja, String polizaMotivoBaja) {
			// TODO Auto-generated method stub
			messageService.warnUser("No se puede anular la poliza porque ya está anulada");
		}
	};
	
	@Override
	public void anulacion(Poliza poliza, Date polizaFechaBaja, String polizaMotivoBaja) {
		// TODO Auto-generated method stub
		poliza.setPolizaEstado(anulada);
		poliza.setPolizaFechaBaja(polizaFechaBaja);
		poliza.setPolizaMotivoBaja(polizaMotivoBaja);
	}
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Estado(String nom) 
	{
		nombre = nom;
	}
	

	@Override
	public String toString() {
		return this.nombre;
	}

	@Inject
	MessageService messageService;
	
}