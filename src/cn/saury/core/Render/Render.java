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

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.saury.core.Const;

/**
 * Render.
 */
public abstract class Render implements Serializable {
	
	private static final long serialVersionUID = -6161983268638909080L;
	protected String view;
	protected transient HttpServletRequest request;
	protected transient HttpServletResponse response;
	
	private transient static String encoding = Const.DEFAULT_ENCODING;
	private transient static boolean devMode;
	
	static final void init(String encoding, boolean devMode) {
		Render.encoding = encoding;
		Render.devMode = devMode;
	}
	
	public static final String getEncoding() {
		return encoding;
	}
	
	public static final boolean getDevMode() {
		return devMode;
	}
	
	public final Render setContext(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		return this;
	}
	
	public final Render setContext(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		this.request = request;
		this.response = response;
		if (view != null && !view.startsWith("/"))
			view = viewPath + view;
		return this;
	}
	
	/**
	 * Render to client
	 */
	public abstract void render();
}
