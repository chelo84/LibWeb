package libweb.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import libweb.api.model.usuario.Usuario;

public class CustomUserDetails extends Usuario implements UserDetails {

	public CustomUserDetails(final Usuario usuario) {
		super(usuario);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority("ROLE_"+ getRole().getRole()));
		
		return auths;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getSenha();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getNomeDeUsuario();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
