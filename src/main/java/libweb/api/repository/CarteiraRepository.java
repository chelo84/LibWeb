package libweb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import libweb.api.model.usuario.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
	
}
