package com.gag.gag1.struct;

import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject.ObjectType;

public class GagGameBox extends GagGameObject {
	public GagGameBox()
	{
		bounds.width = GagGameConfig.BoxWidth;
		bounds.height = GagGameConfig.BoxHeight;
		
		objectType = ObjectType.ObjectType_Box;
		beDown = true;
	}
}
