package weixin.message.response;

public class BaseMessage {
	//���շ��˺ţ��յ���OpenID��
	private String ToUserName;
	//������΢�ź�
	private String FromUserName;
	//��Ϣ����ʱ�䣨���Σ�
	private long CreateTime;
	//��Ϣ���ͣ�text/image/location/link/voice��
	private String MsgType;
	//��ϢID��64λ����
	
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
