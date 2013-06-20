package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogicgames.superjumper.OverlapTester;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.GagWorld.SceneID;
import com.gag.gag1.GagWorld.WorldState;
import com.gag.gag1.func.GagGameObject_Func.InputState;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlayer;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameObject.ObjectType;
import com.gag.gag1.struct.GagGamePlayer.MoveState;
import com.gag.gag1.struct.GagGameTreasure.TreasureState;
import com.gag.gag1.struct.GagGameTreasure.TreasureType;
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
		world.m_Player = new GagGamePlayer();
		world.m_Objects.add(world.m_Player);
	}
	
	public static void checkWorld()
	{
		if( GagGameConfig.TreasureName.length!=TreasureType.values().length )
		{
			Gdx.app.error("Error:", "GagGameConfig.TreasureName.length!=TreasureType.values().length");
			Gdx.app.exit();
		}
		
		if( GagGameConfig.TreasureTopString.length!=TreasureType.values().length )
		{
			Gdx.app.error("Error:", "GagGameConfig.TreasureTopString.length!=TreasureType.values().length");
			Gdx.app.exit();
		}
	}
	
	public static void initWorldConfigByGraphic()
	{
		float f_w = Gdx.graphics.getWidth()/GagGameConfig.World_Graphic_Defult_W;
		float f_h = Gdx.graphics.getHeight()/GagGameConfig.World_Graphic_Defult_H;

		GagGameConfig.GameBottomUIHeight*=f_h;
		GagGameConfig.GameTopUIHeight*=f_h;
		GagGameConfig.UI_treasures_x*=f_w;
		GagGameConfig.UI_treasures_y*=f_h;
		GagGameConfig.UI_treasures_h*=f_h;
		GagGameConfig.UI_treasure_w*=f_w;
		GagGameConfig.UI_treasures_spacing*=f_w;
		GagGameConfig.UI_goLeft_x*=f_w;
		GagGameConfig.UI_goLeft_y*=f_h;
	}
	
	public static void initWorldConfig(GagWorld world)
	{
		float f_w = world.worldBound.width/GagGameConfig.World_Defult_W;
		float f_h = world.worldBound.height/GagGameConfig.World_Defult_H;
		
		GagGameConfig.World_g *= f_h;
		GagGameConfig.UmbrellaScale_g *= f_h;
		GagGameConfig.PlayerMoveLeftDistance *= f_w;
		GagGameConfig.PlayerMoveRightDistance *= f_w;
		GagGameConfig.DownSpeedDead *= f_h;
		GagGameConfig.DisByDoorToPlayer *= (f_w*f_h);
		GagGameConfig.DisByTreasureToPlayer *= (f_w*f_h);
		GagGameConfig.CollisionAppendW *= f_w;
		GagGameConfig.CollisionAppendH *= f_h;
	}
	
	public static void setWorldBound(GagWorld world, float x, float y, float w, float h)
	{
		world.worldBound.x = x;
		world.worldBound.y = y;
		world.worldBound.width = w;
		world.worldBound.height = h;
	}
	
	public static void loadScene(SceneID sceneId, GagWorld world)
	{
		world.m_SceneId = sceneId;
		
		initWorld(world);
		
		try {
			GagGameDataLoad_Func.LoadSceneFromXml(GagGameConfig.SceneFileName[world.m_SceneId.ordinal()-1], world);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if( GagGameConfig.SceneFileName[world.m_SceneId.ordinal()-1].equals("data/scene_editor.xml") )
		{
			GagGameTreasure_Func.pickUpAllTreasure(world);
			world.m_Editor.isEnable = true;
			world.m_Editor.isRun = false;
		}else{
			world.m_Editor.isRun = true;
		}
		
		world.m_WorldState = WorldState.WorldState_FadeIn;
		
		//加载固定道具
		world.m_GoLeft = new GagGameTreasure();
		world.m_GoLeft.treasureType = TreasureType.TreasureType_GoLeft;
		world.m_GoLeft.postion.x = GagGameConfig.UI_goLeft_x;
		world.m_GoLeft.postion.y = GagGameConfig.UI_goLeft_y;
		world.m_GoLeft.bounds.width = GagGameConfig.UI_treasure_w;
		world.m_GoLeft.bounds.height = GagGameConfig.UI_treasures_h;
		world.m_GoLeft.isPickUp = true;
		world.m_Objects.add(world.m_GoLeft);
		
		world.m_GoRight = new GagGameTreasure();
		world.m_GoRight.treasureType = TreasureType.TreasureType_GoRight;
		world.m_GoRight.postion.x = Gdx.graphics.getWidth() - GagGameConfig.UI_goLeft_x;
		world.m_GoRight.postion.y = GagGameConfig.UI_goLeft_y;
		world.m_GoRight.bounds.width = GagGameConfig.UI_treasure_w;
		world.m_GoRight.bounds.height = GagGameConfig.UI_treasures_h;
		world.m_GoRight.isPickUp = true;
		world.m_Objects.add(world.m_GoRight);
		
		world.m_NextPage = new GagGameTreasure();
		world.m_NextPage.treasureType = TreasureType.TreasureType_NextPage;
		world.m_NextPage.postion.x = Gdx.graphics.getWidth() - GagGameConfig.UI_goLeft_x - (GagGameConfig.UI_treasure_w+GagGameConfig.UI_treasures_spacing);
		world.m_NextPage.postion.y = GagGameConfig.UI_goLeft_y;
		world.m_NextPage.bounds.width = GagGameConfig.UI_treasure_w;
		world.m_NextPage.bounds.height = GagGameConfig.UI_treasures_h;
		world.m_NextPage.isPickUp = true;
		world.m_Objects.add(world.m_NextPage);
		
		world.m_PrePage = new GagGameTreasure();
		world.m_PrePage.treasureType = TreasureType.TreasureType_PrePage;
		world.m_PrePage.postion.x = GagGameConfig.UI_goLeft_x+GagGameConfig.UI_treasure_w+GagGameConfig.UI_treasures_spacing;
		world.m_PrePage.postion.y = GagGameConfig.UI_goLeft_y;
		world.m_PrePage.bounds.width = GagGameConfig.UI_treasure_w;
		world.m_PrePage.bounds.height = GagGameConfig.UI_treasures_h;
		world.m_PrePage.isPickUp = true;
		world.m_Objects.add(world.m_PrePage);
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
		GagGameObject retObj = GagGameWorld_Func.updateObjectPosByWorldObjects(obj, world, startPos, endPos, 0f, 0f, true, null);
		if( retObj!=null )
		{
			int i = 0;
			++i;
		}
	}
	
	public static GagGameObject updateObjectPosByWorldObjects( GagGameObject object_out, GagWorld world, Vector2 start, Vector2 end, float append_w, float append_h, boolean updated, Vector2 pos_out )
	{
		//计算玩家的位移是否与世界里其它道具相碰撞
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
																				  object_out.bounds.width+append_w, 
																				  object_out.bounds.height+append_h, 
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
		
		if( updated )
		{
			object_out.postion.set(nearV);
		}
		
		if( pos_out!=null )
		{
			pos_out.set(nearV);
		}
		
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
