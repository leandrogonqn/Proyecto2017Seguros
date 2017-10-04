package domainapp.app.services.homepage;

import java.util.List;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.services.i18n.TranslatableString;
import domainapp.dom.cliente.Cliente;
import domainapp.dom.cliente.ClienteRepository;
import domainapp.dom.persona.Persona;
import domainapp.dom.persona.PersonaRepository;

@ViewModel
public class HomePageViewModel {

    //region > title
    public TranslatableString title() {
        return TranslatableString.tr("{num} objects", "num", getObjects().size());
    }
    //endregion

    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    public List<Persona> getObjects() {
        return personaRepository.listarActivos();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    PersonaRepository personaRepository;

    //endregion
}