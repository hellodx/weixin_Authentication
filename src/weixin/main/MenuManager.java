package weixin.main;

import weixin.menu.Button;
import weixin.menu.ClickButton;
import weixin.menu.ComplexButton;
import weixin.menu.Menu;
import weixin.menu.ViewButton;


public class MenuManager {
	/*
	 * ����˵��ṹ
	 * 
	 * @return
	 * */
	
	public static Menu getMenu(){
		ClickButton btn11 = new ClickButton();
		btn11.setName("WiFi��֤");
		btn11.setType("click");
		btn11.setKey("wifi");
		
//		ClickButton btn12 = new ClickButton();
//		btn12.setName("ITeye");
//		btn12.setType("click");
//		btn12.setKey("iteye");
//		
//		ViewButton btn13 = new ViewButton();
//		btn13.setName("CocoaChina");
//		btn13.setType("view");
//		btn13.setUrl("http://www.iteye.com");
		
		ViewButton btn21 = new ViewButton();
		btn21.setName("����");
		btn21.setType("view");
		btn21.setUrl("http://m.jd.com");
		
		ViewButton btn22 = new ViewButton();
		btn22.setName("ΨƷ��");
		btn22.setType("view");
		btn22.setUrl("http://m.vipshop.com");
		
		ViewButton btn23 = new ViewButton();
		btn23.setName("������");
		btn23.setType("view");
		btn23.setUrl("http://m.dangdang.com");
		
		ViewButton btn24 = new ViewButton();
		btn24.setName("�Ա�");
		btn24.setType("view");
		btn24.setUrl("http://m.taobao.com");
		
		ViewButton btn31 = new ViewButton();
		btn31.setName("����");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");
		
		ViewButton btn32 = new ViewButton();
		btn32.setName("һ��88");
		btn32.setType("view");
		btn32.setUrl("http://www.yi588.com");
		
		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("��Ա����");
		mainBtn1.setSub_button(new Button[]{btn11});
		
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("����");
		mainBtn2.setSub_button(new Button[]{btn21,btn22,btn23,btn24});
		
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("��ҳ��Ϸ");
		mainBtn3.setSub_button(new Button[]{btn31,btn32});
		
		Menu menu = new Menu();
		menu.setButton(new Button[]{mainBtn1,mainBtn2,mainBtn3});
		
		return menu;
	}
	
//	public static void main(String[] args){
//		String appId = "wx94c8fa7d9c7b068d";
//		String appSecret = "5f8f9ceb744dd096afcf85435af52a7d";
//		
//		Token token = CommonUtil.getToken(appId, appSecret);
//		
//		if(token != null){
//			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
//			if(result){
////				System.out.println("�˵������ɹ�");
//			}else{
////				System.out.println("�˵�����ʧ��");
//			}
//		}
	
//	String appId = "wx94c8fa7d9c7b068d";
//	String appSecret = "5f8f9ceb744dd096afcf85435af52a7d";
//	Token token = CommonUtil.getToken(appId, appSecret);
//	Menu menu = new Menu();
//	menu = MenuManager.getMenu();
//	respContent = MenuUtil.createMenu(menu, token.getAccessToken());
//	}
}
