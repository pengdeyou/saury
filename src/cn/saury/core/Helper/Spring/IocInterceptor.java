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

package cn.saury.core.Helper.Spring;

import java.lang.reflect.Field;
import org.springframework.context.ApplicationContext;

import cn.saury.core.ActionInvocation;
import cn.saury.core.Controller;
import cn.saury.core.AOP.Interceptor;

/**
 * IocInterceptor.
 */
public class IocInterceptor implements Interceptor {
	
	static ApplicationContext ctx;
	
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		Field[] fields = controller.getClass().getDeclaredFields();
		for (Field field : fields)
			injectField(controller, field);
		
		ai.invoke();
	}
	
	private void injectField(Controller controller, Field field) {
		Object bean = null;
		if (field.isAnnotationPresent(Inject.BY_NAME.class))
			bean = ctx.getBean(field.getName());
		else if (field.isAnnotationPresent(Inject.IGNORE.class))
			return ;
		else
			bean = ctx.getBean(field.getType());
		
		try {
			field.setAccessible(true);
			field.set(controller, bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
