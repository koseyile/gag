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
import com.gag.gag1.GagWorld.SceneID;
import com.gag.gag1.func.GagGameRender;
import com.gag.gag1.func.GagGameTreasureUI_Func;
import com.gag.gag1.func.GagGameWorld_Func;
import com.gag.gag1.struct.GagGamePlayer;

public class GagGameScreen implements Screen {
	Game game;
	public static GagWorld m_GagWorld;

	public GagGameScreen (Game game) {
		this.game = game;
		
		GagGameWorld_Func.checkWorld();
		
		GagGameWorld_Func.initWorldConfigByGraphic();
		m_GagWorld = new GagWorld();
//		GagGameWorld_Func.setWorldBound(m_GagWorld, 0, GagGameConfig.GameBottomUIHeight, 
//												  Gdx.graphics.getWidth(), 
//												  Gdx.graphics.getHeight()-GagGameConfig.GameBottomUIHeight-GagGameConfig.GameTopUIHeight);
		
		GagGameWorld_Func.setWorldBound(m_GagWorld, 0, 0, 
											     Gdx.graphics.getWidth(), 
											     Gdx.graphics.getHeight());

		//GagGameWorld_Func.initWorldConfig(m_GagWorld);
		GagGameTreasureUI_Func.initUI(m_GagWorld);
		GagGameWorld_Func.loadScene(SceneID.SceneID_1, m_GagWorld);
	}
	
	public void update(float delta)
	{
		m_GagWorld.update(delta);
	}
	
	public void draw()
	{
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		GagGameRender.Draw();
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
