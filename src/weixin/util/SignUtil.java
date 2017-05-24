package weixin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	//与开发模式接口配置信息中的Token一致
	private static String token = "weixin";
	
	//将字节数组转化为十六进制字符串
	private static String byteToStr(byte[] byteArray){
		String strDigest = "";
		for(int i=0;i<byteArray.length;i++){
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	
	//将字节转化为十六进制字符串
	private static String byteToHexStr(byte mByte){
		char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte>>>4) & 0x0F];
		tempArr[1] = Digit[mByte & 0x0F];
		
		String s = new String(tempArr);
		
		return s;
	}
	
	public static boolean chechSignature(String signature,String timestamp,String nonce){
		//对token、timestamp、nonce按字典顺序排序
		String[] paramArr = new String[]{token,timestamp,nonce};
		Arrays.sort(paramArr);
		//将排序结果拼接成一个字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		
		String ciphertext = null;
		try{
			//对拼接的字符进行SHA-1加密
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		
		//将SHA1加密后的字符串与signature对比
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}
}
