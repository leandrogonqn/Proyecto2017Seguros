package domainapp.dom.estado;

import java.util.Date;

import domainapp.dom.poliza.Poliza;

public interface IEstado {
	
	public void actualizarEstado(Poliza poliza);
	
	public void anulacion(Poliza poliza, Date polizaFechaBaja, String polizaMotivoBaja);

}
