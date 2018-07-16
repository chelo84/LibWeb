package libweb.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import libweb.api.model.CustomUserDetails;
import libweb.api.model.usuario.Carteira;
import libweb.api.model.usuario.Role;
import libweb.api.model.usuario.Usuario;
import libweb.api.model.usuario.UsuarioForm;
import libweb.api.model.validator.UsuarioFormValidator;
import libweb.api.repository.CarteiraRepository;
import libweb.api.repository.UsuarioRepository;
import libweb.api.service.utils.Argon2Utils;
import libweb.api.service.utils.ServiceUtils;

@Controller
public class UsuarioController {

	@Autowired
	UsuarioFormValidator usuarioFormValidator;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(usuarioFormValidator);
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.GET)
	public String registrarForm(Model model) {
		
		UsuarioForm usuarioForm = new UsuarioForm();
		model.addAttribute("usuarioForm", usuarioForm);
		return "registrar.html";
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.POST)
    public String registrarUsuario(@ModelAttribute("usuarioForm") @Validated UsuarioForm usuarioForm,
    		BindingResult result, Model model,
    		final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "registrar.html";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Usuario criado com sucesso!");
		}
		
		Argon2 argon2 = Argon2Factory.create();
		
		char[] senha = usuarioForm.getSenha().toCharArray();
		Usuario usuarioCriado = null;
		
		try {
			String hash = argon2.hash(Argon2Utils.ITERATIONS, Argon2Utils.MEMORY, Argon2Utils.PARALLELISM, senha);
			
			usuarioForm.setSenha(hash);
			
			Usuario usuario = usuarioForm.toUsuario();
			usuario.setSenha(hash);
			
			Role role = new Role();
			role.setId(new Long(2));
			role.setRole("COMUM");
			usuario.setRole(role);
			
			usuarioCriado = usuarioRepository.save(usuario);
			
		} finally {
			argon2.wipeArray(senha);
		}
		
		return "registrar-sucesso.html";
	}
	
	@RequestMapping(value="/api/add-saldo", method=RequestMethod.POST) 
	public String addSaldo(@RequestParam("quantidade") double quantidade, Model model,
						   HttpServletRequest request) {
		
		CarteiraRepository carteiraRepository = ServiceUtils.getCarteiraRepository();
		
		CustomUserDetails authUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Carteira carteira = carteiraRepository.findById(authUser.getId()).get();
		
		carteira.setSaldo(carteira.getSaldo() + quantidade);
		authUser.setCarteira(carteiraRepository.save(carteira));
		
		String currentPage = request.getHeader("Referer");
		return "redirect:"+ currentPage;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/usuarios")
	public String usuarios(Model model) {
		return "usuarios.html";
	}
	
	@ModelAttribute("usuarios")
	@ResponseBody
	public List<Usuario> getAllUsuarios() {
	    List<Usuario> listUsuario = usuarioRepository.findAll();
	    
	    return listUsuario;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/edit-usuario/{id}", method=RequestMethod.GET)
	public String editUsuario(@PathVariable Long id, Model model) {
		
		Usuario usuario = usuarioRepository.findById(id).get();
		
		UsuarioForm usuarioForm = new UsuarioForm(usuario);
		model.addAttribute("usuario", usuarioForm);
		model.addAttribute("usuarioForm", usuarioForm);
		
		return "edit-usuario.html";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/edit-usuario/{id}", method=RequestMethod.POST)
	public String editUsuario(@PathVariable Long id,
			@ModelAttribute("usuarioForm") UsuarioForm usuarioForm,
    		BindingResult result, Model model,
    		final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "redirect:"+ id;
		} else {
			Usuario usuario = usuarioRepository.findById(id).get();
			
			usuarioForm.update(usuario, redirectAttributes, usuarioRepository);
		}
		
		return "redirect:"+ id;
	}
}
