package cn.saury.core.Helper.Shiro;

import cn.saury.core.ActionInvocation;
import cn.saury.core.AOP.Interceptor;

public class ShiroInterceptor implements Interceptor {
	
	public void intercept(ActionInvocation ai) {
		throw new RuntimeException("Not finish!!!");
	}
}
