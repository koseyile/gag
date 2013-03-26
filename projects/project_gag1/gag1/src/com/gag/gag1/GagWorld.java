package com.gag.gag1;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogicgames.superjumper.Platform;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGamePlayer;

public class GagWorld {
	
	public GagGamePlayer m_Player;
	public List<GagGamePlatform> m_Platforms;
	public float m_g;
	
	public GagWorld()
	{
		m_Player = new GagGamePlayer();
		m_Player.postion.x = GagGameConfig.PlayerStartX;
		m_Player.postion.y = GagGameConfig.PlayerStartY;
		
		m_Platforms = new ArrayList<GagGamePlatform>();
		
		
		{
			GagGamePlatform tempPlatform = new GagGamePlatform();
			tempPlatform.postion.x = 100f;
			tempPlatform.postion.y = 16f;
			m_Platforms.add(tempPlatform);
		}
		
		{
			GagGamePlatform tempPlatform = new GagGamePlatform();
			tempPlatform.postion.x = 200f;
			tempPlatform.postion.y = 100f;
			m_Platforms.add(tempPlatform);
		}
		
		{
			GagGamePlatform tempPlatform = new GagGamePlatform();
			tempPlatform.postion.x = 300f;
			tempPlatform.postion.y = 160f;
			m_Platforms.add(tempPlatform);
		}

		{
			GagGamePlatform tempPlatform = new GagGamePlatform();
			tempPlatform.postion.x = 400f;
			tempPlatform.postion.y = 2f;
			m_Platforms.add(tempPlatform);
		}

		m_g = GagGameConfig.World_g;
	}
	
	void updatePlayerPosByObject( Vector2 start, Vector2 end )
	{
		//计算玩家的位移是否与世界里其它道具相碰撞
		Vector2 outV = new Vector2();
		Vector2 nearV = new Vector2();
		nearV.set(end);
		
		int len = m_Platforms.size();
		for (int i = 0; i < len; i++) 
		{
			GagGamePlatform platform = m_Platforms.get(i);
			boolean bResult = GagGameObject_Func.GetIntersectionByObject(outV, start, end, 
																							 m_Player.bounds.width, 
																							 m_Player.bounds.height, 
																							 platform);
			
			if(bResult)
			{
				m_Player.downSpeed = 0f;
			}
			
			if( outV.dst(start)<end.dst(start) )
			{
				nearV.set(outV);
			}
		}
		
		m_Player.postion.set(nearV);		
	}
	
	void update(float delta)
	{
		Vector2 startPos = new Vector2(m_Player.postion);
		Vector2 endPos = new Vector2(m_Player.postion);
		
		ApplicationType appType = Gdx.app.getType();
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS)
		{
			GagGameObject_Func.UpdatePlayerPosByTouch(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.graphics.getWidth(), m_Player);
			GagGameObject_Func.UpdatePlayerPosByAccelerometerY(Gdx.input.getAccelerometerY(), m_Player);
			
			float AX = Gdx.input.getAccelerometerX();
			if( AX>GagGameConfig.AccelerometerMaxX )
			{
				m_g=-Math.abs(m_g);
			}
			
			if( AX<-GagGameConfig.AccelerometerMaxX )
			{
				m_g=Math.abs(m_g);
			}
			
		}else{
			int key = Keys.ANY_KEY;
			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoRightKey))
			{
				key = GagGameConfig.PlayerGoRightKey;
			}
			
			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoLeftKey))
			{
				key = GagGameConfig.PlayerGoLeftKey;
			}
			
			GagGameObject_Func.UpdatePlayerPosByKeyboard(key, m_Player);
			
			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoDownKey))
			{
				m_g=-Math.abs(m_g);
			}
			
			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoUpKey))
			{
				m_g=Math.abs(m_g);
			}
		}
		
		endPos.set(m_Player.postion);
		updatePlayerPosByObject(startPos, endPos);
		
		startPos.set(m_Player.postion);
		GagGameObject_Func.UpdateObjectByWorldG(m_Player, m_g);
		endPos.set(m_Player.postion);
		updatePlayerPosByObject(startPos, endPos);
		
		GagGameObject_Func.UpdatePlayerAnimation(m_Player, delta);
		
		Rectangle bound = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		GagGameObject_Func.UpdateObjectDownSpeedByScreenBound(m_Player, bound);
		GagGameObject_Func.UpdateObjectPosByScreenBound(m_Player, bound);
	}
	
}
