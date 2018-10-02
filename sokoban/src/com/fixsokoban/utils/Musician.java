package com.fixsokoban.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Musician extends Thread {

	private FileInputStream music;// 声明文件流对象
	private sun.audio.AudioStream play_music;// 声明音频流对象
	private File musicFile;// 音频文件
	private Boolean flag = true; // 结束标志

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Musician(String musicFilePath) {
		super();
		musicFile = new File(musicFilePath);
	}

	public boolean stopMusic() throws IOException {
		this.play_music.close();
		return true;
	}

	/*
	 * 线程运行
	 */
	public void run() {

		int musicTime = 0;

		// 循环播放
		while (flag) {

			Clip clip = null;

			try {
				clip = AudioSystem.getClip();
				AudioInputStream ais = AudioSystem
						.getAudioInputStream(musicFile);
				clip.open(ais);
			} catch (LineUnavailableException | UnsupportedAudioFileException
					| IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				music = new FileInputStream(musicFile);// 创建文件流对象
				play_music = new sun.audio.AudioStream(music);// 创建音频流对象
			} catch (Exception e) {
				System.out.println(e);
			}

			musicTime = (int) (clip.getMicrosecondLength() / 1000D);
			sun.audio.AudioPlayer.player.start(play_music);// 开始播放

			try {
				Thread.sleep(musicTime);// 音频播放时间
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
