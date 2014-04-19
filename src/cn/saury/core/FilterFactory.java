/**
 * Copyright (c) 2010-2014, SauryFramework.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.saury.core;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.saury.core.Handler.Handler;
import cn.saury.core.Logger.Logger;

/**
 * Saury framework filter
 */
public final class FilterFactory implements Filter {
	
	private Handler handler;
	private String encoding;
	private Init jfinalConfig;
	private Constant constants;
	private static final Core jfinal = Core.me();
	private static Logger log;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		createSauryConfig(filterConfig.getInitParameter("configClass"));
		
		if (jfinal.init(jfinalConfig, filterConfig.getServletContext()) == false)
			throw new RuntimeException("Saury init error!");
		
		handler = jfinal.getHandler();
		constants = Config.getConstants();
		encoding = constants.getEncoding();
		jfinalConfig.afterSauryStart();
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		request.setCharacterEncoding(encoding);
		
		String target = request.getServletPath();
		boolean[] isHandled = {false};
		try {
			handler.handle(target, request, response, isHandled);
		}
		catch (Exception e) {
			if (log.isErrorEnabled()) {
				String qs = request.getQueryString();
				log.error(qs == null ? target : target + "?" + qs, e);
			}
		}
		
		if (isHandled[0] == false)
			chain.doFilter(request, response);
	}
	
	public void destroy() {
		jfinalConfig.beforeSauryStop();
		jfinal.stopPlugins();
	}
	
	private void createSauryConfig(String configClass) {
		if (configClass == null)
			throw new RuntimeException("Please set configClass parameter of SauryFilter in web.xml");
		
		try {
			Object temp = Class.forName(configClass).newInstance();
			if (temp instanceof Init)
				jfinalConfig = (Init)temp;
			else
				throw new RuntimeException("Can not create instance of class: " + configClass + ". Please check the config in web.xml");
		} catch (InstantiationException e) {
			throw new RuntimeException("Can not create instance of class: " + configClass, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Can not create instance of class: " + configClass, e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class not found: " + configClass + ". Please config it in web.xml", e);
		}
	}
	
	static void initLogger() {
		log = Logger.getLogger(FilterFactory.class);
	}
}
