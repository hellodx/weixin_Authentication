package weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;

public class MySQLUtil {
	//数据库主机名
	public static final String host_name = "w.rdc.sae.sina.com.cn";
	//数据库名
	public static final String db_name = "app_authserver";
	//端口号
	public static final String port = "3307";
	//用户名 SAE Access Key
	public static final String username = "xzxw112yzl";
	//密码 SAE Secret Key
	public static final String password = "hwh0z3y3jzy0l3z2k5y150xxj302123y3j1ixi2x";
	
	/*
	 * 获取MySQL数据库连接
	 * 
	 * @return Connection
	 * */
	private Connection getConn(){
		Connection conn = null;
		
		//JDBC　URL
		String url = String.format("jdbc:mysql://%s:%s/%s",host_name,port,db_name);
		
		try{
			//加载MySQL JDBC驱动
			Class.forName("com.mysql.jdbc.Driver");
			//获取数据库连接
			conn = DriverManager.getConnection(url,username,password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
	
	/*
	 * 查询数据库
	 * 
	 * @param condition(1.all 查询所有会员	2.active 查询在线会员	3.new 查询新增会员)
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
			//calender对象
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
			//获取连接
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
	 * 插入数据到表中
	 * 
	 * @param table_name 表名
	 * @param values 插入数据(多项数据，顺序与数据库数据顺序一致)
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
			//获取连接
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
	 * 查询当前认证方式
	 * 
	 * @return result
	 * 1:portal认证	2:公众号认证	3:并存
	 * */
	public static String QueryAuthMethod(){
		String result = "";
		String sql = "select * from wifiauth where mac=?";
		try{
			//获取连接
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
	 * 取关删除
	 * 
	 * @param OpenId
	 * 
	 * @return result
	 * */
	public static boolean DeleteByOpenId(String openId){
		boolean result = false;
		String sql = "delete from wifiauth where wxOpenId=?";
		try{
			//获取连接
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

