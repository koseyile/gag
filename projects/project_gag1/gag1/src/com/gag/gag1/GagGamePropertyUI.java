package com.gag.gag1;

import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.struct.GagGameObject;

public class GagGamePropertyUI {
	public enum PropertyType
	{
		PropertyType_none,
		PropertyType_world,
		PropertyType_player,
		PropertyType_treasure,
	};
	
	public float x;
	public float y;
	public float w;
	public float h;
	
	public boolean show;
	
	public float touchStartX;
	public float touchStartY;
	public int touchID;
	
	public GagGameObject chooseObject;
	public Vector2 choosePos;
	public GagGamePropertyUI()
	{
		x = 0f;
		y = 0f;
		w = GagGameConfig.UI_Property_w;
		h = GagGameConfig.UI_Property_h;
		
		show = false;

		touchStartX = 0;
		touchStartY = 0;
		touchID = -1;
		
		chooseObject = null;
		choosePos = null;
	}
}
