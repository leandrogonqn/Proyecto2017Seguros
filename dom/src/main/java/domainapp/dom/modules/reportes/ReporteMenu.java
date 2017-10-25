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
package domainapp.dom.modules.reportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.isis.applib.value.Blob;

import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.empresa.Empresa;
import domainapp.dom.empresa.EmpresaRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Reportes",
        menuOrder = "100"
)
public class ReporteMenu {
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
	public Blob imprimirCliente(
            @ParameterLayout(named="Cliente: ") final Cliente clienteSeleccionado) throws JRException, IOException {
    	
	    	List<Object> objectsReport = new ArrayList<Object>();
			
			ClienteReporte clienteReporte = new ClienteReporte();
			
			clienteReporte.setClienteApellido(clienteSeleccionado.getClienteApellido());
			clienteReporte.setClienteNombre(clienteSeleccionado.getClienteNombre());
			clienteReporte.setClienteSexo(clienteSeleccionado.getClienteSexo().toString());
			clienteReporte.setClienteDni(clienteSeleccionado.getClienteDni());
			clienteReporte.setClienteFechaNacimiento(clienteSeleccionado.getClienteFechaNacimiento());
			clienteReporte.setPersonaLocalidad(clienteSeleccionado.getPersonaLocalidad().getLocalidadesNombre());
			clienteReporte.setPersonaProvincia(clienteSeleccionado.getPersonaLocalidad().getLocalidadProvincia().getProvinciasNombre().toString());
			clienteReporte.setPersonaCuitCuil(clienteSeleccionado.getPersonaCuitCuil());
			clienteReporte.setPersonaDireccion(clienteSeleccionado.getPersonaDireccion());
			clienteReporte.setPersonaTelefono(clienteSeleccionado.getPersonaTelefono());
			
			objectsReport.add(clienteReporte);
			String jrxml = "Cliente.jrxml";
			String nombreArchivo = "Cliente_"+clienteSeleccionado.getClienteApellido()+"_"+clienteSeleccionado.getClienteNombre()+"_"+clienteSeleccionado.getPersonaCuitCuil();
			
			return reporteRepository.imprimirReporteIndividual(objectsReport,jrxml, nombreArchivo);
    }
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
	public Blob imprimirEmpresa(
            @ParameterLayout(named="Empresa: ") final Empresa empresaSeleccionada) throws JRException, IOException {
    	
    		List<Object> objectsReport = new ArrayList<Object>();
		
			EmpresaReporte empresaReporte = new EmpresaReporte();
			
			empresaReporte.setEmpresaRazonSocial(empresaSeleccionada.getEmpresaRazonSocial());
			empresaReporte.setPersonaLocalidad(empresaSeleccionada.getPersonaLocalidad().getLocalidadesNombre());
			empresaReporte.setPersonaProvincia(empresaSeleccionada.getPersonaLocalidad().getLocalidadProvincia().getProvinciasNombre().toString());
			empresaReporte.setPersonaCuitCuil(empresaSeleccionada.getPersonaCuitCuil());
			empresaReporte.setPersonaDireccion(empresaSeleccionada.getPersonaDireccion());
			empresaReporte.setPersonaTelefono(empresaSeleccionada.getPersonaTelefono());
			
			objectsReport.add(empresaReporte);
			String jrxml = "Empresa.jrxml";
			String nombreArchivo = "Empresa_"+empresaSeleccionada.getEmpresaRazonSocial()+"_"+empresaSeleccionada.getPersonaCuitCuil();
			
			return reporteRepository.imprimirReporteIndividual(objectsReport,jrxml, nombreArchivo);
    }
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
	public Blob imprimirClientesActivos() throws JRException, IOException {
		
		ClientesActivosDataSource datasource = new ClientesActivosDataSource();
		
		for (Cliente cli : clientesRepository.listarActivos()){
		
			ClientesActivosReporte reporteClientesActivos=new ClientesActivosReporte();
			
			reporteClientesActivos.setClienteNombre(cli.getClienteNombre());
			reporteClientesActivos.setClienteApellido(cli.getClienteApellido());
			reporteClientesActivos.setClienteDni(cli.getClienteDni());
			reporteClientesActivos.setClienteTipoDocumento(cli.getClienteTipoDocumento().toString());
			reporteClientesActivos.setPersonaMail(cli.getPersonaMail());
			reporteClientesActivos.setPersonaTelefono(cli.getPersonaTelefono());
			
			datasource.addParticipante(reporteClientesActivos);
		}
		String jrxml = "ClientesActivos.jrxml";
		String nombreArchivo = "Listado Clientes Activos ";
		
		InputStream input = ReporteRepository.class.getResourceAsStream(jrxml);
		JasperDesign jd = JRXmlLoader.load(input);
		
		JasperReport reporte = JasperCompileManager.compileReport(jd);
		Map<String, Object> parametros = new HashMap<String, Object>();
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
		
		return reporteRepository.imprimirReporteLista(jasperPrint, jrxml, nombreArchivo);
		
	}
    
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
	public Blob imprimirEmpresasActivas() throws JRException, IOException {
		
		EmpresasActivasDataSource datasource = new EmpresasActivasDataSource();
		
		for (Empresa emp : empresaRepository.listar()){
		
			EmpresasActivasReporte reporteEmpresasActivas =new EmpresasActivasReporte();
			
			reporteEmpresasActivas.setEmpresaRazonSocial(emp.getEmpresaRazonSocial());
			reporteEmpresasActivas.setPersonaCuitCuil(emp.getPersonaCuitCuil());
			reporteEmpresasActivas.setPersonaMail(emp.getPersonaMail());
			reporteEmpresasActivas.setPersonaTelefono(emp.getPersonaTelefono());
			
			datasource.addParticipante(reporteEmpresasActivas);
		}
		String jrxml = "EmpresasActivas.jrxml";
		String nombreArchivo = "Listado Empresas Activas ";
		
		InputStream input = ReporteRepository.class.getResourceAsStream(jrxml);
		JasperDesign jd = JRXmlLoader.load(input);
		
		JasperReport reporte = JasperCompileManager.compileReport(jd);
		Map<String, Object> parametros = new HashMap<String, Object>();
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
		
		return reporteRepository.imprimirReporteLista(jasperPrint, jrxml, nombreArchivo);
		
	}
	
    
	public List<Cliente> choices0ImprimirCliente(){
		return clientesRepository.listarActivos();
	}
	
	public List<Empresa> choices0ImprimirEmpresa(){
		return empresaRepository.listar();
	}
	
	@Inject
	ClienteRepository clientesRepository;
	
	@Inject
	EmpresaRepository empresaRepository;
	
	@Inject
	ReporteRepository reporteRepository;
}
