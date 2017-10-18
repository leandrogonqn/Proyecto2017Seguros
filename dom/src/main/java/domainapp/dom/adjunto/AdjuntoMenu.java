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
package domainapp.dom.adjunto;

import java.util.List;
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


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = Adjunto.class
)
@DomainServiceLayout(
        named = "Polizas Extras",
        menuOrder = "40.3"
)
public class AdjuntoMenu {

		
		  @Action(semantics = SemanticsOf.SAFE)
		    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Listar todos los Adjuntos")
		    @MemberOrder(sequence = "2")
		    public List<Adjunto> listar() {
		        return adjuntosRepository.listar();
		    }

		    
		    @ActionLayout(named="Crear Adjunto")
		    @MemberOrder(sequence = "1.2")
		    public Adjunto crear(
		    		@ParameterLayout(named="Descripcion") final String adjuntoDescripcion,
		            @ParameterLayout(named="Imagen") final Blob adjuntoImagen){
		        return adjuntosRepository.crear(adjuntoDescripcion, adjuntoImagen);
		    }

		    @Action(semantics = SemanticsOf.SAFE)
		    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named="Buscar Adjunto por descripcion")
		    @MemberOrder(sequence = "5")
		    public List<Adjunto> buscarPorDescripcion(
		            @ParameterLayout(named="Descripcion")
		            final String adjuntoDescripcion){
		        return adjuntosRepository.buscarPorDescripcion(adjuntoDescripcion);

		    }

		    @javax.inject.Inject
		    AdjuntoRepository adjuntosRepository;

}
