package libweb.api.controller;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import libweb.api.model.livro.Autor;
import libweb.api.model.livro.Categoria;
import libweb.api.model.livro.Livro;
import libweb.api.model.livro.LivroForm;
import libweb.api.model.validator.LivroFormValidator;
import libweb.api.repository.AutorRepository;
import libweb.api.repository.CategoriaRepository;
import libweb.api.repository.LivroRepository;
import libweb.api.service.utils.ServiceUtils;

@Controller
public class LivroController {
	
	@Autowired
	LivroFormValidator livroFormValidator;
	
	@PersistenceContext
    EntityManager em;

	@Autowired
	LivroRepository livroRepository;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(livroFormValidator);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/livros")
	public String livros(Model model) {
		return "livros.html";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/novo-livro")
	public String novoLivro(Model model) {
		
		LivroForm livroForm = new LivroForm();
		model.addAttribute("livroForm", livroForm);
		return "novo-livro.html";
	}
	
	@Transactional 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/novo-livro", method=RequestMethod.POST)
    public String novoLivroPost(@ModelAttribute("livroForm") @Validated LivroForm livroForm,
    		BindingResult result, Model model,
    		final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "novo-livro.html";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Livro adicionado com sucesso!");
		}
		
		Livro livro = livroForm.toLivro();
		
		AutorRepository autorRepository = ServiceUtils.getAutorRepository();
		if(livroForm.isNovoAutor()) {
			autorRepository.saveAndFlush(livro.getAutor());
		}
		
		Autor autor = autorRepository.findByNomeAndSobrenome(livro.getAutor().getNome(), livro.getAutor().getSobrenome()).get();
		livro.setAutor(autor);
		
		CategoriaRepository categoriaRepository = ServiceUtils.getCategoriaRepository();
		for(Categoria categoria : livro.getCategorias()) {
			if(categoria.getId() == null) {
				categoriaRepository.saveAndFlush(categoria);
				
				categoria = (Categoria) categoriaRepository.findByNome(categoria.getNome()).get();
			}
		}
		
		livroRepository.saveAndFlush(livro);
		
		return "redirect:/livros";
	}
	
	@ModelAttribute("livros")
	@ResponseBody
	public List<Livro> getAllLivros() {
	    List<Livro> listLivro = livroRepository.findAll();
	    
	    return listLivro;
	}
	
	@ModelAttribute("autores")
	@ResponseBody
	public List<Autor> getAllAutores() {
		List<Autor> listAutor = ServiceUtils.getAutorRepository().findAll();
		
		return listAutor;
	}
	
	@ModelAttribute("categorias")
	@ResponseBody
	public List<Categoria> getAllCategorias() {
		List<Categoria> listCategoria = ServiceUtils.getCategoriaRepository().findAll();
		
		return listCategoria;
	}
}
