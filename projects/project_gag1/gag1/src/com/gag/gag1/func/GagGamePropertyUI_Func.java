package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGamePropertyUI;
import com.gag.gag1.GagGameCamera.CameraState;
import com.gag.gag1.GagGameInput.TouchInfo;
import com.gag.gag1.GagGamePropertyUI.PropertyType;
import com.gag.gag1.GagWorld;

public class GagGamePropertyUI_Func {
	
	public static void updatePos(GagWorld world)
	{
		GagGamePropertyUI propertyUI = world.m_PropertyUI;
		float minX = propertyUI.w/2;
		float minY = propertyUI.h/2+GagGameConfig.GameBottomUIHeight;
		
		float maxX = Gdx.graphics.getWidth() - propertyUI.w/2;
		float maxY = Gdx.graphics.getHeight() - GagGameConfig.GameTopUIHeight - propertyUI.h/2;
		
		if( propertyUI.x<minX )
		{
			propertyUI.x = minX;
		}else if( propertyUI.x>maxX ){
			propertyUI.x = maxX;
		}
		
		if( propertyUI.y<minY )
		{
			propertyUI.y = minY;
		}else if( propertyUI.y>maxY ){
			propertyUI.y = maxY;
		}
	}
	
	public static void updateState(GagWorld world)
	{
		GagGamePropertyUI propertyUI = world.m_PropertyUI;
		for(int i=0; i<world.m_Input.m_TouchInfos.size(); ++i)
		{
			TouchInfo touchInfo = world.m_Input.m_TouchInfos.get(i);
			switch( touchInfo.curTouchState )
			{
				case TouchState_None:
					{

					}
					break;
				case TouchState_TouchDown:
					{
						float x = Gdx.input.getX(i);
						float y = Gdx.input.getY(i);
						if( GagGameScreen_Func.isInGameScreen(x, y) && propertyUI.touchID==-1 )
						{
							propertyUI.touchID = i;
							propertyUI.touchStartX = x;
							propertyUI.touchStartY = y;
						}
						
						if( propertyUI.show )
						{
							propertyUI.touchID = -1;
							propertyUI.show = false;
						}
					}
					break;
				case TouchState_Touching:
					{

					}
					break;
				case TouchState_TouchUp:
					{
						float x = Gdx.input.getX(i);
						float y = Gdx.input.getY(i);
						if( propertyUI.touchID==i )
						{
							if( Math.abs(x-propertyUI.touchStartX)<GagGameConfig.touchStep &&
								 Math.abs(y-propertyUI.touchStartY)<GagGameConfig.touchStep
							  )
							{
								propertyUI.show = true;
								propertyUI.x = x;
								propertyUI.y = Gdx.graphics.getHeight() - y;
								
								propertyUI.choosePos = GagGameScreen_Func.convertWindowPosToWorldPos(new Vector2(propertyUI.x, propertyUI.y), world.m_Camera);
								
								propertyUI.chooseObject = GagGameWorld_Func.getGameObjectByScreenXY(world, propertyUI.x, propertyUI.y);
								propertyUI.y-=propertyUI.h/2;
							}else{
								propertyUI.touchID = -1;
							}
						}
					}
					break;
			}
			
		}
	}
	
	public static void update(GagWorld world)
	{
		updateState(world);
		updatePos(world);
	}
	

}
