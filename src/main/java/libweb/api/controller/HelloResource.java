package libweb.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import libweb.api.model.CustomUserDetails;

@RequestMapping()
@Controller
public class HelloResource {
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/admin") 
	public String adminPage() {
		return  "admin-home.html";
	}
	
	@PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
	@GetMapping("/home") 
	public String comumPage() {
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(auth.getRole().getId() == 1) {
			return "admin-home.html";
		} else if(auth.getRole().getId() == 2) {
			return  "Pagina comum";
		} else {
			return "Pagina comum";
		}
	}
}
