package weixin.message.event;

public class MenuEvent extends BaseEvent{
	//�¼�keyֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	private String EventKey;
	
	public String getEventKey(){
		return EventKey;
	}
	
	public void setEventKey(String eventKey){
		EventKey = eventKey;
	}
}
