package com.gag.gag1.func;

import com.badlogicgames.superjumper.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogicgames.superjumper.Assets;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameScreen;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameWorldRender {
	
	public static void Draw()
	{
		GagWorld gagWorld = GagGameScreen.m_GagWorld;
		if(gagWorld==null)
		{
			return;
		}
		
		DrawBackGround();
		
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
			}

		}
		
		DrawPlayer();
	
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
	}
	
	public static void DrawBackGround()
	{
		GagGameRender.batcher.draw(Assets.backgroundRegion, 0, 0, GagGameRender.guiCam.viewportWidth, GagGameRender.guiCam.viewportHeight);
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
		boolean ReverseY = gagWorld.m_g < 0f ? false : true;
		GagGameRender.DrawTexByCenter(keyFrame, player.postion.x, player.postion.y, player.bounds.width, player.bounds.height, player.CurReverseX, ReverseY);
		
		//GagGameRender.DrawTexByCenter(Assets.testTexRegion, player.postion.x, player.postion.y, player.bounds.width, player.bounds.height, player.CurReverseX, ReverseY);
		
	}
	
	public static void DrawPlatform(GagGamePlatform platform)
	{
		TextureRegion keyFrame = Assets.platform;
		GagGameRender.DrawTexByCenter(keyFrame, platform.postion.x, platform.postion.y, platform.bounds.width, platform.bounds.height, false, false);
		
		//Texture keyFrame = Assets.testTex;
		//GagGameRender.DrawTexByCenter(keyFrame, platform.postion.x, platform.postion.y, platform.bounds.width, platform.bounds.height, false, false);
	}
	
	public static void DrawDoor(GagGameDoor door)
	{
		TextureRegion keyFrame = Assets.castle;
		GagGameRender.DrawTexByCenter(keyFrame, door.postion.x, door.postion.y, door.bounds.width, door.bounds.height, false, false);

//		Texture keyFrame = Assets.testTex;
//		GagGameRender.DrawTexByCenter(keyFrame, door.postion.x, door.postion.y, door.bounds.width, door.bounds.height, false, false);
	}
}
