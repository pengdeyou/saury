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

package cn.saury.core.Helper.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.saury.core.Handler.Handler;
import cn.saury.core.Helper.StringKit;

/**
 * Provide a context path to view if you need.
 * <br>
 * Example:<br>
 * In SauryFilter: handlers.add(new ContextPathHandler("CONTEXT_PATH"));<br>
 * in freemarker: <img src="${BASE_PATH}/images/logo.png" />
 */
public class ContextPathHandler extends Handler {
	
	private String contextPathName;
	
	public ContextPathHandler() {
		contextPathName = "BASE_PATH";
	}
	
	public ContextPathHandler(String contextPathName) {
		if (StringKit.isBlank(contextPathName))
			throw new IllegalArgumentException("contextPathName can not be blank.");
		this.contextPathName = contextPathName;
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		request.setAttribute(contextPathName, request.getContextPath());
		nextHandler.handle(target, request, response, isHandled);
	}
}
