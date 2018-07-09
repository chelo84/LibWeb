package libweb.api.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
 
        HttpSession session = httpServletRequest.getSession();
        CustomUserDetails authUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("username", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());
        
        ArrayList<GrantedAuthority> authList = (ArrayList<GrantedAuthority>) authUser.getAuthorities();
        
        GrantedAuthority authority = authList.get(0);
        
        switch(authority.getAuthority()) {
        	case "ROLE_ADMIN": {
        		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        		httpServletResponse.sendRedirect("admin");
        		break;
        	}
        	case "ROLE_COMUM": {
            	httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.sendRedirect("home");
                break;
        	}
        	default: {
            	httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                httpServletResponse.sendRedirect("home");
                break;
        	}
        }
        
    }
}
