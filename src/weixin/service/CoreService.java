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
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 * */
	public static String processRequest(HttpServletRequest request){
		//XML格式的消息数据
		String respXml = null;
		//默认返回的文本消息内容
		String respContent = "";
		try{
			//调用parseXml方法解析请求消息
			Map<String,String> requestMap = MessageUtil.parseXml(request);
			//发送方账号
			String fromUserName = requestMap.get("FromUserName");
			//开发者微信号
			String toUserName = requestMap.get("ToUserName");
			//消息类型
			String msgType = requestMap.get("MsgType");
			//消息创建时间
			//String createTime = requestMap.get("CreateTime");
			
			//回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			switch(msgType){
			//文本消息
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
				String msgContent = requestMap.get("Content");
				if(msgContent.equals("胖丁")) respContent = "boboli";
				else respContent = "您发送的是文本消息";
				break;
				
			//图片消息
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
				respContent = "您发送的是图片消息";
				break;
				
			//语音消息
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
				respContent = "您发送的是语音消息";
				break;
			
			//视频消息
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:
				respContent = "您发送的是视频消息";
				break;
				
			//地理位置消息
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
				respContent = "您发送的是地理位置消息";
				break;
				
			//链接消息
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:
				respContent = "您发送的是连接消息";
				break;
				
			//事件推送
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
				String eventType = requestMap.get("Event");
				switch(eventType){
				//关注事件
				case MessageUtil.EVENT_TYPE_SUBSCRIBE:
					respContent = "谢谢您的关注";
					break;
					
				//取消关注
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
					//TODO 取消订阅不需要回复
					if(MySQLUtil.QueryAuthMethod().equals("2")){
						MySQLUtil.DeleteByOpenId(fromUserName);
					}
					
					break;
					
				//扫描带参数二维码
				case MessageUtil.EVENT_TYPE_SCAN:
					//TODO 处理扫描带参数二维码事件
					break;
					
				//上报地理位置
				case MessageUtil.EVENT_TYPE_LOCATION:
					//TODO 处理上报地理位置事件
					break;
					
				//自定义菜单
				case MessageUtil.EVENT_TYPE_CLICK:
					
					//MySQLUtil.InsertInfo("insert_rec",fromUserName,msgType,eventType,toUserName,createTime);
					//事件key值,与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					//根据key值判断用户点击的按钮
					if(eventKey.equals("wifi")){
						String authMethod = MySQLUtil.QueryAuthMethod();
						if(authMethod.equals("2")){
							Article article = new Article();
							article.setTitle("连WiFi");
							article.setDescription("欢迎使用本店WiFi\n请点击此条图文消息完成认证");
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
							//创建图文消息
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticleCount(articleList.size());
							newsMessage.setArticles(articleList);
							
							respXml = MessageUtil.messageToXml(newsMessage);
						}else{
							respContent = "您好，商家未开启此种认证方式，请使用portal认证！\n详情请咨询商家";
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
				//设置文本消息的内容
				textMessage.setContent(respContent);
				//将文本消息转换为XML
				respXml = MessageUtil.messageToXml(textMessage);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return respXml;
	}
}
