package com.gag.gag1.struct;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGameTreasure extends GagGameObject {
	public enum TreasureType
	{
		TreasureType_None,
		TreasureType_AthwartWorld,
		TreasureType_Umbrella,
		TreasureType_KillMe,
		TreasureType_Speed,
		
		//scene_editor
		TreasureType_NewScene,
		TreasureType_SaveScene,
		TreasureType_SaveSceneAs,
		TreasureType_OpenScene,
		TreasureType_RunScene,
		TreasureType_StopScene,
		TreasureType_MoreTool,
	};
	
	public enum TreasureState
	{
		TreasureState_None,
		TreasureState_Start,
		TreasureState_Using,
		TreasureState_End,
	};
	
	public TreasureType treasureType;
	public boolean isPickUp;
	public TreasureState treasureState;
	public float useTime;
	public boolean needRelease;
	public boolean enable;
	
	public GagGameTreasure()
	{
		bounds.width = GagGameConfig.TreasureWidth;
		bounds.height = GagGameConfig.TreasureHeight;
		
		objectType = ObjectType.ObjectType_Treasure;
		treasureType = TreasureType.TreasureType_None;
		isPickUp = false;
		treasureState = TreasureState.TreasureState_None;
		useTime = 0f;
		needRelease = false;
		enable = true;
	}
}
