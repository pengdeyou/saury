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

/**
 * TextRender.
 */
public class TextRender extends Render {
	
	private static final long serialVersionUID = -5264892635310241831L;
	private static final String defaultContentType = "text/plain;charset=" + getEncoding();
	private String text;
	
	public TextRender(String text) {
		this.text = text;
	}
	
	private String contentType;
	public TextRender(String text, String contentType) {
		this.text = text;
		this.contentType = contentType;
	}
	
	public void render() {
		PrintWriter writer = null;
		try {
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
	        if (contentType == null) {
	        	response.setContentType(defaultContentType);
	        }
	        else {
	        	response.setContentType(contentType);
				response.setCharacterEncoding(getEncoding());
	        }
	        
	        writer = response.getWriter();
	        writer.write(text);
	        writer.flush();
		} catch (IOException e) {
			throw new RenderException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}




