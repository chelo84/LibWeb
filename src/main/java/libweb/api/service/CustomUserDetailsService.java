package libweb.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import libweb.api.model.CustomUserDetails;
import libweb.api.model.usuario.Usuario;
import libweb.api.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String nomeDeUsuario) throws UsernameNotFoundException {
		Optional<Usuario> optionalUsuario = usuarioRepository.findByNomeDeUsuario(nomeDeUsuario);

		optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Nome de usuario nao encontrado"));
		
		return optionalUsuario.map(CustomUserDetails::new).get();
	}
}
