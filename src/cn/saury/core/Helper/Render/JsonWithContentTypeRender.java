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

package cn.saury.core.Helper.Render;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import cn.saury.core.Helper.JsonKit;
import cn.saury.core.Render.Render;
import cn.saury.core.Render.RenderException;

/**
 * JsonRenderWithContentType
 */
public class JsonWithContentTypeRender extends Render {
	
	private static final long serialVersionUID = 3672646716279300085L;
	private String key;
	private Object value;
	private String[] attrs;
	private String contentType;
	
	public JsonWithContentTypeRender(String contentType) {
		this.contentType = contentType;
	}
	
	public JsonWithContentTypeRender(String key, Object value, String contentType) {
		this.key = key;
		this.value = value;
		this.contentType = contentType;
	}
	
	public JsonWithContentTypeRender(String[] attrs, String contentType) {
		this.attrs = attrs;
		this.contentType = contentType;
	}
	
	public void render() {
		String jsonText = buildJsonText();
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			response.setContentType(contentType + ";charset=" + getEncoding());
			writer = response.getWriter();
	        writer.write(jsonText);
	        writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}
	
	private static final int depth = 8;
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private String buildJsonText() {
		Map map = new HashMap();
		if (key != null) {
			map.put(key, value);
		}
		else if (attrs != null) {
			for (String key : attrs)
				map.put(key, request.getAttribute(key));
		}
		else {
			Enumeration<String> attrs = request.getAttributeNames();
			while (attrs.hasMoreElements()) {
				String key = attrs.nextElement();
				Object value = request.getAttribute(key);
				map.put(key, value);
			}
		}
		
		return JsonKit.mapToJson(map, depth);
	}

}











