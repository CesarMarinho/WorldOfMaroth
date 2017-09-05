package wom.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import wom.objects.EnergyBall;


public class Assets implements Disposable
{

	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	public Mage mage;
	public Ground ground;
	public Rock rock;
	public Iron iron;
	public Thorn thorn;
	public Slime slime;
	public AssetFonts fonts;
	public KeyBlue keyBlue;
	public KeyYellow keyYellow;
	public KeyGreen keyGreen;
	public BlueDoor blueDoor;
	public AssetMusic assetMusic;
	public AssetSounds assetSound;
	public GreenDoor greenDoor;
	public YellowDoor yellowDoor;
	public EnergyBall energyBall;
	public MenuItens menuItens;
	public Turtle turtle;
	public Archer archer;
	public Gate gate;
	public Vulture vulture;

	
	public class Vulture
	{
		public AtlasRegion vulture;
		public AtlasRegion vulturePower;
		public final Animation animNormal;
		public Vulture(TextureAtlas atlas)
		{
			Array<AtlasRegion> regions = null;
			AtlasRegion region = null;
			
			vulture = atlas.findRegion("vulture1");
			vulturePower = atlas.findRegion("vulturePower");
			
			regions = atlas.findRegions("vulture1");
			animNormal = new Animation(1.0f / 10.0f, regions,Animation.PlayMode.NORMAL);
		}
	}
	
	public class Gate
	{
		public AtlasRegion gate;
		
		public Gate(TextureAtlas atlas)
		{
			gate = atlas.findRegion("portal1");
		}
		
	}
	
	public class Archer
	{
		public AtlasRegion body;
		public AtlasRegion arrow;
		public final Animation animNormal;	
		public final Animation animNull;
		
		public Archer(TextureAtlas atlas){
			
			body = atlas.findRegion("archer1");
			arrow = atlas.findRegion("arrow");
			
			Array<AtlasRegion> regions = null;
			AtlasRegion region = null;

			regions = atlas.findRegions("archer1");
			animNormal = new Animation(1.0f / 5.0f, regions, Animation.PlayMode.NORMAL);
			
			regions = atlas.findRegions("archer1");
			animNull = new Animation(1.0f, regions, Animation.PlayMode.LOOP_RANDOM);
			
			
		}
	}
	
	public class Turtle
	{
		public AtlasRegion body;
		public final Animation animNormal;	
		
		public Turtle(TextureAtlas atlas)
		{
			body = atlas.findRegion("turtlle1");
			Array<AtlasRegion> regions = null;
			AtlasRegion region = null;
			
			regions = atlas.findRegions("turtlle1");
			animNormal = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.NORMAL);
		}
	}
	public class MenuItens
	{
		public AtlasRegion titleMaroth;
		public AtlasRegion endButton;
		public AtlasRegion voltar;
		public AtlasRegion upgrades;
		public AtlasRegion achButton;
		public AtlasRegion beginButton;
		public AtlasRegion buyLife;
		public AtlasRegion conquestFly;
		public AtlasRegion upgradeMagic;
		public AtlasRegion openDoors;
		public AtlasRegion coins;
		public AtlasRegion leeAndrows;
		public AtlasRegion begin;
		public AtlasRegion backGround2;
		public AtlasRegion gameOver;
		public AtlasRegion endGame;
		public AtlasRegion tryAgain;
		
		public MenuItens(TextureAtlas atlas)
		{
			begin = atlas.findRegion("inicial");
			titleMaroth = atlas.findRegion("background");
			endButton = atlas.findRegion("endButton");
			voltar = atlas.findRegion("voltar");
			upgrades = atlas.findRegion("upgrades");
			achButton = atlas.findRegion("achButton");
			beginButton = atlas.findRegion("beginButton");
			buyLife = atlas.findRegion("comprarVidas");
			conquestFly = atlas.findRegion("aumentarVoo");
			upgradeMagic = atlas.findRegion("upgradeMagia");
			openDoors = atlas.findRegion("abrirPortas");
			coins = atlas.findRegion("moedas");
			leeAndrows = atlas.findRegion("leeAndrows");
			backGround2 = atlas.findRegion("background2");
			gameOver = atlas.findRegion("gameOver");
			endGame = atlas.findRegion("fimDeJogo");
			tryAgain = atlas.findRegion("jogarNovamente");
		}
	}
	
	public class EnergyBall{
		public AtlasRegion energyBall;
		
		public EnergyBall(TextureAtlas atlas){
			energyBall = atlas.findRegion("power1");
		}
	}
	
	public class KeyYellow{
		public AtlasRegion keyYellow;
		
		public KeyYellow(TextureAtlas atlas){
			keyYellow = atlas.findRegion("yellowKey");
		}
	}
	
	public class KeyGreen{
		public AtlasRegion keyGreen;
		
		public KeyGreen(TextureAtlas atlas){
			keyGreen = atlas.findRegion("greenKey");
		}
	}
	
	public class AssetSounds
	{
		public final Sound jump;
		public final Sound getItem;
		public final Sound magia;
		public final Sound door;
		public final Sound getKey;
		public final Sound damage;
		public final Sound songMenu;
		public final Sound deathMonster;
		public final Sound loss;
		public final Sound winning;
		
		public AssetSounds(AssetManager assetManager)
		{
			jump = assetManager.get("music/jump.mp3", Sound.class);
			getItem = assetManager.get("music/item.wav", Sound.class);
			magia = assetManager.get("music/Fire.wav", Sound.class);
			door = assetManager.get("music/Porta.mp3", Sound.class);
			getKey = assetManager.get("music/Ring.wav", Sound.class);
			damage = assetManager.get("music/Damage.mp3", Sound.class);
			songMenu = assetManager.get("music/somMenu.wav", Sound.class);
			deathMonster = assetManager.get("music/DeathMonster.wav", Sound.class);
			loss = assetManager.get("music/Loss.wav", Sound.class);
			winning = assetManager.get("music/Winning.wav", Sound.class);
		}
	}
	
	public class AssetMusic
	{
		public final Music menuSong;
		public final Music gamePlaySong;
		public final Music bossSong;
		
		public AssetMusic(AssetManager assetManager)
		{
			menuSong = assetManager.get("music/Tearfalls and Pain.mp3", Music.class);
			gamePlaySong = assetManager.get("music/The Desolation Of Maroth.mp3", Music.class);	
			bossSong = assetManager.get("music/The Fuderation Of Lee Androws.mp3", Music.class);
		}
	}
	
	public class KeyBlue{
		public AtlasRegion keyBlue;
		
		public KeyBlue(TextureAtlas atlas){
			keyBlue = atlas.findRegion("blueKey");
		}
	}
	

	public class YellowDoor
	{
		public AtlasRegion yellowDoor;
		
		public YellowDoor(TextureAtlas atlas){
			yellowDoor = atlas.findRegion("yellowDoor");
		}
	}
	
	public class GreenDoor
	{
		public AtlasRegion greenDoor;
		
		public GreenDoor(TextureAtlas atlas){
			greenDoor = atlas.findRegion("greenDoor");
		}
	}
	
	public class BlueDoor{
		public AtlasRegion blueDoor;
		
		public BlueDoor(TextureAtlas atlas){
			blueDoor = atlas.findRegion("blueDoor");
		}
	}
	
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts () {
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	public class Slime{
		public AtlasRegion slime;
		
		public Slime(TextureAtlas atlas){
			slime = atlas.findRegion("slime1");
		}
	}
	
	public class Thorn{
		public AtlasRegion thorn;
		
		public Thorn(TextureAtlas atlas){
			thorn = atlas.findRegion("thorn");
		}
	}
	
	public class Iron{
		public AtlasRegion iron;
		
		public Iron(TextureAtlas atlas){
			iron = atlas.findRegion("iron");
		}
	}
	
	public class Rock{
		public AtlasRegion rock;
		
		public Rock(TextureAtlas atlas){
			rock = atlas.findRegion("rock");
		}
	}
	
	public class Ground
	{
		public AtlasRegion ground;
		
		public Ground(TextureAtlas atlas)
		{
			ground = atlas.findRegion("ground");
		}
		
	}
	
	public class Mage
	{
		public AtlasRegion body;
		public final Animation animNormal;	
		public final Animation animWalking;
		public final Animation animAttacking;
		
		
		public Mage(TextureAtlas atlas){
			
			body = atlas.findRegion("mageN1");
			
			Array<AtlasRegion> regions = null;
			AtlasRegion region = null;

			// Animation: Mage Normal
			regions = atlas.findRegions("mageN1");
			animNormal = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			//Animation: Mage walking
			regions = atlas.findRegions("mage1");
			animWalking = new Animation(1.0f / 10.0f, regions,Animation.PlayMode.NORMAL);
			
			//Animation: Mage attacking
			regions = atlas.findRegions("mageA1");
			animAttacking = new Animation(1.0f / 10.0f, regions,Animation.PlayMode.NORMAL);
		}
	}
	
	public void init(AssetManager assetManager)
	{
		this.assetManager = assetManager;
		
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.load("music/Tearfalls and Pain.mp3", Music.class);
		assetManager.load("music/The Desolation Of Maroth.mp3", Music.class);
		assetManager.load("music/The Fuderation Of Lee Androws.mp3", Music.class);
		assetManager.load("music/item.wav", Sound.class);
		assetManager.load("music/jump.mp3", Sound.class);
		assetManager.load("music/Fire.wav", Sound.class);
		assetManager.load("music/Porta.mp3", Sound.class);
		assetManager.load("music/Ring.wav", Sound.class);
		assetManager.load("music/Damage.mp3", Sound.class);
		assetManager.load("music/somMenu.wav", Sound.class);
		assetManager.load("music/DeathMonster.wav", Sound.class);
		assetManager.load("music/Loss.wav", Sound.class);
		assetManager.load("music/Winning.wav", Sound.class);
		assetManager.finishLoading();
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		for (Texture t : atlas.getTextures())
		{
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		mage = new Mage(atlas);
		ground = new Ground(atlas);
		rock = new Rock(atlas);
		iron = new Iron(atlas);
		thorn = new Thorn(atlas);
		slime = new Slime(atlas);
		fonts = new AssetFonts();
		keyBlue = new KeyBlue(atlas);
		blueDoor = new BlueDoor(atlas);
		assetMusic = new AssetMusic(assetManager);
		assetSound = new AssetSounds(assetManager);
		keyGreen = new KeyGreen(atlas);
		keyYellow  = new KeyYellow(atlas);
		greenDoor = new GreenDoor(atlas);
		yellowDoor = new YellowDoor(atlas);
		energyBall = new EnergyBall(atlas);
		menuItens = new MenuItens(atlas);
		turtle = new Turtle(atlas);
		archer = new Archer(atlas);
		gate = new Gate(atlas);
		vulture = new Vulture(atlas);
	}
	
	
	@Override
	public void dispose()
	{
		assetManager.dispose();
	}
	
	
	private Assets()
	{}

}