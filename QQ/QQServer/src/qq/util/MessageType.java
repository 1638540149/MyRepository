package qq.util;

public interface MessageType {

	public static final int LOGIN_SUCCESS=1;
	public static final int LOGIN_FAILURE=2;
	public static final int REGISTER_SUCCESS=5;
	public static final int REGISTER_FAILURE=6;
	
	public static final int REGISTER=3;
	public static final int LOGIN=4;
	
	public static final int GET_USER_LIST=7;
	public static final int GET_USER_LIST_SUCCESS=8;
	
	public static final int CHAT_NORMAL=9;
	public static final int CHAT_CONNECTION=10;
	public static final int CHAT_LEAVE=15;
	public static final int GET_CHAT_LEAVE=16;
	public static final int CHAT_CLOSE=11;
	
	public static final int REFRESH_FRIEND=12;
	public static final int EXIT=13;
	public static final int REFRESH_FRIEND_EXIT=14;
	
}
