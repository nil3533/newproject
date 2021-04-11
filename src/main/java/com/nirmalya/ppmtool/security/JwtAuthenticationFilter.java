package com.nirmalya.ppmtool.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nirmalya.ppmtool.domain.User;
import com.nirmalya.ppmtool.service.CustomUserDetailService;
import com.nirmalya.ppmtool.utilities.SecurityConstant;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTProvider tokenProvider;

	@Autowired
	private CustomUserDetailService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		try {
			String jwt = getJwtFromRequest(httpServletRequest);			

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				User userDetails = customUserDetailsService.loadUserById(userId);				

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Collections.emptyList());			

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		} catch (Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader(SecurityConstant.HEADER_STRING);
		
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

}
