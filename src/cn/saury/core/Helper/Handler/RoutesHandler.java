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

import cn.saury.core.Controller;
import cn.saury.core.Handler.Handler;

/**
 * RoutesHandler. Not finish yet.
 * <p>
 * RoutesHandler convert url to route format that Routes can find Controller and Method 
 */
public class RoutesHandler extends Handler {
	
	public void addRoute(String regex, String controllerKey) {
		throw new RuntimeException("Not finished");
	}
	
	public void addRoute(String regex, String controllerKey, String method) {
		throw new RuntimeException("Not finished");
	}
	
	public void addRoute(String regex, Controller controller, String method) {
		throw new RuntimeException("Not finished");
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		throw new RuntimeException("Not finished");
	}
}
