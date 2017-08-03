package domainapp.dom.cliente;

public enum TipoDocumento {
	DNI("DNI"),
	LE("Libreta de Enrolamiento"),
	LC("Libreta Civica"),
	Pasaporte("Pasaporte");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private TipoDocumento(String nom) 
	{
		nombre = nom;
	}
	

	@Override
	public String toString() {
		return this.nombre;
	}

}
