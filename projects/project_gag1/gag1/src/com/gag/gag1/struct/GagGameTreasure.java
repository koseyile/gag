package com.gag.gag1.struct;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGameTreasure extends GagGameObject {
	public enum TreasureType
	{
		TreasureType_None,
		TreasureType_AthwartWorld,
	};
	public TreasureType treasureType;
	public boolean isPickUp;
	
	public GagGameTreasure()
	{
		bounds.width = GagGameConfig.TreasureWidth;
		bounds.height = GagGameConfig.TreasureHeight;
		
		objectType = ObjectType.ObjectType_Treasure;
		treasureType = TreasureType.TreasureType_None;
		isPickUp = false;
	}
}
