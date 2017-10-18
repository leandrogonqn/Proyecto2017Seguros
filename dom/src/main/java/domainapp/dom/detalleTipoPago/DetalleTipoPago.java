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
package domainapp.dom.detalleTipoPago;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

import domainapp.dom.banco.Banco;

@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "buscarPorTitular", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPago "
                    + "WHERE tipoPagoTitular.toLowerCase().indexOf(:tipoPagoTitular) >= 0 "),
    @javax.jdo.annotations.Query(
            name = "listarActivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPagos "
                    + "WHERE tipoPagoActivo == true "),
    @javax.jdo.annotations.Query(
            name = "listarInactivos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.simple.DetalleTipoPagos "
                    + "WHERE tipoPagoActivo == false ")})
@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"
)
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public abstract class DetalleTipoPago {
	
    public String cssClass(){
    	return (getTipoPagoActivo()==true)? "activo":"inactivo";
    }
    
	@Column
	@Property(
			editing=Editing.DISABLED
	)
	@PropertyLayout(named="Titular")
	protected String tipoPagoTitular;
	    
	public String getTipoPagoTitular() {
		return tipoPagoTitular;
	}

	public void setTipoPagoTitular(String tipoPagoTitular) {
		this.tipoPagoTitular = tipoPagoTitular;
	}
	
	@Column(name="bancoId")
	@Property(
			editing=Editing.DISABLED
			)
	@PropertyLayout(named="Banco")
	private Banco tipoPagoBanco;
	
	public Banco getTipoPagoBanco() {
		return tipoPagoBanco;
	}
	public void setTipoPagoBanco(Banco tipoPagoBanco) {
		this.tipoPagoBanco = tipoPagoBanco;
	}
	
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(
    		editing=Editing.DISABLED
	)
    @PropertyLayout(named="Activo")
    protected boolean tipoPagoActivo;
    
	public boolean getTipoPagoActivo() {
		return tipoPagoActivo;
	}

	public void setTipoPagoActivo(boolean tipoPagoActivo) {
		this.tipoPagoActivo = tipoPagoActivo;
	}
	
	//acciones
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Todos los Pagos")
    @MemberOrder(sequence = "2")
    public List<DetalleTipoPago> listarPagos() {
        return detalleTipoPagosRepository.listar();
    }

    @MemberOrder(sequence="1.2")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Pagos Activos")
	public List<DetalleTipoPago> listarActivos(){
		 return detalleTipoPagosRepository.listarActivos();
	  }
    
    @MemberOrder(sequence="1.2")
    @Action(invokeOn=InvokeOn.COLLECTION_ONLY)
    @ActionLayout(named="Listar Pagos Inactivos")
	public List<DetalleTipoPago> listarInactivos(){
		 return detalleTipoPagosRepository.listarInactivos();
	  }
    
    @Inject
    DetalleTipoPagoRepository detalleTipoPagosRepository;
	
}
