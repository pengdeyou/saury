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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Routes.
 */
public abstract class RouterFactory {
	
	private final Map<String, Class<? extends Controller>> map = new HashMap<String, Class<? extends Controller>>();
	private final Map<String, String> viewPathMap = new HashMap<String, String>();
	
	/**
	 * you must implement config method and use add method to config route
	 */
	public abstract void config();
	
	public RouterFactory add(RouterFactory routes) {
		if (routes != null) {
			routes.config();	// very important!!!
			map.putAll(routes.map);
			viewPathMap.putAll(routes.viewPathMap);
		}
		return this;
	}
	
	/**
	 * Add route
	 * @param controllerKey A key can find controller
	 * @param controllerClass Controller Class
	 * @param viewPath View path for this Controller
	 */
	public RouterFactory add(String controllerKey, Class<? extends Controller> controllerClass, String viewPath) {
		if (controllerKey == null)
			throw new IllegalArgumentException("The controllerKey can not be null");
		// if (controllerKey.indexOf(".") != -1)
			// throw new IllegalArgumentException("The controllerKey can not contain dot character: \".\"");
		controllerKey = controllerKey.trim();
		if ("".equals(controllerKey))
			throw new IllegalArgumentException("The controllerKey can not be blank");
		if (controllerClass == null)
			throw new IllegalArgumentException("The controllerClass can not be null");
		if (map.containsKey(controllerKey))
			throw new IllegalArgumentException("The controllerKey already exists");
		
		if (!controllerKey.startsWith("/"))
			controllerKey = "/" + controllerKey;
		map.put(controllerKey, controllerClass);
		
		if (viewPath == null || "".equals(viewPath.trim()))	// view path is controllerKey by default
			viewPath = controllerKey;
		
		viewPath = viewPath.trim();
		if (!viewPath.startsWith("/"))					// "/" added to prefix
			viewPath = "/" + viewPath;
		
		if (!viewPath.endsWith("/"))					// "/" added to postfix
			viewPath = viewPath + "/";
		
		if (baseViewPath != null)						// support baseViewPath
			viewPath = baseViewPath + viewPath;
		
		viewPathMap.put(controllerKey, viewPath);
		return this;
	}
	
	/**
	 * Add url mapping to controller. The view path is controllerKey
	 * @param controllerkey A key can find controller
	 * @param controllerClass Controller Class
	 */
	public RouterFactory add(String controllerkey, Class<? extends Controller> controllerClass) {
		return add(controllerkey, controllerClass, controllerkey);
	}
	
	public Set<Entry<String, Class<? extends Controller>>> getEntrySet() {
		return map.entrySet();
	}
	
	public String getViewPath(String key) {
		return viewPathMap.get(key);
	}
	
	private static String baseViewPath;
	
	/**
	 * Set the base path for all views
	 */
	static void setBaseViewPath(String baseViewPath) {
		if (baseViewPath == null)
			throw new IllegalArgumentException("The baseViewPath can not be null");
		baseViewPath = baseViewPath.trim();
		if ("".equals(baseViewPath))
			throw new IllegalArgumentException("The baseViewPath can not be blank");
		
		if (! baseViewPath.startsWith("/"))			// add prefix "/"
			baseViewPath = "/" + baseViewPath;
		
		if (baseViewPath.endsWith("/"))				// remove "/" in the end of baseViewPath
			baseViewPath = baseViewPath.substring(0, baseViewPath.length() - 1);
		
		RouterFactory.baseViewPath = baseViewPath;
	}
}






