package com.impetus.bookstore.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impetus.bookstore.exception.DAOLayerException;
import com.impetus.bookstore.persistance.UserDetailsDAO;
import com.impetus.bookstore.persistance.entities.UserEntity;

@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

	private static final Logger LOGGER = Logger
			.getLogger(MyUserDetailService.class);
	@Autowired
	UserDetailsDAO userDetailsDAO;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String email)
			throws UsernameNotFoundException {
		UserEntity user = null;
		List<GrantedAuthority> authorities = null;
		try {
			user = userDetailsDAO.getUserByEmail(email);
			Set<String> userRoles = new HashSet<String>();
			switch (user.getRoleId()) {
			case 1:
				userRoles.add("ROLE_USER");
				break;
			case 2:
				userRoles.add("ROLE_ADMIN");
				break;
			case 3:
				userRoles.add("ROLE_USER");
				userRoles.add("ROLE_ADMINS");
				break;
			default:
				break;
			}
			authorities = buildUserAuthority(userRoles);
		} catch (DAOLayerException e) {
			String message = "Failed to load user by email.";
			LOGGER.error(message);
		}
		return buildUserForAuthentication(user, authorities);

	}

	private User buildUserForAuthentication(UserEntity user,
			List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<String> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (String userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}

}
