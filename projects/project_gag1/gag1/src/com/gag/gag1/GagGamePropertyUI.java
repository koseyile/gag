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
	
	public enum PropertyState
	{
		PropertyState_hide,
		PropertyState_step1,
		PropertyState_stpe2,
		PropertyState_show,
	};
	
	public float x;
	public float y;
	public float w;
	public float h;
	
	public PropertyState propertyState;
	
	public float touchDownX;
	public float touchDownY;
	public float touchUpX;
	public float touchUpY;
	
	public int touchID;
	
	public GagGameObject chooseObject;
	public Vector2 choosePos;
	
	public int curPropertyIndex;
	public int curPropertyChooseIndex;
	
	public int nextIndexFrames;
	public int preIndexFrames;
	
	public int addValueFrames;
	public int subValueFrames;
	
	public float curAddValue;
	public float curSubValue;
	
	public GagGamePropertyUI()
	{
		x = 0f;
		y = 0f;
		w = GagGameConfig.UI_Property_w;
		h = GagGameConfig.UI_Property_h;
		
		propertyState = PropertyState.PropertyState_hide;

		touchDownX = 0;
		touchDownY = 0;
		touchID = -1;
		
		chooseObject = null;
		choosePos = null;
		
		curPropertyIndex = 0;
		curPropertyChooseIndex = 0;
		
		nextIndexFrames = 0;
		preIndexFrames = 0;
		
		addValueFrames = 0;
		subValueFrames = 0;
		
		curAddValue = 0.0f;
		curSubValue = 0.0f;
	}
}
