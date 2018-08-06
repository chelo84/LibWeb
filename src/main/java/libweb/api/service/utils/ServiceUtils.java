package libweb.api.service.utils;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import libweb.api.model.livro.Categoria;
import libweb.api.repository.AutorRepository;
import libweb.api.repository.CarteiraRepository;
import libweb.api.repository.CategoriaRepository;

@Component
public class ServiceUtils {
  private static ServiceUtils instance;

  @Autowired
  private CarteiraRepository carteiraRepository;
  
  @Autowired
  private AutorRepository autorRepository;
  
  @Autowired
  private CategoriaRepository categoriaRepository;

  @PostConstruct
  public void fillInstance() {
    instance = this;
  }

  public static CarteiraRepository getCarteiraRepository() {
	    return instance.carteiraRepository;
  }
  
  public static CategoriaRepository getCategoriaRepository() {
	  return instance.categoriaRepository;
  }

	public static AutorRepository getAutorRepository() {
		return instance.autorRepository;
	}
}

