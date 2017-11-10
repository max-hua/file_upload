package com.itstyle.web;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * 客服端
 * 创建者	max
 * 创建时间	2016年4月14日
 *
 */
public class HttpPostClient {
	public void SubmitPost(String url, String filename, String filepath) {

		HttpClient httpclient = new DefaultHttpClient();

		try {

			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(filepath + File.separator + filename));

			StringBody comment = new StringBody(filename);

			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("file", bin);// file为请求后台的File upload;属性
			reqEntity.addPart("filename", comment);// filename为请求后台的普通参数;属性
			httppost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httppost);

			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {

				System.out.println("服务器正常响应.....");

				HttpEntity resEntity = response.getEntity();

				System.out.println(EntityUtils.toString(resEntity));// httpclient自带的工具类读取返回数据

				System.out.println(resEntity.getContent());

				EntityUtils.consume(resEntity);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
				
			}
		}
	}
	/**
	 * 自定义文件名称  和路径  win环境下测试
	 * @param args
	 */
	public static void main(String[] args) {
		HttpPostClient httpPostClient = new HttpPostClient();
		httpPostClient.SubmitPost("http://127.0.0.1:8080/acts_upload/receiveData","1.png", "D://test");
	}
}
