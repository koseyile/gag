package com.gag.gag1;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogicgames.superjumper.Assets;
import com.badlogicgames.superjumper.HelpScreen2;
import com.badlogicgames.superjumper.OverlapTester;
import com.badlogicgames.superjumper.Platform;
import com.gag.gag1.func.GagGameDataLoad_Func;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.func.GagGameTreasure_Func;
import com.gag.gag1.func.GagGameWorld_Func;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGameObject.ObjectType;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGamePlayer;
import com.gag.gag1.struct.GagGameTreasure;

public class GagWorld {
	
	public enum SceneID
	{
		SceneID_start,
		SceneID_1,
		SceneID_2,
		SceneID_3,
		
		SceneID_end,
	};
	
	public enum WorldState
	{
		WorldState_Play,
		WorldState_FadeIn,
		WorldState_FadeOut,
	};
	
	public GagGamePlayer m_Player;
	public List<GagGameObject> m_Objects;
	public List<GagGameTreasure> m_Treasures;
	public float m_g;
	public SceneID m_SceneId;
	public WorldState m_WorldState;
	public float m_FadeInTime;
	public float m_FadeOutTime;
	public Rectangle worldBound;
	public boolean isGameOver;
	
	public GagWorld()
	{
		m_Player = new GagGamePlayer();
		m_Player.postion.x = GagGameConfig.PlayerStartX;
		m_Player.postion.y = GagGameConfig.PlayerStartY;
		
		m_Objects = new ArrayList<GagGameObject>();
		m_Treasures = new ArrayList<GagGameTreasure>();

		GagGameWorld_Func.initWorld(this);
		
		worldBound = new Rectangle(0, 0, 800, 600);
	}
	
	void setWorldBound(float x, float y, float w, float h)
	{
		worldBound.x = x;
		worldBound.y = y;
		worldBound.width = w;
		worldBound.height = h;
	}
	
	void updatePlayer(float delta)
	{
		Vector2 startPos = new Vector2(m_Player.postion);
		Vector2 endPos = new Vector2(m_Player.postion);
		
		ApplicationType appType = Gdx.app.getType();
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS)
		{
			GagGameObject_Func.updatePlayerPosByTouch(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), m_Player);
			
			//GagGameWorld_Func.UpdateWorldGByTouch(Gdx.input.isTouched(), Gdx.input.getY(), Gdx.graphics.getHeight(), this);
			//GagGameObject_Func.UpdatePlayerPosByAccelerometerY(Gdx.input.getAccelerometerY(), m_Player);
			
//			float AX = Gdx.input.getAccelerometerX();
//			if( AX>GagGameConfig.AccelerometerMaxX )
//			{
//				m_g=-Math.abs(m_g);
//			}
//			
//			if( AX<-GagGameConfig.AccelerometerMaxX )
//			{
//				m_g=Math.abs(m_g);
//			}
			
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
			
			GagGameObject_Func.updatePlayerPosByKeyboard(key, m_Player);
			
//			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoDownKey))
//			{
//				m_g=-Math.abs(m_g);
//			}
//			
//			if (Gdx.input.isKeyPressed(GagGameConfig.PlayerGoUpKey))
//			{
//				m_g=Math.abs(m_g);
//			}
		}
		
		//碰撞检测
		endPos.set(m_Player.postion);
		GagGameWorld_Func.updateObjectPosByWorldObjects(m_Player, this, startPos, endPos);
		
		startPos.set(m_Player.postion);
		GagGameObject_Func.updateObjectByWorldG(m_Player, m_g);
		endPos.set(m_Player.postion);
		
		if( GagGameWorld_Func.updateObjectPosByWorldObjects(m_Player, this, startPos, endPos) ||
			 !GagGameObject_Func.isInScreenBoundByY(m_Player, worldBound)
			)
		{
			//往下掉的速度过快，会gameover
			//Gdx.app.log("GagWorld", "downSpeed: " + m_Player.downSpeed);
			if( Math.abs(m_Player.downSpeed)>=GagGameConfig.DownSpeedDead )
			{
				GagGameWorld_Func.gameOver(this);
			}
			m_Player.downSpeed = 0f;
		}

		GagGameObject_Func.updateObjectDownSpeedByScreenBound(m_Player, worldBound);
		GagGameObject_Func.updateObjectPosByScreenBound(m_Player, worldBound);
		
		GagGameObject_Func.updatePlayerAnimation(m_Player, delta);
		//检测是否走到终点
		if( GagGameWorld_Func.updateByWorld(this) )
		{
			m_WorldState = WorldState.WorldState_FadeOut;
		}

	}
	
	//宝物更新
	void updateTreasure(float delta)
	{
		if (Gdx.input.justTouched())
		{
			float touchX = Gdx.input.getX();
			float touchY = Gdx.input.getY();
			touchY = Gdx.graphics.getHeight() - touchY;
			
			GagGameTreasure_Func.updateTreasureByTouch(touchX, touchY, this);
		}
		
		GagGameTreasure_Func.updateTreasureByTime(delta, this);
		GagGameTreasure_Func.releaseTreasure(this);
	}
	
	void update(float delta)
	{
		switch(m_WorldState)
		{
			case WorldState_Play:
				{
					updatePlayer(delta);
					updateTreasure(delta);
				}
				break;
			case WorldState_FadeIn:
				{
					m_FadeInTime+=delta;
					if( m_FadeInTime>GagGameConfig.FadeInTime )
					{
						m_FadeInTime = 0f;
						m_WorldState = WorldState.WorldState_Play;
					}
				}
				break;
			case WorldState_FadeOut:
				{
					m_FadeOutTime+=delta;
					if( m_FadeOutTime>GagGameConfig.FadeOutTime )
					{
						m_FadeOutTime = 0f;
						m_WorldState = WorldState.WorldState_FadeIn;
						
						int nextScene = m_SceneId.ordinal()+1;
						if( nextScene!=SceneID.SceneID_end.ordinal() )
						{
							GagGameWorld_Func.loadScene( SceneID.values()[nextScene], this );
						}
					}
				}
				break;
		}
	}
	
}
