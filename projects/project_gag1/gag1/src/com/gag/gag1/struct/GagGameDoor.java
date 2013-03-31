package com.gag.gag1.struct;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGameDoor extends GagGameObject {
	
	public enum DoorType
	{
		DoorType_Enter,
		DoorType_Exit,
	};
	
	public DoorType doorType;
	public GagGameDoor()
	{
		bounds.width = GagGameConfig.DoorWidth;
		bounds.height = GagGameConfig.DoorHeight;
		
		objectType = ObjectType.ObjectType_Door;
		
		doorType = DoorType.DoorType_Enter;
	}
}
