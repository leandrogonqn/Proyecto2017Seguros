/*******************************************************************************
 * Copyright 2017 SiGeSe
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.pacinetes.dom.tarjetadecredito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import com.pacinetes.dom.banco.Banco;
import com.pacinetes.dom.banco.BancoRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.tipotarjeta.TipoTarjeta;
import com.pacinetes.dom.tipotarjeta.TipoTarjetaRepository;

@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "listarActivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.TarjetaDeCredito " + "WHERE tipoPagoActivo == true "),
		@javax.jdo.annotations.Query(name = "listarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM com.pacinetes.dom.simple.TarjetaDeCredito " + "WHERE tipoPagoActivo == false ") })
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "TarjetaDeCredito")
@DomainObject(publishing = Publishing.ENABLED, auditing = Auditing.ENABLED)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "tarjetaDeCreditoId")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class TarjetaDeCredito extends DetalleTipoPago implements Comparable<TarjetaDeCredito> {
	// region > title
	public TranslatableString title() {
		return TranslatableString.tr("{name}", "name",
				"Tarjeta de Credito  - Titular: " + getTipoPagoTitular() + " - N°:" + getTarjetaDeCreditoNumero());
	}
	// endregion

	public static final int NAME_LENGTH = 200;
	// Constructor

	public TarjetaDeCredito(String tipoPagoTitular, TipoTarjeta tarjetaDeCreditoTipoTarjeta, Banco tipoPagoBanco,
			long tarjetaDeCreditoNumero, int tarjetaDeCreditoMesVencimiento, int tarjetaDeCreditoAnioVencimiento) {
		setTipoPagoTitular(tipoPagoTitular);
		setTarjetaDeCreditoTipoTarjeta(tarjetaDeCreditoTipoTarjeta);
		setTipoPagoBanco(tipoPagoBanco);
		setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
		setTarjetaDeCreditoMesVencimiento(tarjetaDeCreditoMesVencimiento);
		setTarjetaDeCreditoAnioVencimiento(tarjetaDeCreditoAnioVencimiento);
		this.tipoPagoActivo = true;
	}

	@Column(name = "tipoTarjetaId")
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Tipo de Tarjeta")
	private TipoTarjeta tarjetaDeCreditoTipoTarjeta;

	public TipoTarjeta getTarjetaDeCreditoTipoTarjeta() {
		return tarjetaDeCreditoTipoTarjeta;
	}

	public void setTarjetaDeCreditoTipoTarjeta(TipoTarjeta tarjetaDeCreditoTipoTarjeta) {
		this.tarjetaDeCreditoTipoTarjeta = tarjetaDeCreditoTipoTarjeta;
	}

	@Property(editing = Editing.DISABLED, optionality = Optionality.OPTIONAL)
	@PropertyLayout(named = "N° de Tarjeta")
	private long tarjetaDeCreditoNumero;

	public long getTarjetaDeCreditoNumero() {
		return tarjetaDeCreditoNumero;
	}

	public void setTarjetaDeCreditoNumero(long tarjetaDeCreditoNumero) {
		this.tarjetaDeCreditoNumero = tarjetaDeCreditoNumero;
	}

	@javax.jdo.annotations.Column
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Mes de vencimiento")
	private int tarjetaDeCreditoMesVencimiento;

	public int getTarjetaDeCreditoMesVencimiento() {
		return tarjetaDeCreditoMesVencimiento;
	}

	public void setTarjetaDeCreditoMesVencimiento(int tarjetaDeCreditoMesVencimiento) {
		this.tarjetaDeCreditoMesVencimiento = tarjetaDeCreditoMesVencimiento;
	}

	@javax.jdo.annotations.Column
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(named = "Año de vencimiento")
	private int tarjetaDeCreditoAnioVencimiento;

	public int getTarjetaDeCreditoAnioVencimiento() {
		return tarjetaDeCreditoAnioVencimiento;
	}

	public void setTarjetaDeCreditoAnioVencimiento(int tarjetaDeCreditoAnioVencimiento) {
		this.tarjetaDeCreditoAnioVencimiento = tarjetaDeCreditoAnioVencimiento;
	}

	// endregion

	// region > delete (action)
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void borrarTarjetaDeCredito() {
		final String title = titleService.titleOf(this);
		messageService.informUser(String.format("'%s' deleted", title));
		setTipoPagoActivo(false);
	}

	public TarjetaDeCredito actualizarTipoPagoTitular(
			@ParameterLayout(named = "Titular") final String tipoPagoTitular) {
		setTipoPagoTitular(tipoPagoTitular);
		return this;
	}

	public String default0ActualizarTipoPagoTitular() {
		return getTipoPagoTitular();
	}

	public TarjetaDeCredito actualizarTarjetaDeCreditoTipoTarjeta(
			@ParameterLayout(named = "Tipo de Tarjeta") final TipoTarjeta tarjetaDeCreditoTipoTarjeta) {
		setTarjetaDeCreditoTipoTarjeta(tarjetaDeCreditoTipoTarjeta);
		return this;
	}

	public TipoTarjeta default0ActualizarTarjetaDeCreditoTipoTarjeta() {
		return getTarjetaDeCreditoTipoTarjeta();
	}

	public List<TipoTarjeta> choices0ActualizarTarjetaDeCreditoTipoTarjeta() {
		return tarjetaDeCreditoTipoTarjetaRepository.listarActivos();
	}

	public TarjetaDeCredito actualizarTipoPagoBanco(@ParameterLayout(named = "Banco") final Banco tipoPagoBanco) {
		setTipoPagoBanco(tipoPagoBanco);
		return this;
	}

	public Banco default0ActualizarTipoPagoBanco() {
		return getTipoPagoBanco();
	}

	public List<Banco> choices0ActualizarTipoPagoBanco() {
		return tarjetaDeCreditoBancosRepository.listarActivos();
	}

	public TarjetaDeCredito actualizarMesVencimiento(
			@ParameterLayout(named = "Mes de Vencimiento") final int tarjetaDeCreditoMesVencimiento) {
		setTarjetaDeCreditoMesVencimiento(tarjetaDeCreditoMesVencimiento);
		return this;
	}

	public int default0ActualizarMesVencimiento() {
		return getTarjetaDeCreditoMesVencimiento();
	}

	public Collection<Integer> choices0ActualizarMesVencimiento() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			numbers.add(i);
		}
		return numbers;
	}

	public TarjetaDeCredito actualizarAnioVencimiento(
			@ParameterLayout(named = "Año de Vencimiento") final int tarjetaDeCreditoAnioVencimiento) {
		setTarjetaDeCreditoAnioVencimiento(tarjetaDeCreditoAnioVencimiento);
		return this;
	}

	public int default0ActualizarAnioVencimiento() {
		return getTarjetaDeCreditoAnioVencimiento();
	}

	public Collection<Integer> choices0ActualizarAnioVencimiento() {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Calendar hoy = Calendar.getInstance();
		int año = hoy.get(Calendar.YEAR);
		int añoposterios = año + 30;
		for (int i = año; i <= añoposterios; i++) {
			numbers.add(i);
		}
		return numbers;
	}

	public TarjetaDeCredito actualizarNumero(
			@ParameterLayout(named = "N° de tarjeta") final long tarjetaDeCreditoNumero) {
		setTarjetaDeCreditoNumero(tarjetaDeCreditoNumero);
		return this;
	}

	public long default0ActualizarNumero() {
		return getTarjetaDeCreditoNumero();
	}

	public TarjetaDeCredito actualizarActivo(@ParameterLayout(named = "Activo") final boolean tarjetaDeCreditoActivo) {
		setTipoPagoActivo(tarjetaDeCreditoActivo);
		return this;
	}

	public boolean default0ActualizarActivo() {
		return getTipoPagoActivo();
	}

	// endregion

	// region > toString, compareTo
	@Override
	public String toString() {
		return "Titular: "+getTipoPagoTitular().toString() + "N°: "+getTarjetaDeCreditoNumero();
	}

	@Override
	public int compareTo(final TarjetaDeCredito o) {
        if (getTarjetaDeCreditoNumero()<o.getTarjetaDeCreditoNumero()) {
            return -1;
        }
        if (getTarjetaDeCreditoNumero()>o.getTarjetaDeCreditoNumero()) {
            return 1;
        }	
        return 0;
	}

	// endregion

	// region > injected dependencies

	@javax.inject.Inject
	RepositoryService repositoryService;

	@javax.inject.Inject
	TitleService titleService;

	@javax.inject.Inject
	MessageService messageService;

	@Inject
	TipoTarjetaRepository tarjetaDeCreditoTipoTarjetaRepository;

	@Inject
	BancoRepository tarjetaDeCreditoBancosRepository;

	// endregion
}
