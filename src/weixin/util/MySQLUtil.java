package weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;

public class MySQLUtil {
	//���ݿ�������
	public static final String host_name = "w.rdc.sae.sina.com.cn";
	//���ݿ���
	public static final String db_name = "app_authserver";
	//�˿ں�
	public static final String port = "3307";
	//�û��� SAE Access Key
	public static final String username = "xzxw112yzl";
	//���� SAE Secret Key
	public static final String password = "hwh0z3y3jzy0l3z2k5y150xxj302123y3j1ixi2x";
	
	/*
	 * ��ȡMySQL���ݿ�����
	 * 
	 * @return Connection
	 * */
	private Connection getConn(){
		Connection conn = null;
		
		//JDBC��URL
		String url = String.format("jdbc:mysql://%s:%s/%s",host_name,port,db_name);
		
		try{
			//����MySQL JDBC����
			Class.forName("com.mysql.jdbc.Driver");
			//��ȡ���ݿ�����
			conn = DriverManager.getConnection(url,username,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * ��ѯ���ݿ�
	 * 
	 * @param condition(1.all ��ѯ���л�Ա	2.active ��ѯ���߻�Ա	3.new ��ѯ������Ա)
	 * 
	 * @return result
	 * */
	
	public static String getUser(String condition){
		String result = null;
		String sql = null;
		
		switch(condition){
		case "all":
			sql = "select * from User";
			break;
		case "active":
			sql = "select * from User where status = 'active'";
			break;
		case "new":
			//calender����
			Calendar c1 = Calendar.getInstance();
			String year = Integer.toString(c1.get(Calendar.YEAR));
			String month = Integer.toString(c1.get(Calendar.MONTH) + 1);
			String day = Integer.toString(c1.get(Calendar.DATE));
			String date = String.format("%s/%s/%s", year,month,day);
			sql = "select * from User where regDate='RegDate'".replace("RegDate", date);
			break;
		default:
			return null;	
		}
		
		JSONStringer stringer = new JSONStringer();
		
		try{
			//��ȡ����
			Connection conn = new MySQLUtil().getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			stringer.array();			
			while(rs.next()){
				String id = rs.getString("userId");
				String wxOpenID =rs.getString("wxOpenId");
				String phoneNum = rs.getString("phoneNum");
				String regDate = rs.getString("regDate");
				String status = rs.getString("status");
				stringer.object().key("ID").value(id).
					key("WxOpenID").value(wxOpenID).
					key("PhoneNum").value(phoneNum).
					key("RegDate").value(regDate).
					key("Status").value(status).endObject();
			}
			stringer.endArray();
			
			rs.close();
			ps.close();
			conn.close();
		}catch(JSONException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(stringer != null){
			result = stringer.toString();
		}
		
		return result;
	}
	
	/*
	 * �������ݵ�����
	 * 
	 * @param table_name ����
	 * @param values ��������(�������ݣ�˳�������ݿ�����˳��һ��)
	 * 
	 * @return result(boolean)
	 * */
	public static boolean InsertInfo(String table_name,String ...values){
		boolean result = false;
		String sql_value = "values(";
		for(int i =0;i<values.length-1;i++){
			sql_value += "?,";
		}
		sql_value += "?)";
		String sql = String.format("insert into %s " + sql_value, table_name);
		
		try{
			//��ȡ����
			Connection conn = new MySQLUtil().getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i =0;i<values.length;i++){
				ps.setString(i+1, values[i]);
			}
			int state = ps.executeUpdate();
			if(state == 0) result = false;
			else result = true;
			
			ps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * ��ѯ��ǰ��֤��ʽ
	 * 
	 * @return result
	 * 1:portal��֤	2:���ں���֤	3:����
	 * */
	public static String QueryAuthMethod(){
		String result = "";
		String sql = "select * from wifiauth where mac=?";
		try{
			//��ȡ����
			Connection conn = new MySQLUtil().getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "admin");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				result = rs.getString("status");
			}
			rs.close();
			ps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * ȡ��ɾ��
	 * 
	 * @param OpenId
	 * 
	 * @return result
	 * */
	public static boolean DeleteByOpenId(String openId){
		boolean result = false;
		String sql = "delete from wifiauth where wxOpenId=?";
		try{
			//��ȡ����
			Connection conn = new MySQLUtil().getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			int state = ps.executeUpdate();
			if(state == 0) result = false;
			else result = true;
			
			ps.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
}

