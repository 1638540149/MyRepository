package qq.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class MusicUtil {

	public static void main() throws MalformedURLException, FileNotFoundException, InterruptedException {
		//选择播放文件
		File file = new File("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\music\\msg.wav");
		//创建audioclip对象
		AudioClip audioClip = null;
		//将file转换为url
		audioClip = Applet.newAudioClip(file.toURL());
		//循环播放audioClip,loop()	播放一次可以使用audioClip.play
		audioClip.play();
		Thread.sleep(5000);
	}
}
