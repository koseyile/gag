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
		public boolean isTouched;	//�����Ի����ʱ����£����һֱ������Ļ����һ֡�˱�����Ϊtrue�����һ��ʱ���ڻ��Ϊfalse����˷���
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
