package weixin.wifi.dao;

public class User {
	//��ԱID
	private String UserId;
	//΢��OpenId
	private String WxOpenId;
	//�ֻ���
	private String PhoneNum;
	//ע������
	private String RegDate;
	//����״̬
	private String Status;
	
	public String getUserId(){
		return UserId;
	}
	
	public void setUserId(String userId){
		this.UserId = userId;
	}
	
	public String getWxOpenId(){
		return WxOpenId;
	}
	
	public void setWxOpenId(String wxOpenId){
		this.WxOpenId = wxOpenId;
	}
	
	public String getPhoneNum(){
		return PhoneNum;
	}
	
	public void setPhoneNum(String phoneNum){
		this.PhoneNum = phoneNum;
	}
	
	public String getRegDate(){
		return RegDate;
	}
	
	public void setRegDate(String regDate){
		this.RegDate = regDate;
	}
	
	public String getStatus(){
		return Status;
	}
	
	public void setStatus(String status){
		this.Status = status;
	}
}
