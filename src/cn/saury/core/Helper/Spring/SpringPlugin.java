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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.saury.core.Helper.PathKit;
import cn.saury.core.Plugin.IPlugin;

/**
 * SpringPlugin.
 */
public class SpringPlugin implements IPlugin {
	
	private String[] configurations;
	private ApplicationContext ctx;
	
	/**
	 * Use configuration under the path of WebRoot/WEB-INF.
	 */
	public SpringPlugin() {
	}
	
	public SpringPlugin(String... configurations) {
		this.configurations = configurations;
	}
	
	public SpringPlugin(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public boolean start() {
		if (ctx != null)
			IocInterceptor.ctx = ctx;
		else if (configurations != null)
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(configurations);
		else
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(PathKit.getWebRootPath() + "/WEB-INF/applicationContext.xml");
		return true;
	}
	
	public boolean stop() {
		return true;
	}
}
