package libweb.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import libweb.api.model.livro.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Optional<Categoria> findByNome(String string);

}
