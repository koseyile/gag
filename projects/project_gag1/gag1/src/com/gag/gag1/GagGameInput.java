package com.gag.gag1;

import java.util.ArrayList;
import java.util.List;

public class GagGameInput {
	public enum TouchState
	{
		TouchState_None,
		TouchState_TouchDown,
		TouchState_Touching,
		TouchState_TouchUp,
	};
	
	public class TouchInfo
	{
		public TouchInfo()
		{
			isTouched = false;
			touchTime = 0f;
			curTouchState = TouchState.TouchState_None;
		}
		public boolean isTouched;	//此属性会根据时间更新：如果一直按着屏幕，第一帧此变量会为true，随后一段时间内会变为false，如此反复
		public float touchTime;
		public TouchState curTouchState;
	};
	
	public List<TouchInfo> m_TouchInfos;
	public GagGameInput()
	{
		m_TouchInfos = new ArrayList<TouchInfo>();
		m_TouchInfos.add(new TouchInfo());
		m_TouchInfos.add(new TouchInfo());
		m_TouchInfos.add(new TouchInfo());
	}
}
