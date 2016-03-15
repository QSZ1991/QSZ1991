package com.es.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @author youwc
 *
 */
public class HttpUtil {
	public static String get(String url) {
		HttpGet request = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			}
		} catch (Exception e) {
		}
		
		return null;
	}
	
	// 处理post方法，依赖HttpKeyValue类
	public static String post(String url, List<HttpKeyValue> params) {
		HttpPost request = new HttpPost(url);
		List reqParams = new ArrayList();
		if (params != null) {
			for (HttpKeyValue p: params) {
				reqParams.add(new BasicNameValuePair(p.getKey(), p.getValue()));
			}
		}
		
		try {
			HttpEntity entity = new UrlEncodedFormEntity(reqParams, "utf-8");
			request.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			String str = e.getMessage();
			if (str != null)
				Log.e("HttpUtilError", str);
			String erroInt="error";
			return erroInt;
		}
		
		return null;
	}
}
