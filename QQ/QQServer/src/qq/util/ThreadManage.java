package qq.util;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ThreadManage {

	public static Map<String, TalkThread> threadMap=new HashMap<String,TalkThread>();
	
	public static Map<String, Socket>socketMap=new HashMap<String,Socket>();
}
