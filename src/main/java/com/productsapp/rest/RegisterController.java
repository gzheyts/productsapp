package com.productsapp.rest;

import com.productsapp.jwt.JwtPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.productsapp.security.SecurityConstants.REGISTER_URL;
import static java.util.Collections.emptyList;
import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@RestController
public final class RegisterController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserDetailsManager userDetailsManager;

	@Autowired
	public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder
			, UserDetailsManager userDetailsManager) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsManager = userDetailsManager;
	}

	@PostMapping(REGISTER_URL)
	public void register(@RequestBody @Valid JwtPayload payload) {
		UserDetails user = withUsername(payload.getUsername())
				.password(bCryptPasswordEncoder.encode(payload.getPassword()))
				.authorities(emptyList())
				.build();
		if (userDetailsManager.userExists(user.getUsername())) {
			userDetailsManager.updateUser(user);
			return;
		}
		userDetailsManager.createUser(user);
	}
}
