package qq.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class MusicUtil {

	public static void main() throws MalformedURLException, FileNotFoundException, InterruptedException {
		//ѡ�񲥷��ļ�
		File file = new File("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\music\\msg.wav");
		//����audioclip����
		AudioClip audioClip = null;
		//��fileת��Ϊurl
		audioClip = Applet.newAudioClip(file.toURL());
		//ѭ������audioClip,loop()	����һ�ο���ʹ��audioClip.play
		audioClip.play();
		Thread.sleep(5000);
	}
}
