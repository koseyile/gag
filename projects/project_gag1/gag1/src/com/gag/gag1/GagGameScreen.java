package com.gag.gag1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GagGameScreen implements Screen {

	Game game;
	SpriteBatch batcher;
	OrthographicCamera guiCam;
	float stateTime;
	
	public GagGameScreen (Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		guiCam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		batcher = new SpriteBatch();

	}
	
	@Override
	public void render (float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
//		batcher.disableBlending();
//		batcher.begin();
//		batcher.draw(Assets.backgroundRegion, 0, 0, guiCam.viewportWidth, guiCam.viewportHeight);
//		batcher.end();
		
		batcher.enableBlending();
		batcher.begin();
//		batcher.draw(Assets.logo, (guiCam.viewportWidth - 274) / 2, guiCam.viewportHeight - 10 - 142, 274, 142);
		
		TextureRegion keyFrame;
		stateTime+=delta;
		keyFrame = Assets.bobFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		float bobX = (Gdx.graphics.getWidth()-keyFrame.getRegionWidth())/2;
		float bobY = (Gdx.graphics.getHeight()-keyFrame.getRegionHeight())/2;
		//batcher.draw(keyFrame,  bobX+0.5f, bobY-0.5f, -32, 32);
		batcher.draw(keyFrame,  0, 0, 64, 64);
		
		batcher.end();
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
