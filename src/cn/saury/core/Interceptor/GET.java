package cn.saury.core.Interceptor;

import cn.saury.core.ActionInvocation;
import cn.saury.core.Controller;
import cn.saury.core.AOP.Interceptor;

/**
 * Accept GET method only.
 */
public class GET implements Interceptor {
	public void intercept(ActionInvocation ai) {
		Controller controller = ai.getController();
		if ("GET".equalsIgnoreCase(controller.getRequest().getMethod()))
			ai.invoke();
		else
			controller.renderError404();
	}
}
