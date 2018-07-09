package libweb.api.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import libweb.api.model.usuario.Usuario;
import libweb.api.model.usuario.UsuarioForm;
import libweb.api.repository.UsuarioRepository;

@Component
public class UsuarioFormValidator implements Validator {

	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UsuarioForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UsuarioForm usuario = (UsuarioForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "NotEmpty.usuarioForm.nome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sobrenome", "NotEmpty.usuarioForm.sobrenome");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDeUsuario", "NotEmpty.usuarioForm.nomeDeUsuario");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senha", "NotEmpty.usuarioForm.senha");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmaSenha", "NotEmpty.usuarioForm.confirmaSenha");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.usuarioForm.email");
		
		if(usuarioRepository.findByNomeDeUsuario(usuario.getNomeDeUsuario())
				.isPresent()) {
			errors.rejectValue("nomeDeUsuario", "AlreadyExists.usuarioForm.nomeDeUsuario");
		}
		
		if(!usuario.getSenha().equals(usuario.getConfirmaSenha())) {
			errors.rejectValue("confirmaSenha", "Diff.usuarioForm.confirmaSenha");
		}
		
		if(!emailValidator.valid(usuario.getEmail())){
			errors.rejectValue("email", "Pattern.usuarioForm.email");
		} else if(usuarioRepository.findByEmail(usuario.getEmail())
				.isPresent()) {
			errors.rejectValue("email", "AlreadyExists.usuarioForm.email");
		}
	}
}
