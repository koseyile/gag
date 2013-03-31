package com.gag.gag1.struct;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGamePlatform extends GagGameObject {
	
	public GagGamePlatform()
	{
		bounds.width = GagGameConfig.PlatformWidth;
		bounds.height = GagGameConfig.PlatformHeight;
		
		objectType = ObjectType.ObjectType_Plaform;
	}
	
}
