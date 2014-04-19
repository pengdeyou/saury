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

import java.util.ArrayList;
import java.util.List;

import cn.saury.core.Plugin.IPlugin;

/**
 * Plugins.
 */
final public class PluginFactory {
	
	private final List<IPlugin> pluginList = new ArrayList<IPlugin>();
	
	public PluginFactory add(IPlugin plugin) {
		if (plugin != null)
			this.pluginList.add(plugin);
		return this;
	}
	
	public List<IPlugin> getPluginList() {
		return pluginList;
	}
}
