package domainapp.dom.modules.reportes;

import java.util.Date;

import domainapp.dom.localidad.Localidad;

public class ClienteReporte {
	
	private String clienteNombre;

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}
	
	private String clienteApellido;

	public String getClienteApellido() {
		return clienteApellido;
	}

	public void setClienteApellido(String clienteApellido) {
		this.clienteApellido = clienteApellido;
	}
	
	private String clienteSexo;
	
	public String getClienteSexo() {
		return clienteSexo;
	}

	public void setClienteSexo(String clienteSexo) {
		this.clienteSexo = clienteSexo;
	}

	private String personaCuitCuil;
	
	public String getPersonaCuitCuil() {
		return personaCuitCuil;
	}

	public void setPersonaCuitCuil(String personaCuitCuil) {
		this.personaCuitCuil = personaCuitCuil;
	}

	public int clienteDni;

	public int getClienteDni() {
		return clienteDni;
	}

	public void setClienteDni(int clienteDni) {
		this.clienteDni = clienteDni;
	}
	
    private String personaLocalidad;

	public void setPersonaLocalidad(String personaLocalidad) {
		this.personaLocalidad = personaLocalidad;
	}

	public String getPersonaLocalidad() {
		return personaLocalidad;
	}
	
	private String personaProvincia;

	public String getPersonaProvincia() {
		return personaProvincia;
	}

	public void setPersonaProvincia(String personaProvincia) {
		this.personaProvincia = personaProvincia;
	}
	
	private String personaDireccion;

    public String getPersonaDireccion() {
		return personaDireccion;
	}
	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}	

    private String personaTelefono;

    public String getPersonaTelefono() {
		return personaTelefono;
	}
	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}	
	
    private Date clienteFechaNacimiento;

    public Date getClienteFechaNacimiento() {
		return clienteFechaNacimiento;
	}
	public void setClienteFechaNacimiento(Date clienteFechaNacimiento) {
		this.clienteFechaNacimiento = clienteFechaNacimiento;
	}	
	

}
