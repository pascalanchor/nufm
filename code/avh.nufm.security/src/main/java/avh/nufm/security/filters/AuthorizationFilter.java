package avh.nufm.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import avh.nufm.security.common.SecurityCte;
import avh.nufm.security.jwt.JWTProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired private JWTProvider tokenProvider;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// we do not need to look for any token when the user is attenpting to log in
		// this is required because this function is invoked before landing on our API.
		log.info("new request -> " + request.getServletPath());
		if (request.getServletPath().startsWith(SecurityCte.PublicServletPath)) {
			log.info("This is a public request -> authorized");
			filterChain.doFilter(request, response);
			return;
		}
		 try {
	            String jwt = getJWTFromRequest(request);
	            if (StringUtils.hasText(jwt)) {
	            	UsernamePasswordAuthenticationToken authentication = tokenProvider.validateToken(jwt);
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                filterChain.doFilter(request, response);
	            } else {
	            	response.sendError(HttpStatus.FORBIDDEN.value(), "no token in authorization headers");
	            }
	        }  catch (TokenExpiredException tee) {
        	response.sendError(HttpStatus.FORBIDDEN.value(), "the token has expired");
        } catch (AlgorithmMismatchException | SignatureVerificationException | InvalidClaimException e) {
        	response.sendError(HttpStatus.FORBIDDEN.value(), "wrong token");
        }  catch (JWTVerificationException e) {
        	response.sendError(HttpStatus.FORBIDDEN.value(), "wrong token");
        } catch (Exception unk) {
        	response.sendError(HttpStatus.FORBIDDEN.value(), "unknown error");
        }
	}
	
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
