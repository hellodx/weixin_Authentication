package weixin.wifi.dao;

public class User {
	//会员ID
	private String UserId;
	//微信OpenId
	private String WxOpenId;
	//手机号
	private String PhoneNum;
	//注册日期
	private String RegDate;
	//在线状态
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
