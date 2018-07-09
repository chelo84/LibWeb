package libweb.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping()
@RestController
public class HelloResource {
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/admin") 
	public String adminPage() {
		return  "Pagina admin";
	}
	
	@PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
	@GetMapping("/home") 
	public String comumPage() {
		return  "Pagina comum";
	}
}
