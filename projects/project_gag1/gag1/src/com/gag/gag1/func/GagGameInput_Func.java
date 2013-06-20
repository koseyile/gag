package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameInput;
import com.gag.gag1.GagGameInput.TouchInfo;
import com.gag.gag1.GagGameInput.TouchState;

public class GagGameInput_Func {
	
	public static void update(GagGameInput input, float delta)
	{
		for( int i=0; i<input.m_TouchInfos.size(); ++i )
		{
			updateTouchState(input, i);
			updateTouchByTime(input, delta, i);		
		}
	}
	
	public static void updateTouchByTime(GagGameInput input, float delta, int pointId)
	{
		if( pointId>=input.m_TouchInfos.size() )
		{
			return;
		}
		
		TouchInfo touchInfo = input.m_TouchInfos.get(pointId);
		touchInfo.isTouched = false;
		switch(touchInfo.curTouchState)
		{
			case TouchState_TouchDown:
				{
					touchInfo.isTouched = true;
					touchInfo.touchTime = 0f;
				}
				break;
			case TouchState_Touching:
				{
					touchInfo.touchTime+=delta;
					if( touchInfo.touchTime>=GagGameConfig.touchedOnceTime )
					{
						touchInfo.isTouched = true;
						touchInfo.touchTime = 0f;
					}
				}
				break;
		}
	}
	
	public static void updateTouchState(GagGameInput input, int pointId)
	{
		if( pointId>=input.m_TouchInfos.size() )
		{
			return;
		}
		
		TouchInfo touchInfo = input.m_TouchInfos.get(pointId);
		switch(touchInfo.curTouchState)
		{
			case TouchState_None:
				{
					if( Gdx.input.isTouched(pointId) )
					{
						touchInfo.curTouchState = TouchState.TouchState_TouchDown;
					}
				}
				break;
			case TouchState_TouchDown:
				{
					if( Gdx.input.isTouched(pointId) )
					{
						touchInfo.curTouchState = TouchState.TouchState_Touching;
					}else{
						touchInfo.curTouchState = TouchState.TouchState_None;
					}
				}
				break;
			case TouchState_Touching:
				{
					if( Gdx.input.isTouched(pointId) )
					{
						touchInfo.curTouchState = TouchState.TouchState_Touching;
					}else{
						touchInfo.curTouchState = TouchState.TouchState_TouchUp;
					}
				}
				break;
			case TouchState_TouchUp:
				{
					touchInfo.curTouchState = TouchState.TouchState_None;
				}
				break;
		}

	}
}
