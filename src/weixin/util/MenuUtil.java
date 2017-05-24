package weixin.util;

import net.sf.json.JSONObject;
import weixin.menu.Menu;

public class MenuUtil {
	//�˵�����(POST)
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//�˵���ѯ(GET)
	public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//�˵�ɾ��(GET)
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/*
	 * �����˵�
	 * 
	 * @param menu �˵�ʵ��
	 * @param access_token �ӿ�ƾ֤
	 * @return true or false
	 * */
	
	public static String createMenu(Menu menu,String accessToken){
		String result = "";
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		//��String����ת����JSON�ַ���
		String jsonMenu = JSONObject.fromObject(menu).toString();
		//����POST���󴴽��˵�
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);
		
		if(jsonObject != null){
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(errorCode == 0){
				result = "�˵������ɹ���errcode="+errorCode+"\terrorMsg="+errorMsg ;
			}else{
				result = "�˵�����ʧ�ܣ�errcode="+errorCode+"\terrorMsg="+errorMsg;
				//System.out.println(errorCode + errorMsg);
			}
		}
		return result;
	}
	
	/*
	 * ��ѯ�˵�
	 * 
	 * @param accessToken ƾ֤
	 * 
	 * return �˵�JSON����ת��ΪString����
	 * */
	
	public static String getMenu(String accessToken){
		String result = null;
		String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		//����get�����ѯ�˵�
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		if(jsonObject != null){
			result = jsonObject.toString();
		}
		
		return result;
	}
	
	/*
	 * ɾ���˵�
	 * 
	 * @param accessToken ƾ֤
	 * 
	 * @return true or false
	 * 
	 * */
	
	public static boolean deleteMenu(String accessToken){
		boolean result = false;
		String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		//����get����ɾ���˵�
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		if(jsonObject != null){
			int errorCode = jsonObject.getInt("errcode");
			//String errorMsg = jsonObject.getString("errmsg");
			if(errorCode == 0){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}
}
