package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameCamera;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;

public class GagGameScreen_Func {
	public static boolean isInGameScreen(float x, float y)
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		if( x>0f && x<w && 
			 y>GagGameConfig.GameTopUIHeight && y<(h-GagGameConfig.GameBottomUIHeight)
		  )
		{
			return true;
		}
		return false;
	}
	
	public static boolean isInPropertyScreen(float x, float y, GagWorld world)
	{
		float w = world.m_PropertyUI.w;
		float h = world.m_PropertyUI.h;
		
		float x_start = world.m_PropertyUI.x-w/2;
		float y_start = world.m_PropertyUI.y-h/2;
		
		
		if( x>x_start && x<(x_start+w) && 
			 y>y_start && y<(y_start+h)
		  )
		{
			return true;
		}
		return false;
	}
	
	public static float getGameScreenWidth()
	{
		return Gdx.graphics.getWidth();
	}
	
	public static float getGameScreenHeight()
	{
		float gameScreenH = Gdx.graphics.getHeight()-GagGameConfig.GameTopUIHeight-GagGameConfig.GameBottomUIHeight;
		return gameScreenH;
	}
	
	public static Vector2 getCenterPosByScreen()
	{
		Vector2 vCenter = new Vector2();
		float gameScreenH = getGameScreenHeight();
		vCenter.x = getGameScreenWidth()/2;
		vCenter.y = Gdx.graphics.getHeight()/2;
		
		return vCenter;
	}
	
	public static Vector2 convertWindowPosToWorldPos(Vector2 pos, GagGameCamera camera)
	{
		//得到游戏区域中心点坐标
		Vector2 vCenter = GagGameScreen_Func.getCenterPosByScreen();
		
		Vector2 vSub = pos.cpy();
		vSub.sub(vCenter);
		
		vSub.x*=(camera.w/Gdx.graphics.getWidth());
		vSub.y*=(camera.h/Gdx.graphics.getHeight());
		
		Vector2 out = new Vector2(camera.x, camera.y);
		out.add(vSub);
		return out;
	}
}
