package weixin.message.response;

public class BaseMessage {
	//接收方账号（收到的OpenID）
	private String ToUserName;
	//开发者微信号
	private String FromUserName;
	//消息创建时间（整形）
	private long CreateTime;
	//消息类型（text/image/location/link/voice）
	private String MsgType;
	//消息ID，64位整形
	
	public String getToUserName(){
		return ToUserName;
	}
	
	public void setToUserName(String toUserName){
		ToUserName = toUserName;
	}
	
	public String getFromUserName(){
		return FromUserName;
	}
	
	public void setFromUserName(String fromUserName){
		FromUserName = fromUserName;
	}
	
	public long getCreateTime(){
		return CreateTime;
	}
	
	public void setCreateTime(long createTime){
		CreateTime = createTime;
	}
	
	public String getMsgType(){
		return MsgType;
	}
	
	public void setMsgType(String msgType){
		MsgType = msgType;
	}
}
