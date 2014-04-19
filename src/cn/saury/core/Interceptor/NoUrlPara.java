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

package cn.saury.core.Interceptor;

import cn.saury.core.ActionInvocation;
import cn.saury.core.Controller;
import cn.saury.core.AOP.Interceptor;

/**
 * Force action no urlPara, otherwise render error 404 to client.
 */
public class NoUrlPara implements Interceptor {
	public void intercept(ActionInvocation invocation) {
		Controller controller = invocation.getController();
		if (controller.getPara() == null)
			invocation.invoke();
		else
			controller.renderError404();
	}
}