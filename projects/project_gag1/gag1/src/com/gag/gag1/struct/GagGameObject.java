package com.gag.gag1.struct;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GagGameObject {
	public final Vector2 postion;
	public final Rectangle bounds;
	public float downSpeed;
	
	static final float DEFAULT_X = 0;
	static final float DEFAULT_Y = 0;
	static final float DEFAULT_WIDTH = 32;
	static final float DEFAULT_HEIGHT = 32;
	
	public GagGameObject()
	{
		postion = new Vector2(DEFAULT_X, DEFAULT_Y);
		bounds = new Rectangle(0, 0, 
									  DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		downSpeed = 0f;
	}
}
