package weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import weixin.pojo.Token;
import weixin.util.MyX509TrustManager;

public class CommonUtil {
	//凭证获取(get)
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
			+ "appid=wx94c8fa7d9c7b068d&secret=5f8f9ceb744dd096afcf85435af52a7d";
	
	/*
	 * 发送HTTPS请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式
	 * @param outputStr 提交的数据
	 * @return JSONObject
	 * */
	
	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject = null;
		try{
			//创建SSLContext对象，并使用信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//从SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//建立连接
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//设置请求方式
			conn.setRequestMethod(requestMethod);
			
			//当outputStr不为null时，向输出流写数据
			if(outputStr != null){
				OutputStream outputStream = conn.getOutputStream();
				//注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			//从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			//取得输入流
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str = bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			
			//释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		}catch(ConnectException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	/*
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return token
	 * */
	public static Token getToken(String appid,String appsecret){
		Token token = null;
		String requestUrl = token_url;
		
		//发起get请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl,"GET",null);
		
		if(jsonObject != null){
			try{
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}catch(JSONException e){
				token = null;
				//获取token失败
				e.printStackTrace();
			}
		}
		return token;
	}
}
