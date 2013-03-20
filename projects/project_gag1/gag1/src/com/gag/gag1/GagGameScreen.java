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
import com.badlogicgames.superjumper.Animation;
import com.badlogicgames.superjumper.Assets;
import com.gag.gag1.GagGameScreen.MoveState;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.struct.GagGamePlayer;

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
	int m_PlayerFaceToRight;
	float m_PlyerDisWidth;
	float m_PlyerDisHeight;
	float m_PlayerPosX;
	float m_PlayerPosY;
	
	float m_WordMinX;
	float m_WordMinY;
	float m_WordMaxX;
	float m_WordMaxY;
	
	float m_Word_g;
	float m_PlayerDownSpeed;
	
	public static GagWorld m_GagWorld;
	
	public GagGameScreen (Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		batcher = new SpriteBatch();

		m_CurMoveState = MoveState.MoveState_Stand;
		m_PlayerFaceToRight = 1;
		m_PlyerDisWidth = 32f;
		m_PlyerDisHeight = 32f;
		m_PlayerPosX = 16f;
		m_PlayerPosY = 100f;

		m_WordMinX = 0f;
		m_WordMinY = 0f;
		m_WordMaxX = Gdx.graphics.getWidth();
		m_WordMaxY = Gdx.graphics.getHeight();
		
		m_Word_g = 1.0f;
		m_PlayerDownSpeed = 0.0f;
		
		m_GagWorld = new GagWorld();
		//m_GagWorld = null;
	}
	
	public void update(float delta)
	{
		if( m_GagWorld!=null )
		{
			m_GagWorld.update(delta);
		}
		
		stateTime+=delta;
		
		boolean bMoveRight = false;
		boolean bMoveLeft = false;
		
		ApplicationType appType = Gdx.app.getType();
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) 
		{
			float AX = Gdx.input.getAccelerometerX();
			float AY = Gdx.input.getAccelerometerY();
			float AZ = Gdx.input.getAccelerometerZ();
			
			if( AY<-2f )
			{
				bMoveLeft = true;
			}
			
			if( AY>2f )
			{
				bMoveRight = true;
			}
			
			if( AX>5f )
			{
				m_Word_g=Math.abs(m_Word_g);
			}
			
			if( AX<-5f )
			{
				m_Word_g=-Math.abs(m_Word_g);
			}
			
			
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
				
				Gdx.app.log("GAG", "AX : " + AX);
				Gdx.app.log("GAG", "AY : " + AY);
				Gdx.app.log("GAG", "AZ : " + AZ);
				
			}
			
		} else {
			
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				bMoveRight = true;
			}
			
			if (Gdx.input.isKeyPressed(Keys.LEFT))
			{
				bMoveLeft = true;
			}
			
			if (Gdx.input.isKeyPressed(Keys.DOWN))
			{
				m_Word_g=Math.abs(m_Word_g);
			}		
			
			if (Gdx.input.isKeyPressed(Keys.UP))
			{
				m_Word_g=-Math.abs(m_Word_g);
			}
			
		}
		
		m_CurMoveState = MoveState.MoveState_Stand;
		
		if(bMoveRight)
		{
			m_CurMoveState = MoveState.MoveState_Right;
			m_PlayerFaceToRight = 1;
			m_PlayerPosX+=2f;	
		}
		
		if(bMoveLeft)
		{
			m_CurMoveState = MoveState.MoveState_Left;
			m_PlayerFaceToRight = -1;
			m_PlayerPosX-=2f;
		}
		
		m_PlayerDownSpeed+=m_Word_g;
		m_PlayerPosY-=m_PlayerDownSpeed;
		
		if( m_PlayerPosX<(m_WordMinX+m_PlyerDisWidth/2) )
		{
			m_PlayerPosX = (m_WordMinX+m_PlyerDisWidth/2);
		}
		
		if( m_PlayerPosX>(m_WordMaxX-m_PlyerDisWidth/2) )
		{
			m_PlayerPosX = (m_WordMaxX-m_PlyerDisWidth/2);
		}
		
		if( m_PlayerPosY<(m_WordMinY+m_PlyerDisHeight/2) )
		{
			m_PlayerPosY = (m_WordMinY+m_PlyerDisHeight/2);
			m_PlayerDownSpeed = 0.0f;
		}
		
		if( m_PlayerPosY>(m_WordMaxY-m_PlyerDisHeight/2) )
		{
			m_PlayerPosY = (m_WordMaxY-m_PlyerDisHeight/2);
			m_PlayerDownSpeed = 0.0f;
		}
		
	}
	
	public void draw()
	{
		GLCommon gl = Gdx.gl;
		
		if(m_GagWorld!=null)
		{
			gl.glClearColor(1, 0, 0, 1);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GagGameRender.Draw();
			return;			
		}


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
		
		if( m_PlayerFaceToRight<0 )
		{
			x+=(m_PlyerDisWidth/2);
		}else{
			x-=(m_PlyerDisWidth/2);
		}
		
		int gIsDown = m_Word_g>0f ? 1 : -1;
		
		if( gIsDown<0 )
		{
			y+=(m_PlyerDisHeight/2);
		}else{
			y-=(m_PlyerDisHeight/2);
		}
		
		batcher.draw(keyFrame,  x, y, m_PlyerDisWidth*m_PlayerFaceToRight, m_PlyerDisHeight*gIsDown);
		
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
