package com.pacinetes.app.viewmodel;

import java.util.Date;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import com.pacinetes.dom.cliente.Cliente;

@DomainObjectLayout(named = "Lista de clientes que estan por cumplir años", bookmarking = BookmarkPolicy.AS_ROOT)
@DomainObject(nature = Nature.VIEW_MODEL)
public class ClientesCumpleañosViewModel implements Comparable<ClientesCumpleañosViewModel> {

	public ClientesCumpleañosViewModel() {
	}

	public ClientesCumpleañosViewModel(Cliente cliente) {
		this.cliente = cliente;
	}

	public String cssClass() {
		String a = null;
		if (this.getDiasRestantes() > 5) {
			a = "vigenteSinRenovacionConVencimientoMayor30Dias";
		} else {
			if (this.getDiasRestantes() > 2) {
				a = "vigenteSinRenovacionConVencimientoMenor30Dias";
			} else {
				a = "vigenteSinRenovacionConVencimientoMenor15Dias";
			}
		}
		return a;
	}

	@PropertyLayout(named = "Cliente")
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@PropertyLayout(named = "Fecha de Nacimiento")
	public Date getFechaNacimiento() {
		return cliente.getClienteFechaNacimiento();
	}

	@PropertyLayout(named = "Días restantes")
	public long getDiasRestantes() {
		return cliente.calcularDiasRestantesParaCumpleaños();
	}

	@Override
	public int compareTo(ClientesCumpleañosViewModel o) {
		if (getDiasRestantes() < o.getDiasRestantes()) {
			return -1;
		}
		if (getDiasRestantes() > o.getDiasRestantes()) {
			return 1;
		}
		return 0;
	}
}
