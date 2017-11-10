package com.itstyle.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * 服务端 接收文件
 * 创建者	max
 * 创建时间	2016年4月14日
 */
public class HttpPostServer extends HttpServlet {
	private static final long serialVersionUID = -1002826847460469784L;

	@Override
	public void init() throws ServletException {

	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		
		response.setContentType("text/html;charset=UTF-8");
        String basePath = "d://test1";
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		File directory = null;
		List<FileItem> items = new ArrayList<FileItem>();
		String message = "";
		try {
			items = upload.parseRequest(request);
			// 得到所有的文件
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
                if(!fItem.isFormField()){
                	String name = fItem.getName();
                	if (name != null && !("".equals(name))) {
                		name = name.substring(name.lastIndexOf(File.separator) + 1);
                		directory = new File(basePath);
                		directory.mkdirs();
                		String filePath = (basePath)  + File.separator + name;
                		InputStream is = fItem.getInputStream();
                		FileOutputStream fos = new FileOutputStream(filePath);
                		byte[] buffer = new byte[1024];
                		while (is.read(buffer) > 0) {
                			fos.write(buffer, 0, buffer.length);
                		}
                		fos.flush();
                		fos.close();
                	}
                }
			}
			message = "{success:true, msg:'接收成功'}";
		} catch (Exception e) {
			message = "{success:false, msg:'读取http请求属性值出错!'}";
			e.printStackTrace();
		}finally{
			out = response.getWriter();
			out.print(message);
			out.close();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
