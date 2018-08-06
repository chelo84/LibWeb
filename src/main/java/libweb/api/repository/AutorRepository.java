package libweb.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import libweb.api.model.livro.Autor;
import libweb.api.model.usuario.Usuario;

public interface AutorRepository extends JpaRepository<Autor, Long> {
	
	Optional<Autor> findByNomeAndSobrenome(String nome, String sobrenome);
}
