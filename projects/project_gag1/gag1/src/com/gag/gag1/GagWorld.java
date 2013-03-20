package com.gag.gag1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.gag.gag1.func.GagGameObject_Func;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.struct.GagGamePlayer;

public class GagWorld {
	
	public GagGamePlayer m_Player;
	
	public GagWorld()
	{
		m_Player = new GagGamePlayer();
		m_Player.postion.x = 16;
		m_Player.postion.y = 100;
	}
	
	void update(float delta)
	{
		ApplicationType appType = Gdx.app.getType();
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS)
		{
			GagGameObject_Func.UpdatePlayerPosByTouch(Gdx.input.isTouched(), Gdx.input.getX(), Gdx.graphics.getWidth(), m_Player);
			GagGameObject_Func.UpdatePlayerPosByAccelerometerY(Gdx.input.getAccelerometerY(), m_Player);
		}else{
			int key = Keys.ANY_KEY;
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				key = Keys.RIGHT;
			}
			
			if (Gdx.input.isKeyPressed(Keys.LEFT))
			{
				key = Keys.LEFT;
			}
			
			GagGameObject_Func.UpdatePlayerPosByKeyboard(key, m_Player);
		}
		
		GagGameObject_Func.UpdatePlayerAnimation(m_Player, delta);
	}
	
}
