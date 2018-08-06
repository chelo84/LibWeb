package libweb.api.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import libweb.api.model.livro.Autor;
import libweb.api.model.livro.LivroForm;
import libweb.api.repository.AutorRepository;
import libweb.api.repository.LivroRepository;

@Component
public class LivroFormValidator implements Validator {

	@Autowired
	LivroRepository livroRepository;
	
	@Autowired
	AutorRepository autorRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return LivroForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LivroForm livro = (LivroForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "NotEmpty.livroForm.nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autor.nome", "NotEmpty.livroForm.autor.nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autor.sobrenome", "NotEmpty.livroForm.autor.sobrenome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataDeLancamento", "NotEmpty.livroForm.dataDeLancamento");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "preco", "NotEmpty.livroForm.preco");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "disponivel", "NotEmpty.livroForm.disponivel");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categorias", "NotEmpty.livroForm.categorias");
		
		if(livroRepository.findByNome(livro.getNome())
				.isPresent()) {
			errors.rejectValue("nome", "AlreadyExists.livroForm.nome");
		}
		
		Autor autor = livro.getAutor();
		boolean autorExiste = autorRepository
									.findByNomeAndSobrenome(autor.getNome(), autor.getSobrenome())
									.isPresent();
		
		if(!autorExiste && !livro.isNovoAutor()) {
			errors.rejectValue("autor", "DoesntExist.livroForm.autor");
		} else if(autorExiste && livro.isNovoAutor()) {
			errors.rejectValue("autor", "AlreadyExists.livroForm.autor");
		}
		
		if(livro.getPreco() < 0){
			errors.rejectValue("preco", "NotNegative.livroForm.preco");
		}
		
		if(!livro.getCategorias().matches("(,?\\s?[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]{1,},?)*")) {
			errors.rejectValue("categorias", "DoesntMatch.livroForm.categorias");
		}
	}
}
