package com.agri.payment.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.agri.payment.audit.LoggedUser;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class LoggedUserFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			// HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			LoggedUser.logIn("System");

			filterChain.doFilter(request, response);
		} finally {
			LoggedUser.logOut();
		}
	}

	@Override
	public void destroy() {
	}
}
