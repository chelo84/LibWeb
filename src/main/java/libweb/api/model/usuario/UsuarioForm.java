package libweb.api.model.usuario;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import libweb.api.repository.UsuarioRepository;
import libweb.api.service.utils.Argon2Utils;

public class UsuarioForm {
	private Long id;
	private String nome;
	private String sobrenome;
	private String nomeDeUsuario;
	private String senha;
	private String confirmaSenha;
	private String email;
	
	public UsuarioForm() {
		
	}
	
	public UsuarioForm(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.nomeDeUsuario = usuario.getNomeDeUsuario();
		this.senha = "";
		this.confirmaSenha = "";
		this.email = usuario.getEmail();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		
		usuario.setNome(this.nome);
		usuario.setSobrenome(this.sobrenome);
		usuario.setNomeDeUsuario(this.nomeDeUsuario);
		usuario.setSenha(this.senha);
		usuario.setEmail(this.email);
		
		return usuario;
	}

	public void update(Usuario usuario, RedirectAttributes redirectAttributes, UsuarioRepository usuarioRepository) {
		
		if(!this.getNome().trim().isEmpty() && !usuario.getNome().equals(this.getNome())) {
			usuario.setNome(this.getNome());
			redirectAttributes.addFlashAttribute("nomeSucesso", true);
		}
		
		if(!this.getSobrenome().trim().isEmpty() && !usuario.getSobrenome().equals(this.getSobrenome())) {
			usuario.setSobrenome(this.getSobrenome());
			redirectAttributes.addFlashAttribute("sobrenomeSucesso", true);
		}
		
		if(!this.getNomeDeUsuario().trim().isEmpty() && !usuario.getNomeDeUsuario().equals(this.getNomeDeUsuario())) {
			boolean existe = usuarioRepository.existsByNomeDeUsuario(this.getNomeDeUsuario());
			
			if(!existe) {
				usuario.setNomeDeUsuario(this.getNomeDeUsuario());
			}
			
			redirectAttributes.addFlashAttribute("nomeDeUsuarioSucesso", !existe);
		}
		
		if(!this.getEmail().trim().isEmpty() && !usuario.getEmail().equals(this.getEmail())) {
			boolean existe = usuarioRepository.existsByEmail(this.getEmail());
			
			if(!existe) {
				usuario.setEmail(this.getEmail());
			}
			
			redirectAttributes.addFlashAttribute("emailSucesso", !existe);
		}
		
		if(!this.getSenha().trim().isEmpty()) {
			if(this.getSenha().equals(this.getConfirmaSenha())) {
				Argon2 argon2 = Argon2Factory.create();
				
				char[] senha = this.getSenha().toCharArray();
				String hash = argon2.hash(Argon2Utils.ITERATIONS, Argon2Utils.MEMORY, Argon2Utils.PARALLELISM, senha);
				usuario.setSenha(hash);
				
				redirectAttributes.addFlashAttribute("senhaSucesso", true);
			} else {
				redirectAttributes.addFlashAttribute("confirmaSenhaSucesso", false);
			}
		}
		
		usuarioRepository.save(usuario);
	}
}
