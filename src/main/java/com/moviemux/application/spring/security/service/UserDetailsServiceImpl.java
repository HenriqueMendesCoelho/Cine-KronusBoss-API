package com.moviemux.application.spring.security.service;

import com.moviemux.application.spring.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviemux.user.adapter.repository.UserRepository;
import com.moviemux.user.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new UserSS(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
				user.getStatistics().getConsecutiveFailedLoginAttempts(), user.getRoles());
	}

}
