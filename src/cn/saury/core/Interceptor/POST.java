package cn.saury.core.Interceptor;

import cn.saury.core.ActionInvocation;
import cn.saury.core.Controller;
import cn.saury.core.AOP.Interceptor;

/**
 * Accept POST method only.
 */
public class POST implements Interceptor {
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		if ("POST".equalsIgnoreCase(controller.getRequest().getMethod().toUpperCase()))
			ai.invoke();
		else
			controller.renderError404();
	}
}
