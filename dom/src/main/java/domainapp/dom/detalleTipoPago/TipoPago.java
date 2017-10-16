package domainapp.dom.detalleTipoPago;

public enum TipoPago {
Efectivo("Efectivo"), Tarjeta_De_Credito("Tarjeta De Credito"), Debito_Automatico("Debito Automatico");
	
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
