package libweb.api.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import libweb.api.model.usuario.Role;
import libweb.api.model.usuario.Usuario;
import libweb.api.model.usuario.UsuarioForm;
import libweb.api.model.validator.UsuarioFormValidator;
import libweb.api.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	UsuarioFormValidator usuarioFormValidator;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(usuarioFormValidator);
	}
	
	@RequestMapping("/registrar")
	public String registrarForm(Model model) {
		UsuarioForm usuarioForm = new UsuarioForm();
		model.addAttribute("usuarioForm", usuarioForm);
		return "registrar.html";
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.POST)
    public String registrarUsuario(@ModelAttribute("usuarioForm") @Validated UsuarioForm usuarioForm,
    		BindingResult result, Model model,
    		final RedirectAttributes redirectAttributes) {
		
		logger.debug("registrarUsuario() : {}", usuarioForm);
		
		if (result.hasErrors()) {
			return "registrar.html";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Usuario criado com sucesso!");
		}
		
		Argon2 argon2 = Argon2Factory.create();
		
		final int ITERATIONS = 2;
		final int MEMORY= 65536;
		final int PARALLELISM = 1;
		
		char[] senha = usuarioForm.getSenha().toCharArray();
		Usuario usuarioCriado = null;
		
		try {
			String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, senha);
			
			usuarioForm.setSenha(hash);
			
			Usuario usuario = usuarioForm.toUsuario();
			usuario.setSenha(hash);
			
			Role role = new Role();
			role.setId(new Long(1));
			role.setRole("ADMIN");
			usuario.setRole(role);
			
			usuarioCriado = usuarioRepository.save(usuario);
			
		} finally {
			argon2.wipeArray(senha);
		}
		
		return "registrar-sucesso.html";
	}
	
	@RequestMapping(value="/api/add-saldo", method=RequestMethod.POST) 
	public String addSaldo(@RequestParam("quantidade") double quantidade, Model model) {
		System.err.println(quantidade);
		
    	return "redirect:/home";
	}
	
	@RequestMapping("/edit-usuario")
	public String editUsuario() {
		return "edit-usuario.html";
	}
}
