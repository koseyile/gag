package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.gag.gag1.GagGameCamera;
import com.gag.gag1.GagGameCamera.CameraState;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.GagGameInput;
import com.gag.gag1.GagGameInput.TouchInfo;
import com.gag.gag1.GagGameInput.TouchState;
import com.gag.gag1.GagGamePropertyUI.PropertyState;
import com.gag.gag1.GagWorld;
import com.gag.gag1.struct.GagGamePlayer;
import com.gag.gag1.struct.GagGamePlayer.MoveState;

public class GagGameCamera_Func {
	
	public static void moveCameraByPlayer( GagWorld world )
	{
		world.m_Camera.x = world.m_Player.postion.x;
		world.m_Camera.y = world.m_Player.postion.y;
	}
	
	public static void moveCameraByTouching( GagWorld world )
	{
		float x = Gdx.input.getX(world.m_Camera.touchID);
		float y = Gdx.input.getY(world.m_Camera.touchID);

		float temp_x = x;
		float temp_y = y;
		
		x-=world.m_Camera.touchStartX;
		y-=world.m_Camera.touchStartY;
		x*=GagGameConfig.cameraMoveScale;
		y*=GagGameConfig.cameraMoveScale;
		world.m_Camera.x-=x;
		world.m_Camera.y+=y;
		
		world.m_Camera.touchStartX = temp_x;
		world.m_Camera.touchStartY = temp_y;

	}
	
	public static void updateCameraByBound(GagWorld world)
	{
		float viewW = world.m_Camera.w;
		float viewH = world.m_Camera.h;
		
		if( world.m_Camera.x<viewW/2 )
		{
			world.m_Camera.x = viewW/2;
		}
		
		if( world.m_Camera.x>(world.worldBound.width-viewW/2) )
		{
			world.m_Camera.x = world.worldBound.width-viewW/2;
		}
		
		float temp_h1 = GagGameConfig.GameBottomUIHeight*viewH/Gdx.graphics.getHeight();
		float bottomY = viewH/2-temp_h1;
		if( world.m_Camera.y<bottomY )
		{
			world.m_Camera.y = bottomY;
		}
		
		float temp_h2 = GagGameConfig.GameTopUIHeight*viewH/Gdx.graphics.getHeight();
		float topY = (world.worldBound.height-viewH/2)+temp_h2;
		if( world.m_Camera.y>topY )
		{
			world.m_Camera.y = topY;
		}
	}
	
	public static void updateCameraState( GagWorld world )
	{
		GagGameCamera camera = world.m_Camera;
		
		for(int i=0; i<world.m_Input.m_TouchInfos.size(); ++i)
		{
			TouchInfo touchInfo = world.m_Input.m_TouchInfos.get(i);
			switch( touchInfo.curTouchState )
			{
				case TouchState_None:
					{
						if( world.m_Camera.isTouched && world.m_Camera.touchID==i )
						{
							world.m_Camera.isTouched = false;
							world.m_Camera.touchID = -1;
							camera.curCameraState = CameraState.CameraState_TouchEnd;
						}
					}
					break;
				case TouchState_TouchDown:
					{
						float x = Gdx.input.getX(i);
						float y = Gdx.input.getY(i);
						if( GagGameScreen_Func.isInGameScreen(x, y) && world.m_Camera.isTouched==false )
						{
							world.m_Camera.touchStartX = x;
							world.m_Camera.touchStartY = y;
							world.m_Camera.touchID = i;
							world.m_Camera.isTouched = true;
							camera.curCameraState = CameraState.CameraState_TouchStart;
						}
					}
					break;
				case TouchState_Touching:
					{
						float x = Gdx.input.getX(i);
						float y = Gdx.input.getY(i);
						if( GagGameScreen_Func.isInGameScreen(x, y) &&
							 world.m_Camera.isTouched==true &&
							 world.m_Camera.touchID==i )
						{
							//if( world.m_PropertyUI.propertyState!=PropertyState.PropertyState_show )
							{
								camera.curCameraState = CameraState.CameraState_TouchingAndMoving;
							}
						}
					}
					break;
			}
			
		}
		
	}
	
	public static void updateCamera( GagWorld world )
	{
		updateCameraState(world);
		
		GagGameCamera camera = world.m_Camera;
		
		switch( camera.curCameraState )
		{
			case CameraState_None:
				{
					moveCameraByPlayer(world);
				}
				break;
			case CameraState_TouchStart:
				{
					
				}
				break;
			case CameraState_TouchingAndMoving:
				{
					moveCameraByTouching(world);
				}
				break;
			case CameraState_TouchEnd:
				{
					if( world.m_Player.CurMoveState!=MoveState.MoveState_Stand )
					{
						camera.curCameraState = CameraState.CameraState_None;
					}
				}
				break;
		}
		
		updateCameraByBound(world);
	}
}
