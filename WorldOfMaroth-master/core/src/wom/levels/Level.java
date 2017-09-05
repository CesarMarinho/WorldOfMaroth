package wom.levels;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import wom.objects.Archer;
import wom.objects.BlueDoor;
import wom.objects.EnergyBall;
import wom.objects.GameObject;
import wom.objects.Gate;
import wom.objects.GreenDoor;
import wom.objects.Ground;
import wom.objects.Iron;
import wom.objects.KeyBlue;
import wom.objects.KeyGreen;
import wom.objects.KeyYellow;
import wom.objects.Mage;
import wom.objects.Mage.VIEW_DIRECTION;
import wom.objects.Rock;
import wom.objects.Slime;
import wom.objects.Thorn;
import wom.objects.Turtle;
import wom.objects.Vulture;
import wom.objects.YellowDoor;

public class Level {

	public enum BLOCK_TYPE {
		
		TURTLE(169, 8, 179),
		ARCHER(242, 3, 3),
		YELLOWKEY(127, 0, 0),
		GREENKEY(0, 127, 0),
		YELLOWDOOR(127, 100, 100),
		GREENDOOR(100, 127, 100),
		TELEPORTA(151, 230, 143),
		TELEPORTB(150, 200, 140),		
		EMPTY(0, 0, 0), // black
		GROUND(0, 0, 255), // green
		ROCK(196,88,88),//brown
		IRON(255,0,255),//pink
		SLIME(0, 255, 0),
		PLAYER_SPAWNPOINT(255, 255, 255), // white
		ITEM_FEATHER(255, 0, 255), // purple
		THORN(255, 255, 0), // yellow
		KEYBLUE(0, 0, 127),
		BLUEDOOR(100, 100,127),
		GATE(150, 200, 140),
		VULTURE(70,3, 3);
		private int color;

		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor (int color) {
			return this.color == color;
		}

		public int getColor () {
			return color;
		}
	}

	public Mage mage;
	public EnergyBall energyBall;
	public ArrayList<Ground> grounds;
	public ArrayList<Rock> rocks;
	public ArrayList<Iron> irons;
	public ArrayList<Thorn> thorns;
	public ArrayList<Slime> slimes;
	public ArrayList<KeyBlue> keyBlues;
	public ArrayList<BlueDoor> blueDoors;
	public ArrayList<KeyGreen> keysGreen;
	public ArrayList<KeyYellow> keyYellow;
	public ArrayList<YellowDoor> yellowDoors;
	public ArrayList<GreenDoor> greenDoors;
 	public ArrayList<Turtle> turtles;
 	public ArrayList<Archer> archers;
 	public ArrayList<Vulture> vultures;
	public Gate gate;
	public Level (String filename) {
		init(filename);
	}

	private void init (String filename) {
		mage = null;
		gate = new Gate();
		energyBall = new EnergyBall();
		blueDoors = new ArrayList<BlueDoor>();
		grounds = new ArrayList<Ground>();
		rocks = new ArrayList<Rock>();
		irons = new ArrayList<Iron>();
		thorns = new ArrayList<Thorn>();
		slimes = new ArrayList<Slime>();
		keyBlues = new ArrayList<KeyBlue>();
		keyYellow = new ArrayList<KeyYellow>();
		keysGreen = new ArrayList<KeyGreen>();
		yellowDoors = new  ArrayList<YellowDoor>() ;
	    greenDoors = new  ArrayList<GreenDoor>();
	    turtles = new ArrayList<Turtle>();
	    archers = new ArrayList<Archer>();
	    vultures = new ArrayList<Vulture>();
		
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
				int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				GameObject obj = null;				
				float baseHeight = pixmap.getHeight() - pixelY;
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
	
				//WHITE
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				}
				// player spawn point
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
					obj = new Mage();
					obj.position.set(pixelX, baseHeight * obj.dimension.y );
					mage = (Mage)obj;					
				}
				else if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) {
					obj = new Ground();
					obj.position.set(pixelX, baseHeight * obj.dimension.y);
					grounds.add((Ground)obj);

				}else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
					obj = new Rock();
					obj.position.set(pixelX, baseHeight * obj.dimension.y);
					rocks.add((Rock)obj);

				}else if (BLOCK_TYPE.IRON.sameColor(currentPixel)) {
					obj = new Iron();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y + 0.5f));
					irons.add((Iron)obj);

				}else if (BLOCK_TYPE.THORN.sameColor(currentPixel)) {
					obj = new Thorn();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y + 0.5f));
					thorns.add((Thorn)obj);
					
				}else if (BLOCK_TYPE.SLIME.sameColor(currentPixel)) {
					obj = new Slime();
					obj.position.set(pixelX, baseHeight * obj.dimension.y);
					slimes.add((Slime)obj);

				}else if (BLOCK_TYPE.KEYBLUE.sameColor(currentPixel)) {
					obj = new KeyBlue();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y +  0.5f ));
					keyBlues.add((KeyBlue)obj);

				}
				else if (BLOCK_TYPE.BLUEDOOR.sameColor(currentPixel)) {
					obj = new BlueDoor();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					blueDoors.add((BlueDoor)obj);
				}
				else if (BLOCK_TYPE.ARCHER.sameColor(currentPixel)) {
					obj = new Archer();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					archers.add((Archer)obj);
				
				}
				else if (BLOCK_TYPE.TURTLE.sameColor(currentPixel)) {
					obj = new Turtle();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					turtles.add((Turtle)obj);
				}
				
				else if (BLOCK_TYPE.YELLOWKEY.sameColor(currentPixel)) {

					obj = new KeyYellow();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y +  0.5f ));
					keyYellow.add((KeyYellow)obj);

					
				}
				
				else if (BLOCK_TYPE.VULTURE.sameColor(currentPixel)) {

					obj = new Vulture();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y +  0.5f ));
					vultures.add((Vulture)obj);

					
				}
				else
				if (BLOCK_TYPE.GREENKEY.sameColor(currentPixel)) {
					obj = new KeyGreen();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y + 0.5f ));
					keysGreen.add((KeyGreen)obj);	
					
				}
				else if (BLOCK_TYPE.YELLOWDOOR.sameColor(currentPixel)) {
					obj = new YellowDoor();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					yellowDoors.add((YellowDoor)obj);	
					
				}
				else 
				if (BLOCK_TYPE.GREENDOOR.sameColor(currentPixel)) {
				
					obj = new GreenDoor();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					greenDoors.add((GreenDoor)obj);	
					
				}
				else if (BLOCK_TYPE.GATE.sameColor(currentPixel)) {
					
					obj = new Gate();
					obj.position.set(pixelX, baseHeight * (obj.dimension.y  ));
					gate = (Gate)obj;
					
				}
					
				
				lastPixel = currentPixel;
			}
		}

		pixmap.dispose();
	}
	

	public void update (float deltaTime) {
		
		mage.update(deltaTime);		
		
		energyBall.update(deltaTime);
		
		for(Iron iron: irons)
		{
			iron.update(deltaTime);
		}
		
		for(Slime slime: slimes)
		{
			slime.update(deltaTime);
		}
		
		for(Archer archer: archers)
		{
			archer.update(deltaTime);
		}
		
		for(Vulture vulture: vultures)
		{
			vulture.update(deltaTime);
		}
		
	}	

	public void render (SpriteBatch batch) {
	
		mage.render(batch);
		
		if(energyBall.isAttacking)
			energyBall.render(batch);
		
		for(Ground ground: grounds)
		{
			ground.render(batch);
		}
		for(Rock rock: rocks)
		{
			rock.render(batch);
		}
		for(Iron iron: irons)
		{
			iron.render(batch);
		}
		for(Thorn thorn: thorns)
		{
			thorn.render(batch);
		}
		for(Slime slime: slimes)
		{
			slime.render(batch);
		}
		for(KeyBlue key: keyBlues)
		{
			key.render(batch);
		}
		
		for(KeyYellow key: keyYellow)
		{
			key.render(batch);
		}
		
		for(KeyGreen key: keysGreen)
		{
			key.render(batch);
		}
		
		for(BlueDoor bdoor: blueDoors)
		{
			bdoor.render(batch);
		}
		
		for(GreenDoor gdoor: greenDoors)
		{
			gdoor.render(batch);
		}
		
		for(YellowDoor ydoor: yellowDoors)
		{
			ydoor.render(batch);
		}
		
		for(Turtle turtle: turtles)
		{
			turtle.render(batch);
		}
		
		for(Archer archer: archers)
		{
			archer.setViewDirection(mage.position.x > archer.position.x?VIEW_DIRECTION.RIGHT: VIEW_DIRECTION.LEFT);
			archer.render(batch);

		}
		gate.render(batch);
		
		for(Vulture vulture: vultures)
		{
			vulture.render(batch);
		}
		
	}
}
