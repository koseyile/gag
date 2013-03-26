package com.gag.gag1.func;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gag.gag1.GagGameConfig;
import com.gag.gag1.struct.GagGameObject;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameObject_Func {
	public enum InputState
	{
		InputState_None,
		InputState_Left,
		InputState_Right,
	}

	public static void UpdatePlayerPosByInputState(InputState inputState, GagGamePlayer player)
	{
		switch(inputState)
		{
			case InputState_None:
				{
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Stand;
				}
				break;
			case InputState_Left:
				{
					player.postion.x-=GagGameConfig.PlayerMoveLeftDistance;
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Left;
					player.CurReverseX = true;
				}
				break;
			case InputState_Right:
				{
					player.postion.x+=GagGameConfig.PlayerMoveRightDistance;
					player.CurMoveState = GagGamePlayer.MoveState.MoveState_Right;
					player.CurReverseX = false;
				}
				break;
		}
	}
	
	public static void UpdatePlayerPosByTouch(boolean bTouched, float TouchX, float ScreenW, GagGamePlayer player)
	{
		if( bTouched )
		{
			if( TouchX>(ScreenW/2) )
			{
				UpdatePlayerPosByInputState(InputState.InputState_Right, player);
			}else{
				UpdatePlayerPosByInputState(InputState.InputState_Left, player);
			}
		}else{
			UpdatePlayerPosByInputState(InputState.InputState_None, player);
		}
	}
	
	public static void UpdatePlayerPosByAccelerometerY(float AY, GagGamePlayer player)
	{
		if( AY<=-GagGameConfig.AccelerometerMaxY )
		{
			UpdatePlayerPosByInputState(InputState.InputState_Left, player);
		}
		
		if( AY>=GagGameConfig.AccelerometerMaxY )
		{
			UpdatePlayerPosByInputState(InputState.InputState_Right, player);
		}
	}
	
	public static void UpdatePlayerPosByKeyboard(int key, GagGamePlayer player)
	{
		switch( key )
		{
			case GagGameConfig.PlayerGoRightKey:
				{
					UpdatePlayerPosByInputState(InputState.InputState_Right, player);
				}
				break;
			case GagGameConfig.PlayerGoLeftKey:
				{
					UpdatePlayerPosByInputState(InputState.InputState_Left, player);
				}
				break;
			default:
				{
					UpdatePlayerPosByInputState(InputState.InputState_None, player);
				}
				break;
		}
	}
	
	public static void UpdatePlayerAnimation(GagGamePlayer player, float delta)
	{
		player.PassTime += delta;
	}
	
	public static void UpdateObjectByWorldG(GagGameObject object, float g)
	{
		object.downSpeed+=g;
		object.postion.y+=object.downSpeed;
	}
	
	public static void UpdateObjectDownSpeedByScreenBound(GagGameObject object, Rectangle bound)
	{
		float minY = bound.y+object.bounds.height/2;
		float maxY = bound.y+bound.height-object.bounds.height/2;

		object.downSpeed = object.postion.y<minY ? 0f : object.downSpeed;
		object.downSpeed = object.postion.y>maxY ? 0f : object.downSpeed;
	}
	
	public static void UpdateObjectPosByScreenBound(GagGameObject object, Rectangle bound)
	{
		float minX = bound.x+object.bounds.width/2;
		float minY = bound.y+object.bounds.height/2;
		
		float maxX = bound.x+bound.width-object.bounds.width/2;
		float maxY = bound.y+bound.height-object.bounds.height/2;

		object.postion.x = object.postion.x<minX ? minX : object.postion.x;
		object.postion.y = object.postion.y<minY ? minY : object.postion.y;
		
		object.postion.x = object.postion.x>maxX ? maxX : object.postion.x;
		object.postion.y = object.postion.y>maxY ? maxY : object.postion.y;
	}
	
	public static boolean Vector2IsAvailability( Vector2 v )
	{
		if( Float.isNaN(v.x) || Float.isNaN(v.y) )
		{
			return false;
		}
		
		if( Float.isInfinite(v.x) || Float.isInfinite(v.y) )
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean Vector2IsInCenter( Vector2 v, Vector2 v1, Vector2 v2 )
	{
		float dst_12 = v1.dst(v2);
		float dst_1 = v1.dst(v);
		float dst_2 = v2.dst(v);
		
		if( dst_1>dst_12 || dst_2>dst_12 )
		{
			return false;
		}
		return true;
	}
	
	public static boolean GetIntersectionByObject(Vector2 out, Vector2 start, Vector2 end, float w, float h, GagGameObject object)
	{
		//计算最终end
		Vector2 temp_end = new Vector2(end);
		if( end.y>start.y )
		{
			temp_end.y+=(h/2);
		}else if( end.y<start.y )
		{
			temp_end.y-=(h/2);
		}
		
		if( end.x>start.x )
		{
			temp_end.x+=(w/2);
		}else if( end.x<start.x )
		{
			temp_end.x-=(w/2);
		}
		
		Vector2 LT = new Vector2( object.postion.x-object.bounds.width/2, object.postion.y+object.bounds.height/2 );
		Vector2 LB = new Vector2( object.postion.x-object.bounds.width/2, object.postion.y-object.bounds.height/2 );
		Vector2 RT = new Vector2( object.postion.x+object.bounds.width/2, object.postion.y+object.bounds.height/2 );
		Vector2 RB = new Vector2( object.postion.x+object.bounds.width/2, object.postion.y-object.bounds.height/2 );
		
		Vector2 Nearest_intersect = new Vector2(end);
		
		//得到与object上边的交点
		Vector2 T_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LT, RT, T_intersect);
		
		//得到与object下边的交点
		Vector2 B_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LB, RB, B_intersect);
		
		//得到与object左边的交点
		Vector2 L_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, LT, LB, L_intersect);
		
		//得到与object右边的交点
		Vector2 R_intersect = new Vector2();
		Intersector.intersectLines(start, temp_end, RT, RB, R_intersect);
		
		boolean bResult = false;
		//得到最近交点
		if( Vector2IsAvailability(T_intersect) && Vector2IsInCenter(T_intersect, new Vector2(LT.x-w/2, LT.y), new Vector2(RT.x+w/2, RT.y)) && Vector2IsInCenter(T_intersect, start, temp_end) )
		{
			T_intersect.y+=(h/2);
			if( T_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(T_intersect);
				bResult = true;
			}
		}
		
		if( Vector2IsAvailability(B_intersect) && Vector2IsInCenter(B_intersect, new Vector2(LB.x-w/2, LB.y), new Vector2(RB.x+w/2, RB.y)) && Vector2IsInCenter(B_intersect, start, temp_end) )	
		{
			B_intersect.y-=(h/2);
			if( B_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(B_intersect);
				bResult = true;
			}
		}
		
		if( Vector2IsAvailability(L_intersect) )
		{
			int i = 0;
			i++;
		}
		
		if( Vector2IsAvailability(L_intersect) && Vector2IsInCenter(L_intersect, LT, LB) && Vector2IsInCenter(L_intersect, start, temp_end) )
		{
			L_intersect.x-=(w/2);
			if( L_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(L_intersect);
				bResult = true;
			}
		}
		

		if( Vector2IsAvailability(R_intersect) && Vector2IsInCenter(R_intersect, RT, RB) && Vector2IsInCenter(R_intersect, start, temp_end) )
		{
			R_intersect.x+=(w/2);
			if( R_intersect.dst2(start)<Nearest_intersect.dst2(start) )
			{
				Nearest_intersect.set(R_intersect);
				bResult = true;
			}
		}
		
		out.set(Nearest_intersect);
		return bResult;
	}
	
}
