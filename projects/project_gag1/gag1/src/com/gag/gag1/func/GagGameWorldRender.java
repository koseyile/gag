package com.gag.gag1.func;

import com.badlogicgames.superjumper.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogicgames.superjumper.Assets;
import com.gag.gag1.GagGameScreen;
import com.gag.gag1.GagWorld;
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
		DrawPlayer();
		
		int len = gagWorld.m_Platforms.size();
		for (int i = 0; i < len; i++) 
		{
			GagGamePlatform platform = gagWorld.m_Platforms.get(i);
			DrawPlatform(platform);
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
}
