package weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import weixin.message.response.Article;
import weixin.message.response.NewsMessage;
import weixin.message.response.TextMessage;
import weixin.util.MessageUtil;
import weixin.util.MySQLUtil;


public class CoreService {
	/*
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 * */
	public static String processRequest(HttpServletRequest request){
		//XML��ʽ����Ϣ����
		String respXml = null;
		//Ĭ�Ϸ��ص��ı���Ϣ����
		String respContent = "";
		try{
			//����parseXml��������������Ϣ
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			//���ͷ��˺�
			String fromUserName = requestMap.get("FromUserName");
			//������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			//��Ϣ����
			String msgType = requestMap.get("MsgType");
			//��Ϣ����ʱ��
			//String createTime = requestMap.get("CreateTime");
			
			//�ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			switch(msgType){
			//�ı���Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				String msgContent = requestMap.get("Content");
				if(msgContent.equals("�ֶ�")) respContent = "boboli";
				else respContent = "�����͵����ı���Ϣ";
				break;
				
			//ͼƬ��Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "�����͵���ͼƬ��Ϣ";
				break;
				
			//������Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "�����͵���������Ϣ";
				break;
			
			//��Ƶ��Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "�����͵�����Ƶ��Ϣ";
				break;
				
			//����λ����Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "�����͵��ǵ���λ����Ϣ";
				break;
				
			//������Ϣ
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "�����͵���������Ϣ";
				break;
				
			//�¼�����
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				String eventType = requestMap.get("Event");
				switch(eventType){
				//��ע�¼�
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					respContent = "лл���Ĺ�ע";
					break;
					
				//ȡ����ע
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					//TODO ȡ�����Ĳ���Ҫ�ظ�
					if(MySQLUtil.QueryAuthMethod().equals("2")){
						MySQLUtil.DeleteByOpenId(fromUserName);
					}
					
					break;
					
				//ɨ���������ά��
				case MessageUtil.EVENT_TYPE_SCAN:
					//TODO ����ɨ���������ά���¼�
					break;
					
				//�ϱ�����λ��
				case MessageUtil.EVENT_TYPE_LOCATION:
					//TODO �����ϱ�����λ���¼�
					break;
					
				//�Զ���˵�
				case MessageUtil.EVENT_TYPE_CLICK:
					
					//MySQLUtil.InsertInfo("insert_rec",fromUserName,msgType,eventType,toUserName,createTime);
					//�¼�keyֵ,�봴���˵�ʱ��keyֵ��Ӧ
					String eventKey = requestMap.get("EventKey");
					//����keyֵ�ж��û�����İ�ť
					if(eventKey.equals("wifi")){
						String authMethod = MySQLUtil.QueryAuthMethod();
						if(authMethod.equals("2")){
							Article article = new Article();
							article.setTitle("��WiFi");
							article.setDescription("��ӭʹ�ñ���WiFi\n��������ͼ����Ϣ�����֤");
							article.setPicUrl("https://wifi.weixin.qq.com/resources/images/background.jpg");
							article.setUrl("http://authserver.applinzi.com/reLogin/?wxopenid="+fromUserName);
							
//							Article article1 = new Article();
//							article1.setTitle("ChinaNBA");
//							article1.setDescription("Description \n ChinaNBA");
//							article1.setPicUrl("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/"
//									+ "u=1613426663,3260446361&fm=58&s=51575896F68A9AB85E91D5A60100A0A0");
//							article1.setUrl("http://china.nba.com/");
							
							List<Article> articleList = new ArrayList<Article>();
							articleList.add(article);
							//����ͼ����Ϣ
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticleCount(articleList.size());
							newsMessage.setArticles(articleList);
							
							respXml = MessageUtil.messageToXml(newsMessage);
						}else{
							respContent = "���ã��̼�δ����������֤��ʽ����ʹ��portal��֤��\n��������ѯ�̼�";
						}
						
					}else if(eventKey.equals("iteye")){
//						String appId = "wx94c8fa7d9c7b068d";
//						String appSecret = "5f8f9ceb744dd096afcf85435af52a7d";
//						Token token = CommonUtil.getToken(appId, appSecret);
//						Menu menu = new Menu();
//						menu = MenuManager.getMenu();
//						respContent = MenuUtil.createMenu(menu, token.getAccessToken());
					}
					break;
				case MessageUtil.EVENT_TYPE_WifiConnected:
//					String shopID = requestMap.get("ShopId");
//					String deviceNo = requestMap.get("DeviceNo");
//					String connectTime = requestMap.get("ConnectTime");
//					String expireTime = requestMap.get("ExpireTime");
//					String vendorId = requestMap.get("VendorId");
					
//					boolean res = MySQLUtil.InsertInfo("WiFiUser",toUserName,fromUserName,
//							createTime,msgType,eventType,connectTime,expireTime,vendorId,shopID,deviceNo);
					//MySQLUtil.InsertInfo("insert_rec",fromUserName,msgType,eventType,toUserName,createTime);
//					if(res == false){
//						MySQLUtil.InsertInfo("insert_rec",fromUserName,msgType,eventType,toUserName,createTime);
//					}
					break;
				default:
					//MySQLUtil.InsertInfo("insert_rec",fromUserName,msgType,eventType,toUserName,createTime);
					break;
				}
				break;
			}
			
			if(!respContent.isEmpty()){
				//�����ı���Ϣ������
				textMessage.setContent(respContent);
				//���ı���Ϣת��ΪXML
				respXml = MessageUtil.messageToXml(textMessage);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return respXml;
	}
}
