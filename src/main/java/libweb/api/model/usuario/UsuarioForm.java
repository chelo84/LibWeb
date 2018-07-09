package libweb.api.model.usuario;

public class UsuarioForm {
	private String nome;
	private String sobrenome;
	private String nomeDeUsuario;
	private String senha;
	private String confirmaSenha;
	private String email;
	
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
}
