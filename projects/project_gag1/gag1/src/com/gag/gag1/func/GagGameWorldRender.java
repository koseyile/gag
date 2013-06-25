package com.gag.gag1.func;

import com.badlogicgames.superjumper.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogicgames.superjumper.Assets;
import com.gag.gag1.GagGame;
import com.gag.gag1.GagGameAssets;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameScreen;
import com.gag.gag1.GagWorld;
import com.gag.gag1.func.GagGameRender.FontLayout_X;
import com.gag.gag1.func.GagGameRender.FontLayout_Y;
import com.gag.gag1.struct.GagGameBox;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGamePlayer;
import com.gag.gag1.struct.GagGameTreasure;

public class GagGameWorldRender {
	
	public static void Draw()
	{
		GagWorld gagWorld = GagGameScreen.m_GagWorld;
		if(gagWorld==null)
		{
			return;
		}
		
		DrawBackGround(gagWorld);
		
		int len = gagWorld.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = gagWorld.m_Objects.get(i);
			
			switch( object.objectType )
			{
				case ObjectType_Ojbect:
					{
						
					}
					break;
				case ObjectType_Plaform:
					{
						DrawPlatform((GagGamePlatform)object);
					}
					break;
				case ObjectType_Door:
					{
						DrawDoor((GagGameDoor)object);
					}
					break;
				case ObjectType_Treasure:
					{
						GagGameTreasure treasure = (GagGameTreasure)object;
						if( treasure.isInWorld )
						{
							DrawTreasure(treasure);
						}
					}
					break;
				case ObjectType_Box:
					{
						DrawBox((GagGameBox)object);
					}
					break;
			}

		}
		
		DrawPlayer();
	}
	
	public static void DrawUI()
	{
		GagWorld gagWorld = GagGameScreen.m_GagWorld;
		if(gagWorld==null)
		{
			return;
		}
		
		//Draw UI of bottom
		{
			GagGameRender.batcher.setColor(0.2f, 0.2f, 0.2f, 1f);
			GagGameRender.batcher.draw(Assets.testTex, 0, 0, GagGameRender.guiCam.viewportWidth, GagGameConfig.GameBottomUIHeight);
			GagGameRender.batcher.setColor(1, 1, 1, 1);
		}
		
		int len = gagWorld.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = gagWorld.m_Objects.get(i);
			
			switch( object.objectType )
			{
				case ObjectType_Treasure:
					{
						GagGameTreasure treasure = (GagGameTreasure)object;
						if( !treasure.isInWorld )
						{
							DrawTreasure(treasure);
						}
					}
					break;
			}
		}
		
		switch( gagWorld.m_WorldState )
		{
			case WorldState_FadeIn:
				{
					GagGameRender.batcher.setColor(0, 0, 0, 1f-(gagWorld.m_FadeInTime/GagGameConfig.FadeInTime));
					GagGameRender.batcher.draw(Assets.testTex, 0, 0, GagGameRender.guiCam.viewportWidth, GagGameRender.guiCam.viewportHeight);
					GagGameRender.batcher.setColor(1, 1, 1, 1);
				}
				break;
			case WorldState_FadeOut:
				{
					GagGameRender.batcher.setColor(0, 0, 0, (gagWorld.m_FadeOutTime/GagGameConfig.FadeInTime));
					GagGameRender.batcher.draw(Assets.testTex, 0, 0, GagGameRender.guiCam.viewportWidth, GagGameRender.guiCam.viewportHeight);
					GagGameRender.batcher.setColor(1, 1, 1, 1);
				}
				break;
		}

		//Draw UI of top
		{
			GagGameRender.batcher.setColor(0, 0, 0, 1);
			GagGameRender.batcher.draw(Assets.testTex, 0, GagGameRender.guiCam.viewportHeight-GagGameConfig.GameTopUIHeight, GagGameRender.guiCam.viewportWidth, GagGameConfig.GameTopUIHeight);
			GagGameRender.batcher.setColor(1, 1, 1, 1);
		}


		//if( GagGameConfig.ShowFps && gagWorld.m_Editor.isEnable )
		if( GagGameConfig.ShowFps )
		{
			GagGameRender.DrawString("FPS:"+Gdx.graphics.getFramesPerSecond(), 0.5f, 0.5f, FontLayout_X.FontLayout_X_Right, FontLayout_Y.FontLayout_Y_Up);
		}
		
		if( gagWorld.m_Editor.topString!=null )
		{
			GagGameRender.DrawString(gagWorld.m_Editor.topString_x, gagWorld.m_Editor.topString_y, gagWorld.m_Editor.topString, 1.0f, 1.0f);
		}
	}
	
	public static void DrawBackGround(GagWorld world)
	{
		GagGameRender.batcher.draw(Assets.backgroundRegion, world.worldBound.x, world.worldBound.y, world.worldBound.width, world.worldBound.height); 
	}
	
	public static void DrawPlayer()
	{
		GagWorld gagWorld = GagGameScreen.m_GagWorld;
		GagGamePlayer player = gagWorld.m_Player;
	
		Animation animation = Assets.bobFall;
		switch( player.CurMoveState )
		{
			case MoveState_Stand:
				{
					animation = Assets.bobFall;
				}
				break;
			case MoveState_Left:
				{
					animation = Assets.bobJump;
				}
				break;
			case MoveState_Right:
				{
					animation = Assets.bobJump;
				}
				break;
		}
		
		TextureRegion keyFrame = animation.getKeyFrame(player.PassTime, Animation.ANIMATION_LOOPING);
		
		if( gagWorld.isGameOver )
		{
			keyFrame = Assets.bobHit;
		}
		
		boolean ReverseY = gagWorld.m_g < 0f ? false : true;
		GagGameRender.DrawTexByCenter(keyFrame, player.postion.x, player.postion.y, player.bounds.width, player.bounds.height, player.CurReverseX, ReverseY);
		
		//GagGameRender.DrawTexByCenter(Assets.testTexRegion, player.postion.x, player.postion.y, player.bounds.width, player.bounds.height, player.CurReverseX, ReverseY);
		
	}
	
	public static void DrawPlatform(GagGamePlatform platform)
	{
		TextureRegion keyFrame = Assets.platform;
		GagGameRender.DrawTexByCenter(keyFrame, platform.postion.x, platform.postion.y, platform.bounds.width, platform.bounds.height, false, false);
		
//		Texture keyFrame = Assets.testTex;
//		GagGameRender.DrawTexByCenter(keyFrame, platform.postion.x, platform.postion.y, platform.bounds.width, platform.bounds.height, false, false);
	}
	
	public static void DrawDoor(GagGameDoor door)
	{
		TextureRegion keyFrame = Assets.castle;
		GagGameRender.DrawTexByCenter(keyFrame, door.postion.x, door.postion.y, door.bounds.width, door.bounds.height, false, false);

//		Texture keyFrame = Assets.testTex;
//		GagGameRender.DrawTexByCenter(keyFrame, door.postion.x, door.postion.y, door.bounds.width, door.bounds.height, false, false);
	}
	
	public static void DrawBox(GagGameBox box)
	{
		Texture keyFrame = GagGameAssets.boxTex;
		GagGameRender.DrawTexByCenter(keyFrame, box.postion.x, box.postion.y, box.bounds.width, box.bounds.height, false, false);
	}
	
	public static void DrawTreasure(GagGameTreasure treasure)
	{
		if( !treasure.isShow )
		{
			return;
		}
		
		Texture keyFrame = GagGameAssets.treasureTex;
		
		if( treasure.isPickUp )
		{
			switch( treasure.treasureType )
			{
				case TreasureType_None:
					{
						
					}
					break;
				case TreasureType_AthwartWorld:
					{
						keyFrame = GagGameAssets.athwartWorldTex;
					}
					break;
				case TreasureType_Umbrella:
					{
						keyFrame = GagGameAssets.umbrellaTex;
					}
					break;
				case TreasureType_KillMe:
					{
						keyFrame = GagGameAssets.killMeTex;
					}
					break;
				case TreasureType_Speed:
					{
						keyFrame = GagGameAssets.speedTex;
					}
					break;
				case TreasureType_GoRight:
					{
						keyFrame = GagGameAssets.goRightTex;
					}
					break;					
				case TreasureType_GoLeft:
					{
						keyFrame = GagGameAssets.goLeftTex;
					}
					break;					
				case TreasureType_NextPage:
					{
						keyFrame = GagGameAssets.nextPageTex;
					}
					break;
				case TreasureType_PrePage:
					{
						keyFrame = GagGameAssets.prePageTex;
					}
					break;					
				case TreasureType_NewScene:
					{
						keyFrame = GagGameAssets.newSceneTex;
					}
					break;
				case TreasureType_SaveScene:
					{
						keyFrame = GagGameAssets.saveSceneTex;
					}
					break;
				case TreasureType_SaveSceneAs:
					{
						keyFrame = GagGameAssets.saveSceneAsTex;
					}
					break;
				case TreasureType_OpenScene:
					{
						keyFrame = GagGameAssets.openSceneTex;
					}
					break;
				case TreasureType_RunScene:
					{
						keyFrame = GagGameAssets.runSceneTex;
					}
					break;
				case TreasureType_StopScene:
					{
						keyFrame = GagGameAssets.stopSceneTex;
					}
					break;
			}
		}
		
		GagGameRender.DrawTexByCenter(keyFrame, treasure.postion.x, treasure.postion.y, treasure.bounds.width, treasure.bounds.height, false, false);
		
		if( treasure.enable==false )
		{
			GagGameRender.DrawTexByCenter(GagGameAssets.disableTex, treasure.postion.x, treasure.postion.y, treasure.bounds.width, treasure.bounds.height/8, false, false);
		}
	}
}
