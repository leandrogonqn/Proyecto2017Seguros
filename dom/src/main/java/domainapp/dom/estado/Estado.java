package domainapp.dom.estado;

import java.util.Date;

import javax.swing.JOptionPane;

import domainapp.dom.estado.IEstados;
import domainapp.dom.poliza.Polizas;

public enum Estado implements IEstados{
	previgente("previgente"){
		@Override
		public void actualizarEstado(Polizas poliza) {
			// TODO Auto-generated method stub
			Date hoy = new Date();
			if (hoy.before(poliza.getPolizaFechaVigencia())){
				
			}
			else {
				poliza.setPolizaEstado(vigente);
				poliza.getPolizaEstado().actualizarEstado(poliza);
			}
		}
	},
	vigente("vigente"){
		@Override
		public void actualizarEstado(Polizas poliza) {
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
		public void actualizarEstado(Polizas poliza) {
			// TODO Auto-generated method stub
			Date hoy = new Date();
			if (hoy.after(poliza.getPolizaFechaVencimiento())) {
				
			}
			else {
				poliza.setPolizaEstado(vigente);
				poliza.getPolizaEstado().actualizarEstado(poliza);
			}
		}
		
	},
	anulada("anulada"){
		
		@Override
		public void actualizarEstado(Polizas poliza) {
			// TODO Auto-generated method stub
		}
	
		@Override
		public void anulacion(Polizas poliza, Date polizaFechaBaja, String polizaMotivoBaja) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "No se puede anular la poliza porque ya está anulada");
		}
	};
	
	@Override
	public void anulacion(Polizas poliza, Date polizaFechaBaja, String polizaMotivoBaja) {
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
	
}
