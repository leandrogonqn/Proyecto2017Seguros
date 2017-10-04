package domainapp.dom.detalleTipoPago;

public enum TipoPago {
Efectivo("Efectivo"), TarjetaDeCredito("TarjetaDeCredito"), DebitoAutomatico("DebitoAutomatico");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private TipoPago(String nom) 
	{
		nombre = nom;
	}
	

	@Override
	public String toString() {
		return this.nombre;
	}
}
