package weixin.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import weixin.message.response.TextMessage;
import weixin.message.response.VoiceMessage;
import weixin.message.response.Article;
import weixin.message.response.ImageMessage;
import weixin.message.response.NewsMessage;

public class MessageUtil {
	//请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	//请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	//请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	//请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	//请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	//请求消息类型：连接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	
	//请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
	//事件类型：subscribe(关注/订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	//事件类型：unsubscribe(取消关注/订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	//事件类型：scan(关注用户扫描二维码)
	public static final String EVENT_TYPE_SCAN = "scan";
	//事件类型：location(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	//事件类型：click(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	//事件类型：WiFi连接
	public static final String EVENT_TYPE_WifiConnected = "WifiConnected";
	
	//响应消息类型:文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	//响应消息类型:图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	//响应消息类型:语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	//响应消息类型:视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	//响应消息类型:音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC= "music";
	//响应消息类型:图文
	public static final String RESP_MESSAGE_TYPE_NEWS= "news";
	
	
	/*
	 * 解析微信发送来的请求(xml)
	 * 
	 * @param request
	 * @return Map<String,String>
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	public static Map<String,String> parseXml(HttpServletRequest request) throws Exception{
		//将解析结果存储在HashMap中
		Map<String,String> map = new HashMap<String,String>();
		
		//从request中获取输入流
		InputStream inputStream = request.getInputStream();
		//读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		//得到XML根元素
		Element root = document.getRootElement();
		//得到根元素的所有子节点
		List<Element> elementList = root.elements();
		
		//遍历所有子节点
		for(Element e : elementList)
			map.put(e.getName(), e.getText());
		
		//释放资源
		inputStream.close();
		inputStream = null;
		
		return map;
	}
	
	/*
	 * 扩展xstream使其支持CDATA
	 * */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				//对所有XML节点的转换都增加CDATA标记
				boolean cdata = true;
				
				@SuppressWarnings("rawtypes")
				public void startNode(String name,Class clazz){
					super.startNode(name,clazz);
				}
				
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
	
	/*
	 * 文本消息对象转为XML
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 * */
	public static String messageToXml(TextMessage textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/*
	 * 图片消息对象转为XML
	 * 
	 * @param imageMessage
	 * @return xml
	 * */
	public static String messageToXml(ImageMessage imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/*
	 * 语音消息对象转为XML
	 * 
	 * @param voiceMessage
	 * @return xml
	 * */
	public static String messageToXml(VoiceMessage voiceMessage){
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
	
	/*
	 * 图文消息对象转为XML
	 * 
	 * @param newsMessage
	 * @return xml
	 * */
	public static String messageToXml(NewsMessage newsMessage){
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
