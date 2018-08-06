package libweb.api.model.livro;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="livro")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_livro")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "id_autor")
	private Autor autor;
	
	@Temporal(TemporalType.DATE)  
	@Column(name = "data_de_lancamento")
	private Date dataDeLancamento;
	
	@Column(name = "img_livro")
	private String imgUrl;
	
	@Column(name = "preco")
	private double preco;
	
	@Column(name = "disponivel")
	private boolean disponivel;
	
	@JsonManagedReference
	@ManyToMany(targetEntity = Categoria.class, cascade = CascadeType.ALL)
	@JoinTable(name = "categoria_livro", joinColumns = { @JoinColumn(name = "id_livro") },
				inverseJoinColumns = { @JoinColumn(name = "id_categoria") })
	private List<Categoria> categorias;

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

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Date getDataDeLancamento() {
		return dataDeLancamento;
	}

	public void setDataDeLancamento(Date dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
}
