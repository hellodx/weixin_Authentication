package weixin.message.response;

public class ImageMessage extends BaseMessage{
	private Image Image;
	
	public Image getImage(){
		return Image;
	}
	
	public void setImage(Image image){
		Image = image;
	}
}
