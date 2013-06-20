package com.gag.gag1;

import com.badlogic.gdx.math.Rectangle;

public class GagGameUI {
	public Rectangle treasuresBound;
	public int curPageIndex;
	public int pageNum;
	public int treasureNumOfOnePage;
	public GagGameUI()
	{
		treasuresBound = new Rectangle();
		curPageIndex = 0;
		treasureNumOfOnePage = 0;
		pageNum = 1;
	}
}
