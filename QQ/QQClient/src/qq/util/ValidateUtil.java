package qq.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ^  为限制开�?  <br/>
 * $  为限制结�?  <br/>
 * .  为条件限制除/n以外任意�?个单独字�?  <br/>
 * +  为至少出现一�?  <br/>
 * *  很多�?  <br/>
 */
public class ValidateUtil {

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isString(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z]*$");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isChinese(String str) {
		// 编码
		Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5]*$");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(ValidateUtil.isString("javajpo"));
		System.out.println(ValidateUtil.isNumber("1111111"));
		System.out.println(ValidateUtil.isChinese("啊啊�?"));
	}
	
}
