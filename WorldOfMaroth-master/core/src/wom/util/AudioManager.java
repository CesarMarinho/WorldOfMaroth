package wom.util;



import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

	public static final AudioManager instance = new AudioManager();

	private Music playingMusic;


	private AudioManager () {
	}

	public void play (Sound sound) {
		play(sound, 10);
	}

	public void play (Sound sound, float volume) {
		play(sound, volume, 1);
	}

	public void play (Sound sound, float volume, float pitch) {
		play(sound, volume, pitch, 0);
	}

	public void play (Sound sound, float volume, float pitch, float pan) {
		sound.play(10.5f, pitch, pan);
	}

	public void play (Music music) {
		playingMusic = music;
			music.setLooping(true);
			music.setVolume(6);
			music.play();
	}

	public void stopMusic () {
		if (playingMusic != null) playingMusic.stop();
	}

	public Music getPlayingMusic () {
		return playingMusic;
	}

	public void onSettingsUpdated () {
		if (playingMusic == null) return;
		playingMusic.setVolume(6);
	}

}