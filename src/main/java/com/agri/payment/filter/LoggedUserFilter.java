package com.agri.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import com.agri.payment.audit.LoggedUser;

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
