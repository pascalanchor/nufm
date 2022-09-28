package avh.nufm.security.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.repository.NufmRepos;



@Service
@Transactional
public class UserService implements UserDetailsService{

	@Autowired private NufmRepos rep;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// - find the user
		NufmUser usr = rep.getNfuserrepo().findById(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
		
		// - find the user's roles
		List<UserRole> ms = rep.getUserrolerepo().findByNufmUser(usr);
		
		// - convert role into SimpleGrantedAuthority
		List<SimpleGrantedAuthority> aus = new ArrayList<>();
		for (UserRole m : ms) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(m.getNufmRole().getEid());
			System.out.println(m.getNufmRole().getEid());
			aus.add(sga);
		}
		
		// - construct the spring User
		return User.builder()
				.accountExpired(false)
				.accountLocked(false)
				.credentialsExpired(false)
				.disabled(false)
				.password(usr.getPassword())
				.username(username)
//				.authorities(ms.stream().map(mb -> new SimpleGrantedAuthority(mb.getRole().getRolename())).collect(Collectors.toList()))
				.authorities(aus)
				.build();
	}

}
