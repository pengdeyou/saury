package cn.saury.core;
import java.util.*;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.saury.core.Render.RenderException;

import freemarker.template.*;

public class View
{
	protected String view;
	protected transient HttpServletRequest request;
	protected transient HttpServletResponse response;
    private Configuration cfg;
    private Map<String, Object> attribs = new HashMap<String, Object>();
    public void init() throws Exception{
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("templates"));
    }
	public void assign(String name, Object value) {
		attribs.put(name, value);
	
	} 
	
	public void display(String view)throws Exception
	{
		
        Enumeration<String> attrs = request.getAttributeNames();
		Map root = new HashMap();
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}
		
		PrintWriter writer = null;
        try {
			Template template = cfg.getTemplate(view);
			writer = response.getWriter();
			template.process(root, writer);		// Merge the data-model and the template
		} catch (Exception e) {
			throw new RenderException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}

	}

}