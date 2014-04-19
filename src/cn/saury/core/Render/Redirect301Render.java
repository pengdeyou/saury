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

package cn.saury.core.Render;

import javax.servlet.http.HttpServletResponse;

/**
 * Redirect301Render.
 */
public class Redirect301Render extends Render {
	
	private static final long serialVersionUID = -6822589387282014944L;
	private String url;
	private boolean withOutQueryString;
	
	public Redirect301Render(String url) {
		this.url = url;
		this.withOutQueryString = false;
	}
	
	public Redirect301Render(String url, boolean withOutQueryString) {
		this.url = url;
		this.withOutQueryString = withOutQueryString;
	}
	
	public void render() {
		if (withOutQueryString == false) {
			String queryString = request.getQueryString();
			queryString = (queryString == null ? "" : "?" + queryString);
			url = url + queryString;
		}
		
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		// response.sendRedirect(url);	// always 302
		response.setHeader("Location", url);
		response.setHeader("Connection", "close");
	}

}
