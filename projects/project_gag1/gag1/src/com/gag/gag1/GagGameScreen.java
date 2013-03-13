package com.gag.gag1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gag.gag1.GagGameScreen.MoveState;

public class GagGameScreen implements Screen {

	Game game;
	SpriteBatch batcher;
	OrthographicCamera guiCam;
	float stateTime;
	enum MoveState
	{
		MoveState_Stand,
		MoveState_Left,
		MoveState_Right,
	};
	MoveState m_CurMoveState;
	int m_LeftOrRight;
	float m_PlyerDisWidth;
	float m_PlyerDisHeight;
	float m_PlayerPosX;
	float m_PlayerPosY;
	
	public GagGameScreen (Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		batcher = new SpriteBatch();

		m_CurMoveState = MoveState.MoveState_Stand;
		m_LeftOrRight = 1;
		m_PlyerDisWidth = 32f;
		m_PlyerDisHeight = 32f;
		m_PlayerPosX = 16f;
		m_PlayerPosY = 16f;
	}
	
	public void update(float delta)
	{
		stateTime+=delta;
		
		boolean bMoveRight = false;
		boolean bMoveLeft = false;
		
		ApplicationType appType = Gdx.app.getType();
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) {

			//if (Gdx.input.justTouched())
			if (Gdx.input.isTouched())
			{
				float gw = Gdx.graphics.getWidth();
				float gh = Gdx.graphics.getHeight();	
				
				float tx = Gdx.input.getX();
				float ty = Gdx.input.getY();
				ty = gh-ty;
				
				if( tx>(gw/2) )
				{
					bMoveRight = true;
				}else{
					bMoveLeft = true;
				}
			}
			
		} else {
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
			{
				bMoveRight = true;
			}
			
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
			{
				bMoveLeft = true;
			}
			
		}
		
		m_CurMoveState = MoveState.MoveState_Stand;
		
		if(bMoveRight)
		{
			m_CurMoveState = MoveState.MoveState_Right;
			m_LeftOrRight = 1;
			m_PlayerPosX+=1f;	
		}
		
		if(bMoveLeft)
		{
			m_CurMoveState = MoveState.MoveState_Left;
			m_LeftOrRight = -1;
			m_PlayerPosX-=1f;
		}
	}
	
	public void draw()
	{
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		batcher.disableBlending();
		batcher.begin();
		batcher.draw(Assets.backgroundRegion, 0, 0, guiCam.viewportWidth, guiCam.viewportHeight);
		batcher.end();
		
		batcher.enableBlending();
		batcher.begin();
		//batcher.draw(Assets.logo, (guiCam.viewportWidth - 274) / 2, guiCam.viewportHeight - 10 - 142, 274, 142);
		//batcher.draw(Assets.testTex, 128, 0, 128, 128);
		//batcher.draw(Assets.testTex, 128, 0, -128, 128);
		
		TextureRegion keyFrame;
		
		Animation animation = Assets.bobFall;
		switch( m_CurMoveState )
		{
			case MoveState_Stand:
				{
					animation = Assets.bobFall;
				}
				break;
			case MoveState_Left:
			case MoveState_Right:
				{
					animation = Assets.bobJump;
				}
				break;
		}
		
		keyFrame = animation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		float x = m_PlayerPosX;
		float y = m_PlayerPosY;
		
		if( m_LeftOrRight<0 )
		{
			x+=(m_PlyerDisWidth/2);
			y-=(m_PlyerDisHeight/2);
		}else{
			x-=(m_PlyerDisWidth/2);
			y-=(m_PlyerDisHeight/2);
		}
		
		batcher.draw(keyFrame,  x, y, m_PlyerDisWidth*m_LeftOrRight, m_PlyerDisHeight);
		
		batcher.end();		
	}
	
	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	@Override
	public void resize (int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose () {
		// TODO Auto-generated method stub
		
	}

}
