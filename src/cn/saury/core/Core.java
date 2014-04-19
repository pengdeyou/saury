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

import java.io.File;
import java.util.List;
import javax.servlet.ServletContext;
import cn.saury.core.Activerecord.ActiveRecordPlugin;
import cn.saury.core.Handler.Handler;
import cn.saury.core.Handler.HandlerFactory;
import cn.saury.core.Helper.PathKit;
import cn.saury.core.Helper.Token.ITokenCache;
import cn.saury.core.Helper.Token.TokenManager;
import cn.saury.core.Helper.Uploader.OreillyCos;
import cn.saury.core.I18n.I18N;
import cn.saury.core.Plugin.IPlugin;
import cn.saury.core.Render.RenderFactory;
import cn.saury.core.Server.ServerFactory;

/**
 * Saury
 */
public final class Core {
	
	private Constant constants;
	private ActionMapping actionMapping;
	private Handler handler;
	private ServletContext servletContext;
	
	Handler getHandler() {
		return handler;
	}
	
	private static final Core me = new Core();
	
	// singleton
	private Core() {
	}
	
	public static Core me() {
		return me;
	}
	
	boolean init(Init jfinalConfig, ServletContext servletContext) {
		this.servletContext = servletContext;
		
		initPathUtil();
		
		Config.configSaury(jfinalConfig);	// start plugin and init logger factory in this method
		constants = Config.getConstants();
		
		initActionMapping();
		initHandler();
		initRender();
		initActiveRecord();
		initOreillyCos();
		initI18n();
		initTokenManager();
		
		return true;
	}
	
	private void initTokenManager() {
		ITokenCache tokenCache = constants.getTokenCache();
		if (tokenCache != null)
			TokenManager.init(tokenCache);
	}
	
	private void initI18n() {
		String i18nResourceBaseName = constants.getI18nResourceBaseName();
		if (i18nResourceBaseName != null) {
			I18N.init(i18nResourceBaseName, constants.getI18nDefaultLocale(), constants.getI18nMaxAgeOfCookie());
		}
	}
	
	private void initHandler() {
		Handler actionHandler = new ActionHandler(actionMapping, constants);
		handler = HandlerFactory.getHandler(Config.getHandlers().getHandlerList(), actionHandler);
	}
	
	private void initOreillyCos() {
		Constant ct = constants;
		if (OreillyCos.isMultipartSupported()) {
			String uploadedFileSaveDirectory = ct.getUploadedFileSaveDirectory();
			if (uploadedFileSaveDirectory == null || "".equals(uploadedFileSaveDirectory.trim())) {
				uploadedFileSaveDirectory = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;
				ct.setUploadedFileSaveDirectory(uploadedFileSaveDirectory);
				
				/*File file = new File(uploadedFileSaveDirectory);
				if (!file.exists())
					file.mkdirs();*/
			}
			OreillyCos.init(uploadedFileSaveDirectory, ct.getMaxPostSize(), ct.getEncoding());
		}
	}
	
	private void initActiveRecord() {
		ActiveRecordPlugin.setDevMode(constants.getDevMode());
	}
	
	private void initPathUtil() {
		String path = servletContext.getRealPath("/");
		PathKit.setWebRootPath(path);
	}
	
	private void initRender() {
		RenderFactory renderFactory = RenderFactory.me();
		renderFactory.init(constants, servletContext);
	}
	
	private void initActionMapping() {
		actionMapping = new ActionMapping(Config.getRoutes(), Config.getInterceptors());
		actionMapping.buildActionMapping();
	}
	
	void stopPlugins() {
		List<IPlugin> plugins = Config.getPlugins().getPluginList();
		if (plugins != null) {
			for (int i=plugins.size()-1; i >= 0; i--) {		// stop plugins
				boolean success = false;
				try {
					success = plugins.get(i).stop();
				} 
				catch (Exception e) {
					success = false;
					e.printStackTrace();
				}
				if (!success) {
					System.err.println("Plugin stop error: " + plugins.get(i).getClass().getName());
				}
			}
		}
	}
	
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	
	public static void start() {
		ServerFactory.getServer().start();
	}
	
	public static void start(String webAppDir, int port, String context, int scanIntervalSeconds) {
		ServerFactory.getServer(webAppDir, port, context, scanIntervalSeconds).start();
	}
	
	/**
	 * Run Saury Server with Debug Configurations or Run Configurations in Eclipse JavaEE
	 * args example: WebRoot 80 / 5
	 */
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			ServerFactory.getServer().start();
		}
		else {
			String webAppDir = args[0];
			int port = Integer.parseInt(args[1]);
			String context = args[2];
			int scanIntervalSeconds = Integer.parseInt(args[3]);
			ServerFactory.getServer(webAppDir, port, context, scanIntervalSeconds).start();
		}
	}
	
	public List<String> getAllActionKeys() {
		return actionMapping.getAllActionKeys();
	}
	
	public Constant getConstants() {
		return Config.getConstants();
	}
	
	public Action getAction(String url, String[] urlPara) {
		return actionMapping.getAction(url, urlPara);
	}
}










