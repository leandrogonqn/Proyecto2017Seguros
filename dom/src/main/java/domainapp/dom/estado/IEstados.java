package domainapp.dom.estado;

import java.util.Date;

import domainapp.dom.poliza.Polizas;

public interface IEstados {
	
	public void actualizarEstado(Polizas poliza);
	
	public void anulacion(Polizas poliza, Date polizaFechaBaja, String polizaMotivoBaja);

}
