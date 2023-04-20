package com.bsol.iri.fileSharing.security.service;

/**
 * 
 * @author rupesh
 *	
 */
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bsol.iri.fileSharing.entity.MaUser;
import com.bsol.iri.fileSharing.models.Email;
import com.bsol.iri.fileSharing.repos.MaRoleRepo;
import com.bsol.iri.fileSharing.repos.UserRepo;
import com.bsol.iri.fileSharing.util.AppConstant;

@Service(value = "userDtlsService")
public class UserServiceImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MaRoleRepo maRoleRepo;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		log.debug(" User trying to login with Email {} ", userEmail);
		MaUser user = userRepo.findByEmailAndStatus(userEmail.toLowerCase(), AppConstant.ACTIVE);
		if (user == null) {
			log.error("No user found with email {}", userEmail);
			throw new UsernameNotFoundException("Invalid username or password.");	
		}

		String authority = maRoleRepo.findById(user.getRole()).get().getRoleDesc();
		log.debug(" This user is having authority  {} ", authority);
		return new org.springframework.security.core.userdetails.User(String.valueOf(user.getEmail()), user.getPwd(),
				getAuthority(authority));
	}

	private List<SimpleGrantedAuthority> getAuthority(String authority) {
		return Arrays.asList(new SimpleGrantedAuthority(authority));
	}

}
