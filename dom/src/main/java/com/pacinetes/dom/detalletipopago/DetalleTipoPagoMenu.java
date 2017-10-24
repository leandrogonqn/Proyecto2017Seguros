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
package com.pacinetes.dom.detalletipopago;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.message.MessageService;

import com.pacinetes.dom.debitoautomatico.DebitoAutomaticoRepository;
import com.pacinetes.dom.detalletipopago.DetalleTipoPago;
import com.pacinetes.dom.detalletipopago.DetalleTipoPagoRepository;
import com.pacinetes.dom.tarjetadecredito.TarjetaDeCreditoRepository;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = DetalleTipoPago.class
)
@DomainServiceLayout(
        named = "Tipo de pago",
        menuOrder = "60.1"
)
public class DetalleTipoPagoMenu {
	
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT,
	    		named="Listar Todos los Pagos")
	    @MemberOrder(sequence = "2")
	    public List<DetalleTipoPago> listarPagos() {
	        return detalleTipoPagosRepository.listar();
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "5")
	    public List<DetalleTipoPago> buscarPorTitular(
	            @ParameterLayout(named="Titular")
	            final String tipoPagoTitular
	    ) {
	        return detalleTipoPagosRepository.buscarPorTitular(tipoPagoTitular);
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search")
	    @MemberOrder(sequence = "6")
	    public List buscarPorTipoDePago(
	            @ParameterLayout(named="Tipo de Pago")
	            final TipoPago polizaTipoDePago
	    ) {
	    	if (polizaTipoDePago == TipoPago.Tarjeta_De_Credito){
	    		return tarjetaDeCreditoRepository.listar();
	    	}else
	    	{
	    		if (polizaTipoDePago == TipoPago.Debito_Automatico){
	    			return debitoAutomaticoRepository.listar();
	    		}else
	    		{
	    			if (polizaTipoDePago == TipoPago.Efectivo)
	    	    	messageService.warnUser("No se puede mostrar una lista de Efectivo");
	    		}
	    	}

	    	return null;
	        //return detalleTipoPagosRepository.buscarPorTipoDePago(polizaTipoDePago.toString());
	    }
	    
	    @Action(semantics = SemanticsOf.SAFE)
	    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa="fa-search", hidden=Where.EVERYWHERE)
	    @MemberOrder(sequence = "6")
	    public List buscarPorTipoDePagoCombo(
	            @ParameterLayout(named="Tipo de Pago")
	            final TipoPago polizaTipoDePago
	    ) {
	    	if (polizaTipoDePago == TipoPago.Tarjeta_De_Credito){
	    		return tarjetaDeCreditoRepository.listarActivos();
	    	}else
	    	{
	    		if (polizaTipoDePago == TipoPago.Debito_Automatico){
	    			return debitoAutomaticoRepository.listarActivos();
	    		}
	    	}
	    	return null;
	    }
	    
			  
		@javax.inject.Inject
		DetalleTipoPagoRepository detalleTipoPagosRepository;
		@Inject
		TarjetaDeCreditoRepository tarjetaDeCreditoRepository;
		@Inject 
		DebitoAutomaticoRepository debitoAutomaticoRepository;
		@Inject
		MessageService messageService;
		
}
