package weixin.util;

import net.sf.json.JSONObject;
import weixin.menu.Menu;

public class MenuUtil {
	//菜单创建(POST)
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//菜单差询(GET)
	public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//菜单删除(GET)
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/*
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param access_token 接口凭证
	 * @return true or false
	 * */
	
	public static String createMenu(Menu menu,String accessToken){
		String result = "";
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		//将String对象转换成JSON字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		//发起POST请求创建菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);
		
		if(jsonObject != null){
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(errorCode == 0){
				result = "菜单创建成功：errcode="+errorCode+"\terrorMsg="+errorMsg ;
			}else{
				result = "菜单创建失败：errcode="+errorCode+"\terrorMsg="+errorMsg;
				//System.out.println(errorCode + errorMsg);
			}
		}
		return result;
	}
	
	/*
	 * 查询菜单
	 * 
	 * @param accessToken 凭证
	 * 
	 * return 菜单JSON对象转换为String对象
	 * */
	
	public static String getMenu(String accessToken){
		String result = null;
		String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		//发起get请求查询菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		if(jsonObject != null){
			result = jsonObject.toString();
		}
		
		return result;
	}
	
	/*
	 * 删除菜单
	 * 
	 * @param accessToken 凭证
	 * 
	 * @return true or false
	 * 
	 * */
	
	public static boolean deleteMenu(String accessToken){
		boolean result = false;
		String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		//发起get请求删除菜单
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
