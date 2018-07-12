package libweb.api.service.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import libweb.api.repository.CarteiraRepository;
import libweb.api.repository.UsuarioRepository;

@Component
public class ServiceUtils {
  private static ServiceUtils instance;

  @Autowired
  private UsuarioRepository usuarioRepository;
  
  @Autowired
  private CarteiraRepository carteiraRepository;

  @PostConstruct
  public void fillInstance() {
    instance = this;
  }

  public static UsuarioRepository getUsuarioRepository() {
    return instance.usuarioRepository;
  }
  
  public static CarteiraRepository getCarteiraRepository() {
	    return instance.carteiraRepository;
  }
}

