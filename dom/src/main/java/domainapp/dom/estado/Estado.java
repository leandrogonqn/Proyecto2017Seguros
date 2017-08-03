package domainapp.dom.estado;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import domainapp.dom.estado.IEstados;
import domainapp.dom.poliza.Polizas;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotoresMenu;
import domainapp.dom.riesgoAutomotor.RiesgoAutomotoresRepository;

public enum Estado implements IEstados{
	previgente("Previgente"),
	vigenteConRenovacion("Vigente Con Renovacion ya emitida"),
	vigenteSinRenovacionConVencimientoMenor15Dias("Vigente S/Renovacion ¡Vence en menos de 15 días!"),
	vigenteSinRenovacionConVencimientoMenor30Dias("Vigente S/Renovacion ¡Vence en menos de 30 días!"),
	vigenteSinRenovacionConVencimientoMayor30Dias("Vigente"),
	vencida("Vencida"),
	anulada("Anulada"){
		
		@Override
		public void actualizarEstado(Polizas poliza) {
			// TODO Auto-generated method stub
		}
	
		@Override
		public void anulacion(Polizas poliza) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null, "No se puede anular la poliza porque ya está anulada");
		}
	};
	
	@Override
	public void anulacion(Polizas poliza) {
		// TODO Auto-generated method stub
		poliza.setPolizaEstado(anulada);
	}
	
	@Override
	public void actualizarEstado(Polizas poliza) {
		// TODO Auto-generated method stub
		verificarVencida(poliza);
	}
	
	public void verificarVencida(Polizas poliza){
		Date hoy = new Date();
		
		if (hoy.after(poliza.getPolizaFechaVencimiento())){
			poliza.setPolizaEstado(vencida);
		}
		else 
		{
			verificarPreVigenteoVigente(poliza);
		}
	}
	
	public void verificarPreVigenteoVigente(Polizas poliza){
		Date hoy = new Date();
		if (hoy.before(poliza.getPolizaFechaVigencia())){
			poliza.setPolizaEstado(previgente);
		}
		else{
			verificarVigenteConOSinRenovacion(poliza);
		}
	}
	
	public void verificarVigenteConOSinRenovacion(Polizas poliza){
		if(poliza.getPolizaRenovacion()==null)
		{
			verificarSinRenovacionMasOMenos30Dias(poliza);
		}
		else{
			poliza.setPolizaEstado(vigenteConRenovacion);
		}
	}
	
	public void verificarSinRenovacionMasOMenos30Dias(Polizas poliza){
		Calendar hoyMasTreintaDias = Calendar.getInstance(); 
		hoyMasTreintaDias.add(hoyMasTreintaDias.DATE, 30);
		Date hoyMasTreintaDiasDate = hoyMasTreintaDias.getTime();
		
		if (hoyMasTreintaDiasDate.before(poliza.getPolizaFechaVencimiento())){
			poliza.setPolizaEstado(vigenteSinRenovacionConVencimientoMayor30Dias);
		}
		else{
			verificarMenosDe15o30Dias(poliza);
		}
	}
	
	public void verificarMenosDe15o30Dias(Polizas poliza){
		Calendar hoyMasQuinceDias = Calendar.getInstance(); 
		hoyMasQuinceDias.add(hoyMasQuinceDias.DATE, 15);
		Date hoyMasQuinceDiasDate = hoyMasQuinceDias.getTime();
		if (hoyMasQuinceDiasDate.before(poliza.getPolizaFechaVencimiento())){
			poliza.setPolizaEstado(vigenteSinRenovacionConVencimientoMenor30Dias);
		}
		else{
			poliza.setPolizaEstado(vigenteSinRenovacionConVencimientoMenor15Dias);
		}
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
