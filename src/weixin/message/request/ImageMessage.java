package weixin.message.request;

public class ImageMessage extends BaseMessage{
	//ͼƬ����
	private String PicUrl;
	
	public String getPicUrl(){
		return PicUrl;
	}
	
	public void setPicUrl(String picUrl){
		PicUrl = picUrl;
	}
}
