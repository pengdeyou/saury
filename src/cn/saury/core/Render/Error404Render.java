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

package cn.saury.core.Render;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import cn.saury.core.Const;

/**
 * Error404Render.
 */
public class Error404Render extends Render {
	
	private static final long serialVersionUID = 1764764489766904795L;
	private static final String contentType = "text/html;charset=" + getEncoding();
	private static final String defaultHtml = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr><center><a href='http://www.saury.cn'>Saury Framework/" + Const.SYSTEM_VERSION + "</a></center></body></html>";
	
	public Error404Render(String view) {
		this.view = view;
	}
	
	public Error404Render() {
		
	}
	
	public void render() {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		// render with view
		if (view != null) {
			RenderFactory.me().getRender(view).setContext(request, response).render();
			return;
		}
		
		// render with defaultHtml
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(defaultHtml);
	        writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}
	public String fetch() {
		return "";
		
	}
}




