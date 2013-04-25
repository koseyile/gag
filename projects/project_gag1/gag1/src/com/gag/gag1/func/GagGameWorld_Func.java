package com.gag.gag1.func;

import com.badlogic.gdx.math.Vector2;
import com.badlogicgames.superjumper.OverlapTester;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.GagWorld.SceneID;
import com.gag.gag1.GagWorld.WorldState;
import com.gag.gag1.func.GagGameObject_Func.InputState;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameObject.ObjectType;
import com.gag.gag1.struct.GagGameTreasure.TreasureState;
import com.gag.gag1.struct.GagGameTreasure;

public class GagGameWorld_Func {
	
	public static void initWorld(GagWorld world)
	{
		world.m_Objects.clear();
		world.m_Treasures.clear();
		world.m_g = GagGameConfig.World_g;
		world.m_FadeInTime = 0f;
		world.m_FadeOutTime = 0f;
		world.m_WorldState = WorldState.WorldState_Play;
		world.isGameOver = false;
		world.m_Player.SpeedScale = 1.0f;
		world.m_Objects.add(world.m_Player);
	}
	
	public static void loadScene(SceneID sceneId, GagWorld world)
	{
		world.m_SceneId = sceneId;
		
		initWorld(world);
		
		try {
			GagGameDataLoad_Func.LoadSceneByXml(GagGameConfig.SceneFileName[world.m_SceneId.ordinal()-1], world);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		world.m_WorldState = WorldState.WorldState_FadeIn;		
	}

	public static boolean updateByWorld(GagWorld world)
	{
		int len = world.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = world.m_Objects.get(i);

			switch(object.objectType)
			{
				case ObjectType_Door:
					{
						if( ((GagGameDoor)object).doorType==DoorType.DoorType_Exit )
						{
							if( world.m_Player.postion.dst(object.postion)<GagGameConfig.DisByDoorToPlayer )
							{
								return true;
							}
						}
					}
					break;
				case ObjectType_Treasure:
					{
						if( world.m_Player.postion.dst(object.postion)<GagGameConfig.DisByTreasureToPlayer )
						{
							GagGameTreasure_Func.pickUpTreasure((GagGameTreasure)object, world);
						}
					}
					break;
			
			}

	
		}
		return false;		
	}
	
	public static void updateWorldGByTouch(boolean bTouched, float TouchY, float ScreenH, GagWorld world)
	{
		if( bTouched )
		{
			if( TouchY>(ScreenH/2) )
			{
				world.m_g=-Math.abs(world.m_g);
			}else{
				world.m_g=Math.abs(world.m_g);
			}
		}
	}
	
	public static void pushObjectByPlayer(GagGameObject obj, Vector2 dir, GagWorld world)
	{
		boolean bCanPush = false;
		switch( obj.objectType )
		{
			case ObjectType_Box:
				{
					bCanPush = true;
				}
				break;
		}
		
		if(bCanPush==false)
		{
			return;
		}
		
		Vector2 startPos = new Vector2(obj.postion);
		Vector2 endPos = new Vector2(obj.postion);
		obj.postion.add(dir.mul(1.0f));
		endPos.set(obj.postion);
		GagGameObject retObj = GagGameWorld_Func.updateObjectPosByWorldObjects(obj, world, startPos, endPos);
		if( retObj!=null )
		{
			int i = 0;
			++i;
		}
	}
	
	public static GagGameObject updateObjectPosByWorldObjects( GagGameObject object_out, GagWorld world, Vector2 start, Vector2 end )
	{
		//������ҵ�λ���Ƿ���������������������ײ
		Vector2 outV = new Vector2();
		Vector2 nearV = new Vector2();
		nearV.set(end);
		
		GagGameObject ReturnObj = null;
		
		int len = world.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = world.m_Objects.get(i);
			if( object.equals(object_out) )
			{
				continue;
			}
			
			boolean bNeedCalc = false;
			boolean bResult = false;
			switch( object.objectType )
			{
				case ObjectType_Plaform:
				case ObjectType_Box:
				case ObjectType_Player:
					{
						bNeedCalc = true;
					}
					break;
			}
			
			if( bNeedCalc==false )
			{
				continue;
			}

			bResult = GagGameObject_Func.getIntersectionByObject(outV, start, end, 
																				  object_out.bounds.width, 
																				  object_out.bounds.height, 
																				  object);
			
			if( outV.dst(start)<end.dst(start) )
			{
				nearV.set(outV);
				//object_out.downSpeed = object.downSpeed;
				
				if( object.canBeDown )
				{
					object.beDown = true;
				}
				
				ReturnObj = object;
			}
		}
		
		object_out.postion.set(nearV);
		return ReturnObj;
	}
	
	public static void gameOver(GagWorld world)
	{
		world.m_WorldState = WorldState.WorldState_FadeOut;
		int preScene = world.m_SceneId.ordinal()-1;
		world.m_SceneId = SceneID.values()[preScene];
		world.isGameOver = true;
	}
	
}