package com.gag.gag1.func;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGameDoor;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGameTreasure;
import com.gag.gag1.struct.GagGameDoor.DoorType;
import com.gag.gag1.struct.GagGameTreasure.TreasureState;

public class GagGameTreasure_Func {
	public static void pickUpTreasure(GagGameTreasure treasure, GagWorld world)
	{
		if( treasure.isPickUp==false )
		{
			int len = world.m_Treasures.size();
			treasure.postion.x = treasure.bounds.width/2+treasure.bounds.width*len;
			treasure.postion.y = world.worldBound.height+treasure.bounds.height/2;
			world.m_Treasures.add(treasure);
			treasure.isPickUp = true;
		}
	}
	
	public static void pickUpAllTreasure(GagWorld world)
	{
		int len = world.m_Objects.size();
		for (int i = 0; i < len; i++)
		{
			GagGameObject object = world.m_Objects.get(i);

			switch(object.objectType)
			{
				case ObjectType_Treasure:
					{
						GagGameTreasure_Func.pickUpTreasure((GagGameTreasure)object, world);
					}
					break;
			}
		}
	}
	
	public static void useTreasure(GagGameTreasure treasure, GagWorld world)
	{
		switch(treasure.treasureType)
		{
			case TreasureType_AthwartWorld:
				{
					if( world.m_g>0f )
					{
						world.m_g=-Math.abs(world.m_g);
					}else{
						world.m_g=Math.abs(world.m_g);
					}
				}
				break;
			case TreasureType_Umbrella:
			case TreasureType_Speed:
				{
					if( treasure.treasureState==TreasureState.TreasureState_None )
					{
						treasure.treasureState = TreasureState.TreasureState_Start;
					}
				}
				break;
			case TreasureType_KillMe:
				{
					GagGameWorld_Func.gameOver(world);
				}
				break;
			case TreasureType_NewScene:
				{
					GagGameSceneEditor_Func.newScene(world);
				}
				break;
			case TreasureType_SaveScene:
				{
					GagGameSceneEditor_Func.saveScene(world);
				}
				break;
			case TreasureType_OpenScene:
				{
					GagGameSceneEditor_Func.openScene(world);
				}
				break;
			case TreasureType_RunScene:
				{
					GagGameSceneEditor_Func.runScene(world, world.m_Editor);
				}
				break;
			case TreasureType_StopScene:
				{
					GagGameSceneEditor_Func.stopScene(world, world.m_Editor);
				}
				break;
		}
	}
	
	public static void updateTreasureByTouch(float touchX, float touchY, GagWorld world)
	{
		int len = world.m_Treasures.size();
		for( int i=0; i<len; ++i )
		{
			GagGameTreasure treasure = world.m_Treasures.get(i);
			if (GagGameObject_Func.pointInObject(touchX, touchY, treasure) && treasure.enable)
			{
				useTreasure(treasure, world);
				break;
			}
		}
	}
	
	public static void updateTreasureByTime(float delta, GagWorld world)
	{
		int len = world.m_Treasures.size();
		for( int i=0; i<len; ++i )
		{
			GagGameTreasure treasure = world.m_Treasures.get(i);
			switch(treasure.treasureType)
			{
				case TreasureType_Umbrella:
					{
						if( treasure.treasureState==TreasureState.TreasureState_Start )
						{
							world.m_g*=GagGameConfig.umbrellaScale_g;
							treasure.treasureState = TreasureState.TreasureState_Using;
						}else if( treasure.treasureState==TreasureState.TreasureState_Using )
						{
							treasure.postion.x = world.m_Player.postion.x;
							treasure.postion.y = world.m_Player.postion.y;
							treasure.useTime+=delta;
							if( treasure.useTime>GagGameConfig.umbrellaUseTime )
							{
								treasure.treasureState = TreasureState.TreasureState_End;
							}
						}else if( treasure.treasureState==TreasureState.TreasureState_End )
						{
							world.m_g/=GagGameConfig.umbrellaScale_g;
							treasure.needRelease = true;
						}
					}
					break;
				case TreasureType_Speed:
					{
						if( treasure.treasureState==TreasureState.TreasureState_Start )
						{
							world.m_Player.SpeedScale*=GagGameConfig.speedAddScale;
							treasure.treasureState = TreasureState.TreasureState_Using;
						}else if( treasure.treasureState==TreasureState.TreasureState_Using )
						{
							treasure.postion.x = world.m_Player.postion.x;
							treasure.postion.y = world.m_Player.postion.y;
							treasure.useTime+=delta;
							if( treasure.useTime>GagGameConfig.speedAddUseTime )
							{
								treasure.treasureState = TreasureState.TreasureState_End;
							}
						}else if( treasure.treasureState==TreasureState.TreasureState_End )
						{
							world.m_Player.SpeedScale/=GagGameConfig.speedAddScale;
							treasure.needRelease = true;
						}
					}
					break;
			}
			
		}
	}
	
	public static void releaseTreasure(GagWorld world)
	{
		while(true)
		{
			int len = world.m_Treasures.size();
			int i = 0;
			for( i=0; i<len; ++i )
			{
				GagGameTreasure treasure = world.m_Treasures.get(i);
				if( treasure.needRelease )
				{
					world.m_Treasures.remove(i);
					world.m_Objects.remove(treasure);
					break;
				}
			}
			if(i==len)
			{
				break;
			}
		}
	}
	
	public static void setAllTreasureEnable(GagWorld world, boolean enable)
	{
		int len = world.m_Treasures.size();
		int i = 0;
		for( i=0; i<len; ++i )
		{
			GagGameTreasure treasure = world.m_Treasures.get(i);
			treasure.enable = enable;
		}
	}
	
	public static void setTreasureEnableByType(GagWorld world, GagGameTreasure.TreasureType type, boolean enable)
	{
		int len = world.m_Treasures.size();
		int i = 0;
		for( i=0; i<len; ++i )
		{
			GagGameTreasure treasure = world.m_Treasures.get(i);
			if( treasure.treasureType==type )
			{
				treasure.enable = enable;
			}
		}
	}
	
}
