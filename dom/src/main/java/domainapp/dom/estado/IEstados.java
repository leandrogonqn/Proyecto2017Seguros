package domainapp.dom.estado;

import domainapp.dom.poliza.Polizas;

public interface IEstados {
	
	public void actualizarEstado(Polizas poliza);
	
	public void anulacion(Polizas poliza);

}
