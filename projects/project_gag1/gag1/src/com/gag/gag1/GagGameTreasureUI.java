package com.gag.gag1;

import com.badlogic.gdx.math.Rectangle;

public class GagGameTreasureUI {
	public Rectangle treasuresBound;
	public int curPageIndex;
	public int pageNum;
	public int treasureNumOfOnePage;
	public GagGameTreasureUI()
	{
		treasuresBound = new Rectangle();
		curPageIndex = 0;
		treasureNumOfOnePage = 0;
		pageNum = 1;
	}
}
