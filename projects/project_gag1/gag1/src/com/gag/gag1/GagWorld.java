package com.gag.gag1;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogicgames.superjumper.Platform;
import com.gag.gag1.func.GagGameDataLoad_Func;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGameObject.ObjectType;
import com.gag.gag1.struct.GagGamePlatform;
import com.gag.gag1.struct.GagGamePlayer;

public class GagWorld {
	
	public enum SceneID
	{
		SceneID_1,
		SceneID_2,
		
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
	public float m_g;
	public SceneID m_SceneId;
	public WorldState m_WorldState;
	public float m_FadeInTime;
	public float m_FadeOutTime;
	
	public GagWorld()
	{
		m_Player = new GagGamePlayer();
		m_Player.postion.x = GagGameConfig.PlayerStartX;
		m_Player.postion.y = GagGameConfig.PlayerStartY;
		
		m_Objects = new ArrayList<GagGameObject>();
		
		m_g = GagGameConfig.World_g;
		m_FadeInTime = 0f;
		m_FadeOutTime = 0f;
		
		loadScene(SceneID.SceneID_1);
	}
	
	void loadScene(SceneID sceneId)
	{
		m_SceneId = sceneId;
		
		m_Objects.clear();
		try {
			GagGameDataLoad_Func.LoadSceneByXml(GagGameConfig.SceneFileName[m_SceneId.ordinal()], this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		m_WorldState = WorldState.WorldState_FadeIn;
	}
	
	boolean isCompleteScene()
	{
		int len = m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = m_Objects.get(i);

			if( object.objectType==ObjectType.ObjectType_Door )
			{
				if( ((GagGameDoor)object).doorType==DoorType.DoorType_Exit )
				{
					if( m_Player.postion.dst(object.postion)<GagGameConfig.DisByDoorToPlayer )
					{
						return true;
					}
				}
			}
	
		}
		return false;
	}
	
	void updatePlayerPosByObject( Vector2 start, Vector2 end )
	{
		//计算玩家的位移是否与世界里其它道具相碰撞
		Vector2 outV = new Vector2();
		Vector2 nearV = new Vector2();
		nearV.set(end);
		
		int len = m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = m_Objects.get(i);
			boolean bResult = false;
			boolean bNeedCalc = false;
			switch( object.objectType )
			{
				case ObjectType_Plaform:
					{
						bNeedCalc = true;
					}
					break;
			}
			
			if( bNeedCalc==false )
			{
				continue;
			}

			bResult = GagGameObject_Func.GetIntersectionByObject(outV, start, end, 
																				  m_Player.bounds.width, 
																				  m_Player.bounds.height, 
																				  object);
			
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
	
	void updatePlayer(float delta)
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
		
		//碰撞检测
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
		
		//检测是否走到终点
		if( isCompleteScene() )
		{
			m_WorldState = WorldState.WorldState_FadeOut;
		}
	}
	
	void update(float delta)
	{
		switch(m_WorldState)
		{
			case WorldState_Play:
				{
					updatePlayer(delta);
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
							loadScene( SceneID.values()[nextScene] );
						}
					}
				}
				break;
		}
	}
	
}
