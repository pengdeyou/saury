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

import java.util.List;

import cn.saury.core.HandlerFactory;
import cn.saury.core.Logger.Logger;
import cn.saury.core.Plugin.IPlugin;

class Config {
	
	private static final Constant constants = new Constant();
	private static final RouterFactory routes = new RouterFactory(){public void config() {}};
	private static final PluginFactory plugins = new PluginFactory();
	private static final Interceptors interceptors = new Interceptors();
	private static final HandlerFactory handlers = new HandlerFactory();
	private static Logger log;
	
	// prevent new Config();
	private Config() {
	}
	
	/*
	 * Config order: constant, route, plugin, interceptor, handler
	 */
	static void configSaury(Init jfinalConfig) {
		jfinalConfig.configConstant(constants);				initLoggerFactory();
		jfinalConfig.configRoute(routes);
		jfinalConfig.configPlugin(plugins);					startPlugins();	// very important!!!
		jfinalConfig.configInterceptor(interceptors);
		jfinalConfig.configHandler(handlers);
	}
	
	public static final Constant getConstants() {
		return constants;
	}
	
	public static final RouterFactory getRoutes() {
		return routes;
	}
	
	public static final PluginFactory getPlugins() {
		return plugins;
	}
	
	public static final Interceptors getInterceptors() {
		return interceptors;
	}
	
	public static HandlerFactory getHandlers() {
		return handlers;
	}
	
	private static void startPlugins() {
		List<IPlugin> pluginList = plugins.getPluginList();
		if (pluginList != null) {
			for (IPlugin plugin : pluginList) {
				try {
					boolean success = plugin.start();
					if (!success) {
						log.error("Plugin start error: " + plugin.getClass().getName());
						throw new RuntimeException("Plugin start error: " + plugin.getClass().getName());
					}
				}
				catch (Exception e) {
					log.error("Plugin start error: " + plugin.getClass().getName(), e);
					throw new RuntimeException("Plugin start error: " + plugin.getClass().getName(), e);
				}
			}
		}
	}
	
	private static void initLoggerFactory() {
		Logger.init();
		log = Logger.getLogger(Config.class);
		FilterFactory.initLogger();
	}
}
