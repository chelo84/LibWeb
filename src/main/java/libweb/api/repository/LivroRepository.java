package libweb.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import libweb.api.model.livro.Autor;
import libweb.api.model.livro.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	Optional<Autor> findByNome(String nome);
	
}
