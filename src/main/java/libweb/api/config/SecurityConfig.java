package libweb.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import libweb.api.model.CustomAuthenticationSuccessHandler;
import libweb.api.repository.UsuarioRepository;
import libweb.api.service.CustomUserDetailsService;
import libweb.api.service.utils.Argon2Utils;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UsuarioRepository.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/css/**")
	  .and()
	  .ignoring().antMatchers("/js/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin().loginPage("/login")
			.permitAll()
			.successHandler(new CustomAuthenticationSuccessHandler())
		.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout");
	}
	
	private PasswordEncoder getPasswordEncoder() {
		
		return new PasswordEncoder() {
			
			private final Argon2 ARGON2 = Argon2Factory.create();
			
			@Override
			public String encode(CharSequence rawPassword) {
				final String hash = ARGON2.hash(Argon2Utils.ITERATIONS, Argon2Utils.MEMORY, Argon2Utils.PARALLELISM, rawPassword.toString());
				return hash;
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return ARGON2.verify(encodedPassword, rawPassword.toString());
			}
		};
	}
}
