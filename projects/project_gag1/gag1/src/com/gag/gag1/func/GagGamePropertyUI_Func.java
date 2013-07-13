package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameInput.TouchState;
import com.gag.gag1.GagGamePropertyUI;
import com.gag.gag1.GagGameCamera.CameraState;
import com.gag.gag1.GagGameInput.TouchInfo;
import com.gag.gag1.GagGamePropertyUI.PropertyState;
import com.gag.gag1.GagGamePropertyUI.PropertyType;
import com.gag.gag1.GagGamePropertyValue;
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
		
		switch( propertyUI.propertyState )
		{
			case PropertyState_hide:
				{
					for(int i=0; i<world.m_Input.m_TouchInfos.size(); ++i)
					{
						TouchInfo touchInfo = world.m_Input.m_TouchInfos.get(i);
						switch( touchInfo.curTouchState )
						{
							case TouchState_TouchDown:
								{
									float x = Gdx.input.getX(i);
									float y = Gdx.input.getY(i);
									if( GagGameScreen_Func.isInGameScreen(x, y) )
									{
										propertyUI.touchID = i;
										propertyUI.touchDownX = x;
										propertyUI.touchDownY = y;
										propertyUI.propertyState = PropertyState.PropertyState_step1;
									}
								}
								break;
						}
					}
				}
				break;
			case PropertyState_step1:
				{
					TouchInfo touchInfo = world.m_Input.m_TouchInfos.get(propertyUI.touchID);
					if( touchInfo.curTouchState==TouchState.TouchState_TouchUp )
					{
						propertyUI.touchUpX = Gdx.input.getX(propertyUI.touchID);
						propertyUI.touchUpY = Gdx.input.getY(propertyUI.touchID);
						
						propertyUI.propertyState = PropertyState.PropertyState_stpe2;
					}
				}
				break;
			case PropertyState_stpe2:
				{
					if( Math.abs(propertyUI.touchUpX-propertyUI.touchDownX)<GagGameConfig.touchStep &&
						 Math.abs(propertyUI.touchUpY-propertyUI.touchDownY)<GagGameConfig.touchStep 
					  )
					{
						propertyUI.x = propertyUI.touchUpX;
						propertyUI.y = Gdx.graphics.getHeight() - propertyUI.touchUpY;
						
						propertyUI.choosePos = GagGameScreen_Func.convertWindowPosToWorldPos(new Vector2(propertyUI.x, propertyUI.y), world.m_Camera);
						
						propertyUI.chooseObject = GagGameWorld_Func.getGameObjectByScreenXY(world, propertyUI.x, propertyUI.y);
						propertyUI.y-=propertyUI.h/2;
						propertyUI.propertyState = PropertyState.PropertyState_show;
					}else{
						propertyUI.propertyState = PropertyState.PropertyState_hide;
					}
				}
				break;
			case PropertyState_show:
				{
					TouchInfo touchInfo = world.m_Input.m_TouchInfos.get(propertyUI.touchID);
					if( touchInfo.curTouchState==TouchState.TouchState_TouchDown )
					{
						float x = Gdx.input.getX(propertyUI.touchID);
						float y = Gdx.input.getY(propertyUI.touchID);
						if( !GagGameScreen_Func.isInGameScreen(x, y) )
						{
							propertyUI.propertyState = PropertyState.PropertyState_hide;
						}else{
							propertyUI.touchDownX = x;
							propertyUI.touchDownY = y;
						}
					}
					
					if( touchInfo.curTouchState==TouchState.TouchState_TouchUp )
					{
						float x = Gdx.input.getX(propertyUI.touchID);
						float y = Gdx.input.getY(propertyUI.touchID);
						
						propertyUI.touchUpX = x;
						propertyUI.touchUpY = y;
						
						if( !GagGameScreen_Func.isInPropertyScreen(x, Gdx.graphics.getHeight() - y, world) &&
							Math.abs(propertyUI.touchUpX-propertyUI.touchDownX)<GagGameConfig.touchStep &&
							Math.abs(propertyUI.touchUpY-propertyUI.touchDownY)<GagGameConfig.touchStep 
						  )
						{
							propertyUI.propertyState = PropertyState.PropertyState_hide;
						}
					}else {
						for(int i=0; i<=255; ++i)
						{
							if( Gdx.input.isKeyPressed(i) )
							{
								int zzz = 0;
								zzz++;
							}
						}
						if (Gdx.input.isKeyPressed(GagGameConfig.propertyNextIndexKey))
						{
							if( propertyUI.nextIndexFrames%GagGameConfig.propertyPresskeyFrames==0 )
							{
								propertyUI.curPropertyChooseIndex++;
							}
							propertyUI.nextIndexFrames++;
						}else{
							propertyUI.nextIndexFrames = 0;
						}
						
						if (Gdx.input.isKeyPressed(GagGameConfig.propertyPreIndexKey))
						{
							if( propertyUI.preIndexFrames%GagGameConfig.propertyPresskeyFrames==0 )
							{
								propertyUI.curPropertyChooseIndex--;
							}
							propertyUI.preIndexFrames++;
						}else{
							propertyUI.preIndexFrames = 0;
						}
						
						if( propertyUI.curPropertyChooseIndex<0 )
						{
							propertyUI.curPropertyChooseIndex = 0;
						}
						
						if( propertyUI.curPropertyChooseIndex>3 )
						{
							propertyUI.curPropertyChooseIndex = 3;
						}
						
						if (Gdx.input.isKeyPressed(GagGameConfig.propertyAddKey))
						{
							if( propertyUI.addValueFrames%GagGameConfig.propertyPresskeyFrames==0 )
							{
								GagGamePropertyValue propertyValue = GagGameObject_Func.getPropertyValueByIndex(propertyUI.chooseObject, propertyUI.curPropertyChooseIndex);
								GagGameObject_Func.setPropertyValueByIndex(propertyUI.chooseObject, propertyUI.curPropertyChooseIndex, propertyValue.value+propertyUI.curAddValue);
							}
							propertyUI.addValueFrames++;
							propertyUI.curAddValue+=GagGameConfig.propertyValueChange;
							if( propertyUI.curAddValue>GagGameConfig.propertyValueChangeMax )
							{
								propertyUI.curAddValue = GagGameConfig.propertyValueChangeMax;
							}
						}else{
							propertyUI.addValueFrames = 0;
							propertyUI.curAddValue = GagGameConfig.propertyValueChangeMin;
						}
						
						if (Gdx.input.isKeyPressed(GagGameConfig.propertySubKey))
						{
							if( propertyUI.subValueFrames%GagGameConfig.propertyPresskeyFrames==0 )
							{
								GagGamePropertyValue propertyValue = GagGameObject_Func.getPropertyValueByIndex(propertyUI.chooseObject, propertyUI.curPropertyChooseIndex);
								GagGameObject_Func.setPropertyValueByIndex(propertyUI.chooseObject, propertyUI.curPropertyChooseIndex, propertyValue.value-propertyUI.curSubValue);
							}
							propertyUI.subValueFrames++;
							propertyUI.curSubValue+=GagGameConfig.propertyValueChange;
							if( propertyUI.curSubValue>GagGameConfig.propertyValueChangeMax )
							{
								propertyUI.curSubValue = GagGameConfig.propertyValueChangeMax;
							}
						}else{
							propertyUI.subValueFrames = 0;
							propertyUI.curSubValue = GagGameConfig.propertyValueChangeMin;
						}
						
					}
				}
				break;
		}
		
	}
	
	public static void update(GagWorld world)
	{
		if( world.m_Editor.isEnable==false )
		{
			return;
		}
		
		updateState(world);
		updatePos(world);
	}
	

}
