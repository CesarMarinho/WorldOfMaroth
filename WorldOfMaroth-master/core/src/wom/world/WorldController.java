package wom.world;


import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import wom.levels.Level;
import wom.objects.Archer;
import wom.objects.Arrow;
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
import wom.objects.Mage.JUMP_STATE;
import wom.objects.Rock;
import wom.objects.Slime;
import wom.objects.Thorn;
import wom.objects.Turtle;
import wom.objects.Vulture;
import wom.objects.YellowDoor;
import wom.screen.DirectedGame;
import wom.screen.FinishGameScreen;
import wom.screen.GameOverScreen;
import wom.screen.GamePlayScreen;
import wom.screen.MenuScreen;
import wom.screen.UpgradeScreen;
import wom.util.Achievements;
import wom.util.Assets;
import wom.util.AudioManager;
import wom.util.CameraHelper;
import wom.util.Constants;
import wow.transition.ScreenTransition;
import wow.transition.ScreenTransitionSlice;

public class WorldController extends InputAdapter
{
	public CameraHelper cameraHelper;
	private DirectedGame game;
	public Level level;
	public int score;
	public float scoreVisual;
	public int lives;
	public float livesVisual;
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	public int numberCoins = 0;
	public int numberDoorOpen = 0;
	public int numUpFly = 0;
	public int stage;

	
	public WorldController(WorldController worldController)
	{
		this.cameraHelper = worldController.cameraHelper;
		this.game = worldController.game;
		this.level = worldController.level;
		this.score = worldController.score;
		this.scoreVisual = worldController.scoreVisual;
		this.lives = worldController.lives;
		this.livesVisual = worldController.livesVisual;
		this.numberCoins = worldController.numberCoins;
		this.numUpFly  = worldController.numUpFly;
		this.numberDoorOpen = worldController.numberDoorOpen;
		stage = 1;
	}
	public WorldController(DirectedGame game)
	{
		this.game = game;
		level = new Level("levels/level1.png");
		cameraHelper = new CameraHelper();
		Gdx.input.setInputProcessor(this);
		score = 0;
		scoreVisual = score;
		lives = Constants.LIVES_START;
		cameraHelper.setTarget(level.mage);
		stage= 1;
	}

	public void update(float deltatime)
	{
		
		if(numberCoins >= 100000)
			Achievements.instance.getAchievements()[1] = true;
		
		if(this.numberDoorOpen >= 50)
			Achievements.instance.getAchievements()[2] = true;
		
		if(this.numUpFly >= 5)
			Achievements.instance.getAchievements()[3] = true;
		
		if(this.isGameOver())
		{
			AudioManager.instance.play(Assets.instance.assetSound.loss);
			ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
			game.setScreen(new GameOverScreen(game), transition);
		}
		
		handleInput(deltatime);
		level.update(deltatime);
		testCollisions();
		testCollisionSlime();
		testCollisionsVulture();

		if(level.energyBall.isAttacking)
			testCollisionEnergyBall();
		
		cameraHelper.	update(deltatime);
		
		
		if (livesVisual > lives)
			livesVisual = Math.max(lives, livesVisual - 1 * deltatime);
		
		if (scoreVisual < score)
			scoreVisual = Math.min(score, scoreVisual + 250 * deltatime);
		
		
	}
	
	
	public void testCollisionsVulture()
	{
		for(Vulture vulture: level.vultures)
		{
			
			if(vulture.isDead)
				continue;
			
			r1.set(vulture.position.x, vulture.position.y,
					vulture.bounds.width, vulture.bounds.height);
	
			
			if(vulture.timeColide < 0)
			{			
				for (Rock rock : level.rocks)
				{
					r2.set(rock.position.x, rock.position.y, rock.bounds.width,
							rock.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					if(vulture.position.y > rock.position.y + rock.bounds.height/2)
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > rock.position.x)
						vulture.position.x = rock.position.x + rock.bounds.width; 												
					else
						vulture.position.x = rock.position.x - vulture.bounds.width;

					
					break;		
				}
			}
			
			if(vulture.timeColide < 0)
			{
			
				for (Ground ground : level.grounds) 
				{
					r2.set(ground.position.x, ground.position.y, ground.bounds.width,
							ground.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					if(vulture.position.y > ground.position.y + ground.bounds.height/2)
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > ground.position.x)
						vulture.position.x = ground.position.x + ground.bounds.width; 												
					else
						vulture.position.x = ground.position.x - vulture.bounds.width;


				
					break;
					
				}
			}
			if(vulture.timeColide < 0)
			{
				for (Thorn thorn : level.thorns) 
				{
					r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
							thorn.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > thorn.position.x)
						vulture.position.x = thorn.position.x + thorn.bounds.width;
					else
						vulture.position.x = thorn.position.x - vulture.bounds.width;
					
					
					break;
				}
			}
			
			if(vulture.timeColide < 0)
			{
				for (Iron iron : level.irons)
				{
					if (iron.collected)
						continue;
					r2.set(iron.position.x, iron.position.y,
							iron.bounds.width, iron.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > iron.position.x)
						vulture.position.x = iron.position.x + iron.bounds.width;
					else
						vulture.position.x = iron.position.x - vulture.bounds.width;
					
					
					break;
				}
			}
			if(vulture.timeColide < 0)
			{
				for (GreenDoor door : level.greenDoors)
				{
						
						if (door.open)
							continue;
						r2.set(door.position.x, door.position.y,
								door.bounds.width, door.bounds.height);
						
						if (!r1.overlaps(r2))
							continue;
						
	
						vulture.changeWalk();
						vulture.timeWithoutColide();
						
						if(vulture.position.x > door.position.x)
							vulture.position.x = door.position.x + door.bounds.width;
						else
							vulture.position.x = door.position.x - vulture.bounds.width;
						
						break;
				}			
			}
			
			if(vulture.timeColide < 0)
			{
				for (YellowDoor door : level.yellowDoors)
				{
					if (door.open)
						continue;
					r2.set(door.position.x, door.position.y,
							door.bounds.width, door.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > door.position.x)
						vulture.position.x = door.position.x + door.bounds.width;
					else
						vulture.position.x = door.position.x - vulture.bounds.width;
					
					break;
				}
			}
			
			if(vulture.timeColide < 0)
			{
				for (BlueDoor blueDoor : level.blueDoors)
				{
					if (blueDoor.open)
						continue;
					r2.set(blueDoor.position.x, blueDoor.position.y,
							blueDoor.bounds.width, blueDoor.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > blueDoor.position.x)
						vulture.position.x = blueDoor.position.x + blueDoor.bounds.width;
					else
						vulture.position.x = blueDoor.position.x - vulture.bounds.width;
					
					break;
				}			
			}
			
			if(vulture.timeColide < 0)
			{	
				for (KeyBlue keyBlue : level.keyBlues)
				{
					if (keyBlue.collected)
						continue;
					r2.set(keyBlue.position.x, keyBlue.position.y,
							keyBlue.bounds.width, keyBlue.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > keyBlue.position.x)
						vulture.position.x = keyBlue.position.x +  keyBlue.bounds.width;
					else
						vulture.position.x =  keyBlue.position.x - vulture.bounds.width;
					
						
					break;
				}
			}
			
			if(vulture.timeColide < 0)
			{
				for (KeyYellow ykey : level.keyYellow)
				{
					if (ykey.collected)
						continue;
					r2.set(ykey.position.x, ykey.position.y,
							ykey.bounds.width, ykey.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					
					if(vulture.position.x > ykey.position.x)
						vulture.position.x = ykey.position.x + ykey.bounds.width;
					else
						vulture.position.x = ykey.position.x - vulture.bounds.width;
					
					
					break;
				}
			}
			
			if(vulture.timeColide < 0)
			{
				for (KeyGreen keyGreen : level.keysGreen)
				{
					if (keyGreen.collected)
						continue;
					r2.set(keyGreen.position.x, keyGreen.position.y,
							keyGreen.bounds.width, keyGreen.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					vulture.changeWalk();
					vulture.timeWithoutColide();
					if(vulture.position.x > keyGreen.position.x)
						vulture.position.x = keyGreen.position.x + keyGreen.bounds.width;
					else
						vulture.position.x = keyGreen.position.x - vulture.bounds.width;
					
				
					break;
				}
			}
		}		
	}
	
	public void testCollisionSlime()
	{	
		for(Slime slime: level.slimes)
		{
			
			if(slime.dead)
				continue;
			
			r1.set(slime.position.x, slime.position.y,
					slime.bounds.width, slime.bounds.height);
	
			
			if(slime.timeColide < 0)
			{			
				for (Rock rock : level.rocks)
				{
					r2.set(rock.position.x, rock.position.y, rock.bounds.width,
							rock.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					if(slime.position.y > rock.position.y + rock.bounds.height/2)
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > rock.position.x)
						slime.position.x = rock.position.x + rock.bounds.width; 												
					else
						slime.position.x = rock.position.x - slime.bounds.width;

					
					break;		
				}
			}
			
			if(slime.timeColide < 0)
			{
			
				for (Ground ground : level.grounds) 
				{
					r2.set(ground.position.x, ground.position.y, ground.bounds.width,
							ground.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					if(slime.position.y > ground.position.y + ground.bounds.height/2)
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > ground.position.x)
						slime.position.x = ground.position.x + ground.bounds.width; 												
					else
						slime.position.x = ground.position.x - slime.bounds.width;


				
					break;
					
				}
			}
			if(slime.timeColide < 0)
			{
				for (Thorn thorn : level.thorns) 
				{
					r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
							thorn.bounds.height);
					
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > thorn.position.x)
						slime.position.x = thorn.position.x + thorn.bounds.width;
					else
						slime.position.x = thorn.position.x - slime.bounds.width;
					
					
					break;
				}
			}
			
			if(slime.timeColide < 0)
			{
				for (Iron iron : level.irons)
				{
					if (iron.collected)
						continue;
					r2.set(iron.position.x, iron.position.y,
							iron.bounds.width, iron.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > iron.position.x)
						slime.position.x = iron.position.x + iron.bounds.width;
					else
						slime.position.x = iron.position.x - slime.bounds.width;
					
					
					break;
				}
			}
			if(slime.timeColide < 0)
			{
				for (GreenDoor door : level.greenDoors)
				{
						
						if (door.open)
							continue;
						r2.set(door.position.x, door.position.y,
								door.bounds.width, door.bounds.height);
						
						if (!r1.overlaps(r2))
							continue;
						
	
						slime.changeWalk();
						slime.timeWithoutColide();
						
						if(slime.position.x > door.position.x)
							slime.position.x = door.position.x + door.bounds.width;
						else
							slime.position.x = door.position.x - slime.bounds.width;
						
						break;
				}			
			}
			
			if(slime.timeColide < 0)
			{
				for (YellowDoor door : level.yellowDoors)
				{
					if (door.open)
						continue;
					r2.set(door.position.x, door.position.y,
							door.bounds.width, door.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > door.position.x)
						slime.position.x = door.position.x + door.bounds.width;
					else
						slime.position.x = door.position.x - slime.bounds.width;
					
					break;
				}
			}
			
			if(slime.timeColide < 0)
			{
				for (BlueDoor blueDoor : level.blueDoors)
				{
					if (blueDoor.open)
						continue;
					r2.set(blueDoor.position.x, blueDoor.position.y,
							blueDoor.bounds.width, blueDoor.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > blueDoor.position.x)
						slime.position.x = blueDoor.position.x + blueDoor.bounds.width;
					else
						slime.position.x = blueDoor.position.x - slime.bounds.width;
					
					break;
				}			
			}
			
			if(slime.timeColide < 0)
			{	
				for (KeyBlue keyBlue : level.keyBlues)
				{
					if (keyBlue.collected)
						continue;
					r2.set(keyBlue.position.x, keyBlue.position.y,
							keyBlue.bounds.width, keyBlue.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > keyBlue.position.x)
						slime.position.x = keyBlue.position.x +  keyBlue.bounds.width;
					else
						slime.position.x =  keyBlue.position.x - slime.bounds.width;
					
						
					break;
				}
			}
			
			if(slime.timeColide < 0)
			{
				for (KeyYellow ykey : level.keyYellow)
				{
					if (ykey.collected)
						continue;
					r2.set(ykey.position.x, ykey.position.y,
							ykey.bounds.width, ykey.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > ykey.position.x)
						slime.position.x = ykey.position.x + ykey.bounds.width;
					else
						slime.position.x = ykey.position.x - slime.bounds.width;
					
					
					break;
				}
			}
			
			if(slime.timeColide < 0)
			{
				for (KeyGreen keyGreen : level.keysGreen)
				{
					if (keyGreen.collected)
						continue;
					r2.set(keyGreen.position.x, keyGreen.position.y,
							keyGreen.bounds.width, keyGreen.bounds.height);
					if (!r1.overlaps(r2))
						continue;
					
					slime.changeWalk();
					slime.timeWithoutColide();
					if(slime.position.x > keyGreen.position.x)
						slime.position.x = keyGreen.position.x + keyGreen.bounds.width;
					else
						slime.position.x = keyGreen.position.x - slime.bounds.width;
					
				
					break;
				}
			}
			
			if(slime.timeColide < 0)
			{
				Gate gate = level.gate;
	
				r2.set(gate.position.x, gate.position.y,
				gate.bounds.width, gate.bounds.height);
				
				if (r1.overlaps(r2))
				{		
					
					slime.changeWalk();
					slime.timeWithoutColide();
					
					if(slime.position.x > gate.position.x)
						slime.position.x = gate.position.x + gate.bounds.width;
					else
						slime.position.x = gate.position.x - slime.bounds.width;
					
				}
			}
		}
			
	}		
	
	public void testCollisionEnergyBall()
	{
		
		r1.set(level.energyBall.position.x, level.energyBall.position.y,
				level.energyBall.bounds.width, level.energyBall.bounds.height);

		
		for (Vulture vulture : level.vultures) 
		{
			r2.set(vulture.position.x, vulture.position.y, vulture.bounds.width,
					vulture.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onColide(vulture);
			
		}
		
		
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onColide(rock);
			
		}
		
		for (Archer archer : level.archers) 
		{
			r2.set(archer.position.x, archer.position.y, archer.bounds.width,
					archer.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onColide(archer);
			break;
			
		}
		
		
		for(Turtle turtle: level.turtles)
		{
			r2.set(turtle.position.x, turtle.position.y, turtle.bounds.width,
					turtle.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onColide(turtle);
			
			
		}
		
		
		for (Ground ground : level.grounds) 
		{
			r2.set(ground.position.x, ground.position.y, ground.bounds.width,
					ground.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			onColide(ground);
			
		}
		
		for (Thorn thorn : level.thorns) 
		{
			r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
					thorn.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			if(!level.mage.isDamage)
				onColide(thorn);
			
		}
		
		for (Iron iron : level.irons)
		{
			if (iron.collected)
				continue;
			r2.set(iron.position.x, iron.position.y,
					iron.bounds.width, iron.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(iron);
			break;
		}
		
		for (BlueDoor blueDoor : level.blueDoors)
		{
			if (blueDoor.open)
				continue;
			r2.set(blueDoor.position.x, blueDoor.position.y,
					blueDoor.bounds.width, blueDoor.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(blueDoor);
			break;
		}
		
		for (GreenDoor door : level.greenDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(door);
			break;
		}
		
		for (YellowDoor door : level.yellowDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(door);
			break;
		}
		
		
		for (KeyBlue keyBlue : level.keyBlues)
		{
			if (keyBlue.collected)
				continue;
			r2.set(keyBlue.position.x, keyBlue.position.y,
					keyBlue.bounds.width, keyBlue.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(keyBlue);
			break;
		}
		
		for (KeyYellow ykey : level.keyYellow)
		{
			if (ykey.collected)
				continue;
			r2.set(ykey.position.x, ykey.position.y,
					ykey.bounds.width, ykey.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(ykey);
			break;
		}
		
		for (KeyGreen keyGreen : level.keysGreen)
		{
			if (keyGreen.collected)
				continue;
			r2.set(keyGreen.position.x, keyGreen.position.y,
					keyGreen.bounds.width, keyGreen.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(keyGreen);
			break;
		}
		
		for (Slime slime : level.slimes)
		{
			if(slime.dead)
				continue;
			
			r2.set(slime.position.x, slime.position.y,
			slime.bounds.width, slime.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onColide(slime);
			
			break;
			
			}
		
	}
	
	
	public void onColide(GameObject gameObject)
	{
		
		if(  gameObject instanceof Iron)
		{
		
			
			((Iron)gameObject).collected = true;
		}
		if(  gameObject instanceof Archer)
		{
		
			
			((Archer)gameObject).isDead = true;
		}
		else
			if(  gameObject instanceof Vulture)
			{
			
				
				((Vulture)gameObject).isDead = true;
			}
		else
		if(  gameObject instanceof Turtle)
		{
			AudioManager.instance.play(Assets.instance.assetSound.damage);
				--lives;
				level.mage.setDamaged(true);
		}
		else
		if(  gameObject instanceof KeyBlue)
		{
			((KeyBlue)gameObject).collected = true;
			
		}
		else
		if(  gameObject instanceof KeyYellow)
		{
			((KeyYellow)gameObject).collected = true;
			
		}
		else
		if(  gameObject instanceof KeyGreen)
		{
			((KeyGreen)gameObject).collected = true;

		}
		else
		if(  gameObject instanceof Slime)
		{
			AudioManager.instance.play(Assets.instance.assetSound.deathMonster);
			((Slime)gameObject).dead = true;

		}
		level.energyBall.isAttacking = false;
		level.energyBall.setTimeAtack(-1);
		
	}
	
	public void testCollisionPowerEnemy(Vulture vulture)
	{
		Rectangle r1 = new Rectangle();
		
		
		r1.set(vulture.verticalPower.position.x, vulture.verticalPower.position.y,
				vulture.verticalPower.bounds.width, vulture.verticalPower.bounds.height);
		
				
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			vulture.timeAttack = -1;
			
		}
		
		
		for(Turtle turtle: level.turtles)
		{
			r2.set(turtle.position.x, turtle.position.y, turtle.bounds.width,
					turtle.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			vulture.timeAttack = -1;
			
			
		}
		
		
		for (Ground ground : level.grounds) 
		{
			r2.set(ground.position.x, ground.position.y, ground.bounds.width,
					ground.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			vulture.timeAttack = -1;
			
		}
		
		for (Thorn thorn : level.thorns) 
		{
			r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
					thorn.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			vulture.timeAttack = -1;
		}
		
		for (Iron iron : level.irons)
		{
			if (iron.collected)
				continue;
			r2.set(iron.position.x, iron.position.y,
					iron.bounds.width, iron.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			
			vulture.timeAttack = -1;
			break;
		}
		
		for (BlueDoor blueDoor : level.blueDoors)
		{
			if (blueDoor.open)
				continue;
			r2.set(blueDoor.position.x, blueDoor.position.y,
					blueDoor.bounds.width, blueDoor.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		for (GreenDoor door : level.greenDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		for (YellowDoor door : level.yellowDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		
		for (KeyBlue keyBlue : level.keyBlues)
		{
			if (keyBlue.collected)
				continue;
			r2.set(keyBlue.position.x, keyBlue.position.y,
					keyBlue.bounds.width, keyBlue.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		for (KeyYellow ykey : level.keyYellow)
		{
			if (ykey.collected)
				continue;
			r2.set(ykey.position.x, ykey.position.y,
					ykey.bounds.width, ykey.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		for (KeyGreen keyGreen : level.keysGreen)
		{
			if (keyGreen.collected)
				continue;
			r2.set(keyGreen.position.x, keyGreen.position.y,
					keyGreen.bounds.width, keyGreen.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			break;
		}
		
		for (Slime slime : level.slimes)
		{
			if(slime.dead)
				continue;
			
			r2.set(slime.position.x, slime.position.y,
			slime.bounds.width, slime.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			vulture.timeAttack = -1;
			
			break;
			
			}
	}
	
	public void testCollisionArrowEnemy(Archer archer)
	{
		Rectangle r1 = new Rectangle();
		
		
		r1.set(archer.arrow.position.x, archer.arrow.position.y,
				archer.arrow.bounds.width, archer.arrow.bounds.height);
		
				
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			archer.timeAttack = -1;
			
		}
		
		
		for(Turtle turtle: level.turtles)
		{
			r2.set(turtle.position.x, turtle.position.y, turtle.bounds.width,
					turtle.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			archer.timeAttack = -1;
			
			
		}
		
		
		for (Ground ground : level.grounds) 
		{
			r2.set(ground.position.x, ground.position.y, ground.bounds.width,
					ground.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			archer.timeAttack = -1;
			
		}
		
		for (Thorn thorn : level.thorns) 
		{
			r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
					thorn.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			archer.timeAttack = -1;
		}
		
		for (Iron iron : level.irons)
		{
			if (iron.collected)
				continue;
			r2.set(iron.position.x, iron.position.y,
					iron.bounds.width, iron.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			
			archer.timeAttack = -1;
			break;
		}
		
		for (BlueDoor blueDoor : level.blueDoors)
		{
			if (blueDoor.open)
				continue;
			r2.set(blueDoor.position.x, blueDoor.position.y,
					blueDoor.bounds.width, blueDoor.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		for (GreenDoor door : level.greenDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		for (YellowDoor door : level.yellowDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		
		for (KeyBlue keyBlue : level.keyBlues)
		{
			if (keyBlue.collected)
				continue;
			r2.set(keyBlue.position.x, keyBlue.position.y,
					keyBlue.bounds.width, keyBlue.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		for (KeyYellow ykey : level.keyYellow)
		{
			if (ykey.collected)
				continue;
			r2.set(ykey.position.x, ykey.position.y,
					ykey.bounds.width, ykey.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		for (KeyGreen keyGreen : level.keysGreen)
		{
			if (keyGreen.collected)
				continue;
			r2.set(keyGreen.position.x, keyGreen.position.y,
					keyGreen.bounds.width, keyGreen.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			break;
		}
		
		for (Slime slime : level.slimes)
		{
			if(slime.dead)
				continue;
			
			r2.set(slime.position.x, slime.position.y,
			slime.bounds.width, slime.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			archer.timeAttack = -1;
			
			break;
			
			}
	}
	
	private void testCollisions() {
		
		r1.set(level.mage.position.x, level.mage.position.y,
				level.mage.bounds.width, level.mage.bounds.height);

		
		for(Vulture vulture: level.vultures)
		{
			if(!vulture.isDead)
			{
				testCollisionPowerEnemy(vulture);
				
				r2.set(vulture.verticalPower.position.x, vulture.verticalPower.position.y, vulture.verticalPower.bounds.width,
						vulture.verticalPower.bounds.height);
				
				if(r1.overlaps(r2) && !level.mage.isDamage)
				{
					damage();
					break;
				}
				
				r2.set(vulture.position.x, vulture.position.y, vulture.bounds.width,
						vulture.bounds.height);
				
				
				if (!r1.overlaps(r2))
					continue;
				
				if(!level.mage.isDamage)
					damage();
				}
		}
		
		for (Archer archer : level.archers) 
		{
			
			if(!archer.isDead)
			{
				testCollisionArrowEnemy(archer);
			
			
				r2.set(archer.arrow.position.x, archer.arrow.position.y, archer.arrow.bounds.width,
						archer.arrow.bounds.height);
				
				
				if(r1.overlaps(r2) && !level.mage.isDamage)
				{
					damage();
					break;
				}
				
				r2.set(archer.position.x, archer.position.y, archer.bounds.width,
						archer.bounds.height);
				
				if (!r1.overlaps(r2))
					continue;
				
				if(!level.mage.isDamage)
				{
					damage();
				}
				
				break;
			}
		}
		
		for (Rock rock : level.rocks) 
		{
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onCollisionWithRock(rock);
			
		}
		
		for (Ground ground : level.grounds) 
		{
			r2.set(ground.position.x, ground.position.y, ground.bounds.width,
					ground.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithCharacter(ground);
			
		}
		
		for (Thorn thorn : level.thorns) 
		{
			r2.set(thorn.position.x, thorn.position.y, thorn.bounds.width,
					thorn.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			if(!level.mage.isDamage)
				damage();
			
		}
		
		for (Iron iron : level.irons)
		{
			if (iron.collected)
				continue;
			r2.set(iron.position.x, iron.position.y,
					iron.bounds.width, iron.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithIron(iron);
			break;
		}
		
		for (BlueDoor blueDoor : level.blueDoors)
		{
			if (blueDoor.open)
				continue;
			r2.set(blueDoor.position.x, blueDoor.position.y,
					blueDoor.bounds.width, blueDoor.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithBlueDoor(blueDoor);
			break;
		}
		
		for (GreenDoor door : level.greenDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithGreenDoor(door);
			break;
		}
		
		for (YellowDoor door : level.yellowDoors)
		{
			if (door.open)
				continue;
			r2.set(door.position.x, door.position.y,
					door.bounds.width, door.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithYellowDoor(door);
			break;
		}
		
		for (KeyBlue keyBlue : level.keyBlues)
		{
			if (keyBlue.collected)
				continue;
			r2.set(keyBlue.position.x, keyBlue.position.y,
					keyBlue.bounds.width, keyBlue.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithKeyBlue(keyBlue);
			break;
		}
		
		for (KeyYellow ykey : level.keyYellow)
		{
			if (ykey.collected)
				continue;
			r2.set(ykey.position.x, ykey.position.y,
					ykey.bounds.width, ykey.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithKeyYellow(ykey);
			break;
		}
		
		for (KeyGreen keyGreen : level.keysGreen)
		{
			if (keyGreen.collected)
				continue;
			r2.set(keyGreen.position.x, keyGreen.position.y,
					keyGreen.bounds.width, keyGreen.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithKeyGreen(keyGreen);
			break;
		}
		
		for (Slime slime : level.slimes)
		{
			
			if(slime.dead)
				continue;
			
			r2.set(slime.position.x, slime.position.y,
					slime.bounds.width, slime.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			
			if(!level.mage.isDamage)
				damage();
			break;
		}
		
		for(Turtle turtle: level.turtles)
		{
			
			r2.set(turtle.position.x, turtle.position.y,
					turtle.bounds.width, turtle.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			
			if(!level.mage.isDamage)
				damage();
			break;
			
		}
		
		
		
		r2.set(level.gate.position.x, level.gate.position.y,
				level.gate.bounds.width, level.gate.bounds.height);
		
		if (r1.overlaps(r2))
			this.onColisionWithGate(level.gate);
		
		
	}
	
	
	public void damage()
	{
		AudioManager.instance.play(Assets.instance.assetSound.damage);
		lives--;
		level.mage.setDamaged(true);
	}
	

	public void onCollisionWithKeyYellow(KeyYellow ykey)
	{
		AudioManager.instance.play(Assets.instance.assetSound.getKey);
		ykey.collected = true;
		level.mage.addyKeys();
	}
	
	public void onColisionWithGate(Gate gate)
	{
	
		
		
		switch(stage)
		{
		
		case 1:
			
			level = new Level("levels/level2.png");
			cameraHelper = new CameraHelper();
			Gdx.input.setInputProcessor(this);
			cameraHelper.setTarget(level.mage);
			level.mage.bKeys = 0;
			level.mage.yKeys= 0;
			level.mage.gKeys = 0;
			
			++stage;
			
			break;
		
		case 2:
			level = new Level("levels/level3.png");
			cameraHelper = new CameraHelper();
			Gdx.input.setInputProcessor(this);
			cameraHelper.setTarget(level.mage);
			++stage;
			level.mage.bKeys = 0;
			level.mage.yKeys= 0;
			level.mage.gKeys = 0;
			
			
			break;
			
		case 3:
			Achievements.instance.getAchievements()[0] = true;
			stage = 0;
			level.mage.bKeys = 0;
			level.mage.yKeys= 0;
			level.mage.gKeys = 0;
			ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
			game.setScreen(new FinishGameScreen(game, this), transition );
			
		}
	}
	
	public void onCollisionWithKeyGreen(KeyGreen gkey)
	{
		AudioManager.instance.play(Assets.instance.assetSound.getKey);
		gkey.collected = true;
		level.mage.addgKeys();
	}
	
	public void onCollisionWithYellowDoor(YellowDoor door)
	{
		
		if(level.mage.hasYellowKeys())
		{
			this.numberDoorOpen += 1;
			AudioManager.instance.play(Assets.instance.assetSound.door);
			door.open = true;
			level.mage.removeyKeys();
		}
		else
		{
			if(level.mage.position.x> door.position.x)
				level.mage.position.x = door.position.x + door.bounds.width;
	
			else
				level.mage.position.x = door.position.x - level.mage.bounds.width;			
			
		}
		
	}
	
	public void onCollisionWithGreenDoor(GreenDoor door)
	{
		if(level.mage.hasGreenKeys())
		{
			this.numberDoorOpen += 1;
			AudioManager.instance.play(Assets.instance.assetSound.door);
			door.open = true;
			level.mage.removegKeys();
		}
		else
		{
			
			if(level.mage.position.x> door.position.x)
				level.mage.position.x = door.position.x + door.bounds.width;
	
			else
				level.mage.position.x = door.position.x - level.mage.bounds.width;			
			
		}
		
	}
	
	public void onCollisionWithBlueDoor(BlueDoor door)
	{
		if(level.mage.hasBlueKeys())
		{
			this.numberDoorOpen += 1;
			AudioManager.instance.play(Assets.instance.assetSound.door);
			door.open = true;
			level.mage.removebKeys();
		}
		else
		{
			if(level.mage.position.x> door.position.x)
				level.mage.position.x = door.position.x + door.bounds.width;
	
			else
				level.mage.position.x = door.position.x - level.mage.bounds.width;			
			
		}
		
	}

	private void onCollisionWithKeyBlue(KeyBlue keyBlue)
	{
		AudioManager.instance.play(Assets.instance.assetSound.getKey);
		keyBlue.collected = true;
		level.mage.addbKeys();
		
	}
	
	
	private void onCollisionWithIron(Iron iron){
		AudioManager.instance.play(Assets.instance.assetSound.getItem);
		iron.collected = true;
		score += iron.getScore();
		numberCoins += iron.getScore();
	}
	
	private void onCollisionWithRock(Rock rock){
		
		
		Mage mage = level.mage;
		
		if(mage.position.y < rock.position.y - mage.bounds.height +0.1)
		{
			mage.position.y = rock.position.y - mage.bounds.height;
			return;
		}
		float heightDifference = Math.abs(mage.position.y - (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.08f) {
			boolean hitRightEdge  = mage.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitRightEdge ) {
				mage.position.x = rock.position.x + rock.bounds.width;
			} else {
				mage.position.x = rock.position.x - mage.bounds.width;
			}
			return;
		}
		
		switch (mage.jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			mage.position.y = rock.position.y + mage.bounds.height ;
			mage.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:	
			mage.position.y = rock.position.y + mage.bounds.height;
			break;
		}		
	}
	
	private void onCollisionWithCharacter(GameObject rock) 
	{
		Mage mage = level.mage;
		
		if(mage.position.y < rock.position.y - mage.bounds.height + 0.1 )
		{
			mage.position.y = rock.position.y - mage.bounds.height;
			return;
		}
		float heightDifference = Math.abs(mage.position.y - (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.08f) {
			boolean hitRightEdge  = mage.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitRightEdge ) {
				mage.position.x = rock.position.x + rock.bounds.width;
			} else {
				mage.position.x = rock.position.x - mage.bounds.width;
			}
			return;
		}
		
		switch (mage.jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			mage.position.y = rock.position.y + mage.bounds.height ;
			mage.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			mage.position.y = rock.position.y + mage.bounds.height;
			break;
		}
	}
	
	 public void handleInput(float deltaTime)
	 {
			if (cameraHelper.hasTarget(level.mage)) {
				
				if (Gdx.input.isKeyPressed(Keys.LEFT)) {
					level.mage.velocity.x = -level.mage.terminalVelocity.x;
					level.mage.setWalking(Gdx.input.isKeyPressed(Keys.LEFT));
				} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
					level.mage.velocity.x = level.mage.terminalVelocity.x;
					level.mage.setWalking(Gdx.input.isKeyPressed(Keys.RIGHT));
				}
				if ( Gdx.input.isKeyPressed(Keys.SPACE))
				{	
					level.mage.setJumping(true);
				}else
					level.mage.setJumping(false);
				
				if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT) )
					level.mage.setWalking(true);
				else level.mage.setWalking(false);
				
				if ( Gdx.input.isKeyPressed(Keys.CONTROL_LEFT ) && level.energyBall.isAttacking == false && level.mage.cooldown < 0){
					AudioManager.instance.play(Assets.instance.assetSound.magia);
					level.mage.setAttacking(true);
					level.mage.cooldown = 1f;
					level.energyBall = new EnergyBall();
					level.energyBall.setTimeAtack(level.energyBall.MAX_TIME);
					level.energyBall.setPosition(level.mage.position.x, level.mage.position.y);
					level.energyBall.setAttacking(true);
					level.energyBall.setDirection(level.mage.viewDirection);
				}else {
					level.mage.setAttacking(false);
				}
				if(Gdx.input.isKeyPressed(Keys.U)){
					ScreenTransition transition = ScreenTransitionSlice.init(2, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow5Out);
					game.setScreen(new UpgradeScreen(game, this), transition);
				}
				
			}
	 }
	 
	 public boolean isGameOver() {
			return lives < 0;
		}
	
	@Override
	public boolean keyUp(int key){
		
		return false;
		
	}
	
	
}
