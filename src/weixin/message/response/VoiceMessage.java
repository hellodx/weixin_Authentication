package weixin.message.response;

public class VoiceMessage extends BaseMessage{
	//����
	private Voice Voice;
	
	public Voice getVoice(){
		return Voice;
	}
	
	public void setVoice(Voice voice){
		Voice = voice;
	}
}
