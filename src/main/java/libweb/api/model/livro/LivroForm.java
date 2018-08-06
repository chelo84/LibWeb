package libweb.api.model.livro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libweb.api.repository.CategoriaRepository;
import libweb.api.repository.UsuarioRepository;
import libweb.api.service.utils.ServiceUtils;

public class LivroForm {
	
	private Long id;
	private String nome;
	private Autor autor;
	private boolean novoAutor;
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date dataDeLancamento;
	private String imgUrl;
	private double preco;
	private boolean disponivel;
	private String categorias;
	
	public LivroForm() {
		this.disponivel = true;
	}
	
	public LivroForm(Livro livro) {
		this.id = livro.getId();
		this.nome = livro.getNome();
		this.autor = livro.getAutor();
		this.dataDeLancamento = livro.getDataDeLancamento();
		this.imgUrl = livro.getImgUrl();
		this.preco = livro.getPreco();
		this.disponivel = livro.isDisponivel();
		this.categorias = categoriasToString(livro.getCategorias());
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
	
	public boolean isNovoAutor() {
		return novoAutor;
	}

	public void setNovoAutor(boolean novoAutor) {
		this.novoAutor = novoAutor;
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

	public String getCategorias() {
		return categorias;
	}

	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}

	public Livro toLivro() {
		Livro livro = new Livro();
		
		livro.setNome(this.nome);
		livro.setAutor(this.autor);
		livro.setDataDeLancamento(this.dataDeLancamento);
		livro.setImgUrl(this.imgUrl);
		livro.setPreco(this.preco);
		livro.setDisponivel(this.disponivel);
		livro.setCategorias(stringToCategorias(this.categorias));
		
		return livro;
	}
	
	public static String categoriasToString(List<Categoria> categorias) {
		String setString = "";
		
		for(Categoria categoria : categorias) {
			setString += categoria.getNome() +",";
		}
		
		setString = setString.substring(0, setString.lastIndexOf(','));
		
		return setString;
	}

	public static List<Categoria> stringToCategorias(String categoriasString) {
		List<Categoria> list = new ArrayList<>();
		
		String[] categorias = categoriasString.split(",");
		CategoriaRepository categoriaRepository = ServiceUtils.getCategoriaRepository();
		
		for(int i = 0; i < categorias.length; i++) {
			Categoria categoria = null;
			try {
				categoria = (Categoria) categoriaRepository.findByNome(categorias[i].trim()).get();
			} catch (NoSuchElementException ex) {
				categoria = new Categoria();
				categoria.setNome(categorias[i].trim());
			}
			
			list.add(categoria);
		}
		
		return list;
	}

	public void update(Livro livro, RedirectAttributes redirectAttributes, UsuarioRepository usuarioRepository) {
		
	}
}
