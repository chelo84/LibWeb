package libweb.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import libweb.api.model.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNomeDeUsuario(String nomeDeUsuario);

	Optional<Usuario> findByEmail(String email);
}
