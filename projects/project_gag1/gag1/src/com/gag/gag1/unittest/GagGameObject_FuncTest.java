package com.gag.gag1.unittest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameObject_Func.InputState;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameObject_FuncTest {

	@Test
	public void test_UpdatePlayerPosByInputState()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByInputState(InputState.InputState_None, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByInputState(InputState.InputState_Left, player);
		if( player.postion.x!=-GagGamePlayer.MoveLeftDistance )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Left )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurReverseX!=true )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByInputState(InputState.InputState_Right, player);
		if( player.postion.x!=GagGamePlayer.MoveRightDistance )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
		
		if( player.CurReverseX!=false )
		{
			fail("test failed by UpdatePlayerPosByInput_test");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByTouch()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		
		GagGameObject_Func.UpdatePlayerPosByTouch(false, 0f, 640f, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByTouch(true, 0f, 640f, player);
		if( player.postion.x!=-GagGamePlayer.MoveLeftDistance )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByTouch(true, 640f, 640f, player);
		if( player.postion.x!=GagGamePlayer.MoveRightDistance )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.UpdatePlayerPosByTouch(true, 640f, 640f, player);
		if( player.postion.x!=(10f+GagGamePlayer.MoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByInput");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByKeyboard()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.postion.x = 0f;
		
		GagGameObject_Func.UpdatePlayerPosByKeyboard(Keys.SPACE, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByKeyboard(Keys.LEFT, player);
		if( player.postion.x!=-GagGamePlayer.MoveLeftDistance )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Left )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByKeyboard(Keys.RIGHT, player);
		if( player.postion.x!=GagGamePlayer.MoveRightDistance )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.UpdatePlayerPosByKeyboard(Keys.RIGHT, player);
		if( player.postion.x!=(10f+GagGamePlayer.MoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Right )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		player.postion.x = 0f;
		GagGameObject_Func.UpdatePlayerPosByKeyboard(Keys.SPACE, player);
		if( player.postion.x!=0f )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
		if( player.CurMoveState!=GagGamePlayer.MoveState.MoveState_Stand )
		{
			fail("test failed by test_UpdatePlayerPosByKeyboard");
		}
		
	}

	@Test
	public void test_UpdatePlayerAnimation()
	{
		GagGamePlayer player = new GagGamePlayer();
		player.PassTime = 0f;
		GagGameObject_Func.UpdatePlayerAnimation(player, 10f);
		
		if( player.PassTime!=10f )
		{
			fail("test failed by test_UpdatePlayerAnimation");
		}
		
		GagGameObject_Func.UpdatePlayerAnimation(player, 5f);
		if( player.PassTime!=15f )
		{
			fail("test failed by test_UpdatePlayerAnimation");
		}
	}
	
	@Test
	public void test_UpdatePlayerPosByAccelerometerY()
	{
		GagGamePlayer player = new GagGamePlayer();
		
		player.postion.x = 10f;
		GagGameObject_Func.UpdatePlayerPosByAccelerometerY(2.1f, player);
		if( player.postion.x!=(10f+GagGamePlayer.MoveRightDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByAccelerometerY");
		}
		
		player.postion.x = 10f;
		GagGameObject_Func.UpdatePlayerPosByAccelerometerY(-2.1f, player);
		if( player.postion.x!=(10f-GagGamePlayer.MoveLeftDistance) )
		{
			fail("test failed by test_UpdatePlayerPosByAccelerometerY");
		}
		
	}

}
