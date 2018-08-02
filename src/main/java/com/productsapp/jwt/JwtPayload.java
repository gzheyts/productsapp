package com.productsapp.jwt;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class JwtPayload {
	@NotNull
	private String username;
	@NotNull
	private String password;
}
