package com.gag.gag1.struct;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GagGameObject {
	
	public enum ObjectType
	{
		ObjectType_Ojbect,
		ObjectType_Plaform,
		ObjectType_Door,
		ObjectType_Treasure,
		ObjectType_Box,
		ObjectType_Player,
	};
	
	public final Vector2 postion;
	public final Rectangle bounds;
	public float downSpeed;
	public boolean canBeDown;
	public boolean beDown;
	public float downScaleByWorldG;
	public ObjectType objectType;
	
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
		objectType = ObjectType.ObjectType_Ojbect;
		canBeDown = false;
		beDown = false;
		downScaleByWorldG = 1f;
	}
}
