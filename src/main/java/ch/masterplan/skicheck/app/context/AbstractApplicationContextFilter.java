/*
 * MIT License
 *
 * Copyright (c) 2024 Masterplan AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ch.masterplan.skicheck.app.context;

import ch.masterplan.skicheck.app.configuration.HasLogger;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Abstract base class for servlet filters within the application context, providing common functionality.
 */
abstract class AbstractApplicationContextFilter implements Filter, HasLogger {

	/**
	 * Default constructor.
	 */
	public AbstractApplicationContextFilter() {
		super();
	}

	/**
	 * Initialization method for the filter.
	 *
	 * @param filterConfig The FilterConfig object.
	 * @throws ServletException If an exception occurs during initialization.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// no special initialization needed
		log().info("Filter initialized");
	}

	/**
	 * Destruction method for the filter.
	 */
	@Override
	public void destroy() {
		// no special logic needed
		log().info("Filter destroyed");
	}

	/**
	 * Main filter method, handling requests and responses.
	 *
	 * @param request  The ServletRequest object.
	 * @param response The ServletResponse object.
	 * @param chain    The FilterChain object.
	 * @throws IOException      If an I/O error occurs.
	 * @throws ServletException If a servlet-specific error occurs.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log().debug("Filter called");

		// cast to HTTP objects
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// handling before the chain is executed
		log().debug("Executing doBefore method");
		boolean continueRequest = this.doBefore(httpRequest, httpResponse);

		if (!continueRequest) {
			log().debug("Request terminated based on doBefore method");
			return;
		}

		// handle the chain
		chain.doFilter(request, response);

		// handling after the chain is executed
		if (httpRequest.getSession(false) != null) {
			log().debug("Executing doAfter method");
			this.doAfter(httpRequest, httpResponse);
		}
	}

	/**
	 * Method to be implemented by subclasses, executed after the filter chain.
	 *
	 * @param request  The HttpServletRequest object.
	 * @param response The HttpServletResponse object.
	 */
	protected abstract void doAfter(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Method to be implemented by subclasses, executed before the filter chain.
	 *
	 * @param request  The HttpServletRequest object.
	 * @param response The HttpServletResponse object.
	 * @return Whether the request should continue to the filter chain.
	 */
	protected abstract boolean doBefore(HttpServletRequest request, HttpServletResponse response);
}
