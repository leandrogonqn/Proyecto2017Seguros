package domainapp.dom.cliente;

public enum Sexo {
Masculino("Masculino"),
Femenino("Femenino");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Sexo(String nom) 
	{
		nombre = nom;
	}
	

	@Override
	public String toString() {
		return this.nombre;
	}
}
