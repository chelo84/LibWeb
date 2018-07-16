package libweb.api.model.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_usuario")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
		
	@Column(name = "sobrenome")
	private String sobrenome;
		
	@Column(name = "nome_de_usuario")
	private String nomeDeUsuario;
	
	@Column(name = "senha")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;
		
	@OneToOne()
	@JoinColumn(name = "id_role")
	private Role role;
		
	@OneToOne()
	@JoinColumn(name = "id_carteira")
	private Carteira carteira;
		
	@Column(name = "email")
	private String email;
	
	public Usuario() {
	}
	
	public Usuario(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.nomeDeUsuario = usuario.getNomeDeUsuario();
		this.senha = usuario.getSenha();
		this.role = usuario.getRole();
		this.carteira = usuario.getCarteira();
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
