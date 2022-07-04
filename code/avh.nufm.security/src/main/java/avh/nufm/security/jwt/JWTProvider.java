package avh.nufm.security.jwt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTProvider {
    private final String jwtSecret = "NUFM KANAAN SHAMIEH KHOUJA";
    private int jwtExpirationInMs = 30 * 60 * 1000;
    private Algorithm alg;
    
    public JWTProvider() {
    	log.info("secret = " + jwtSecret);
    	alg = Algorithm.HMAC256(jwtSecret);
    }
    
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String token = JWT.create()
        	.withSubject(userPrincipal.getUsername())
        	.withExpiresAt(expiryDate)
        	.withIssuedAt(now)
        	.withIssuer("AVH_NUFM")
        	.withClaim("roles",
        			userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        	.sign(alg);
        return token;
    }

    public UsernamePasswordAuthenticationToken validateToken(String authToken) {
    	JWTVerifier verifier = JWT.require(alg).build();
    	DecodedJWT infos = verifier.verify(authToken);
    	String username = infos.getSubject();
    	String[] roles = infos.getClaim("roles").asArray(String.class);
    	Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    	Arrays.stream(roles).forEach(role -> {
    		authorities.add(new SimpleGrantedAuthority(role));
    	});
    	return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
    
}
