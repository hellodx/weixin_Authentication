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
	//ƾ֤��ȡ(get)
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
			+ "appid=wx94c8fa7d9c7b068d&secret=5f8f9ceb744dd096afcf85435af52a7d";
	
	/*
	 * ����HTTPS����
	 * 
	 * @param requestUrl �����ַ
	 * @param requestMethod ����ʽ
	 * @param outputStr �ύ������
	 * @return JSONObject
	 * */
	
	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject = null;
		try{
			//����SSLContext���󣬲�ʹ�����ι�������ʼ��
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			//��SSLContext�����еõ�SSLSocketFactory����
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//��������
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			//��������ʽ
			conn.setRequestMethod(requestMethod);
			
			//��outputStr��Ϊnullʱ���������д����
			if(outputStr != null){
				OutputStream outputStream = conn.getOutputStream();
				//ע������ʽ
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			//����������ȡ��������
			InputStream inputStream = conn.getInputStream();
			//ȡ��������
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while((str = bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			
			//�ͷ���Դ
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
	 * ��ȡ�ӿڷ���ƾ֤
	 * 
	 * @param appid ƾ֤
	 * @param appsecret ��Կ
	 * @return token
	 * */
	public static Token getToken(String appid,String appsecret){
		Token token = null;
		String requestUrl = token_url;
		
		//����get�����ȡƾ֤
		JSONObject jsonObject = httpsRequest(requestUrl,"GET",null);
		
		if(jsonObject != null){
			try{
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}catch(JSONException e){
				token = null;
				//��ȡtokenʧ��
				e.printStackTrace();
			}
		}
		return token;
	}
}
