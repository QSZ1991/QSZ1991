package sdw.com;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpSocket
{
	public static String HttpGet(String strUrl)
	{
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 执行一个HTTP POST请求，
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String doPost(String url, Map<String, String> params)
			throws IllegalStateException, IOException {
		String strResult = "";
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			System.out.print(entry.getValue());
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, HTTP.UTF_8);//
		post.setEntity(entity);
		HttpResponse response = httpClient.execute(post);

		// 若状态码为200 ok
		if (response.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			strResult = EntityUtils.toString(response.getEntity());
		}
		return strResult;
	}

	public static String HttpPost(String strUrl)
	{
		   
		return "";
	}
	public static String getJsonContent(String path){
		try {
			URL url = new URL(path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("POST");//GET
			connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			connection.setDoInput(true);
			int code = connection.getResponseCode();
			if(code == 200){
				return changeInputStream(connection.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将一个输入流转换成指定编码的字符串  
	 * @param inputStream
	 * @return
	 */
	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while((len=inputStream.read(data))!=-1){
				outputStream.write(data,0,len);
			}
			jsonString = new String(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("56"+jsonString);
		return jsonString;
	}

}

