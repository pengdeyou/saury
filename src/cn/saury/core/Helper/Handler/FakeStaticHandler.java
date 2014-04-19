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
 * FakeStaticHandler.
 */
public class FakeStaticHandler extends Handler {
	
	private String viewPostfix;
	
	public FakeStaticHandler() {
		viewPostfix = ".html";
	}
	
	public FakeStaticHandler(String viewPostfix) {
		if (StringKit.isBlank(viewPostfix))
			throw new IllegalArgumentException("viewPostfix can not be blank.");
		this.viewPostfix = viewPostfix;
	}
	
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		int index = target.lastIndexOf(viewPostfix);
		if (index != -1)
			target = target.substring(0, index);
		nextHandler.handle(target, request, response, isHandled);
	}
}
