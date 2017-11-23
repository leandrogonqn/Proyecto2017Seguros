package com.pacinetes.app.viewmodel;

import java.math.BigDecimal;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;

import com.pacinetes.dom.compania.Compania;

@DomainObjectLayout(named = "Reporte de facturacion", bookmarking = BookmarkPolicy.AS_ROOT)
@DomainObject(nature = Nature.VIEW_MODEL)
public class FacturacionCompaniasViewModel implements Comparable<FacturacionCompaniasViewModel> {

	public FacturacionCompaniasViewModel() {
	}

	FacturacionCompaniasViewModel(Compania compania) {
		this.compania = compania;
	}

	public TranslatableString title() {
		return TranslatableString.tr("Compañia: {compania}", "compania", getCompania() + ". "
				+ "Prima Total: " + getPrimaTotal() + ".");
	}

	@PropertyLayout(named = "Compania")
	private Compania compania;

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	@PropertyLayout(named = "Prima Total")
	public BigDecimal getPrimaTotal() {
		return compania.calcularPrimaTotalPorCompañia();
	}

	@Override
	public int compareTo(FacturacionCompaniasViewModel o) {
		if (getPrimaTotal().compareTo(o.getPrimaTotal()) > 0) {
			return -1;
		}
		if (getPrimaTotal().compareTo(o.getPrimaTotal()) < 0) {
			return 1;
		}
		return 0;
	}

}