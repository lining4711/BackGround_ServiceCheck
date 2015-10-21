package com.example.demo.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.demo.R;

public class PopUpWindowActivity extends Activity
{
	/** Called when the activity is first created. */
	Button mMyButton;

	PopupWindow mPopupWindow;

	View myView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_popupwindow_activity);

		// 加载页面内容
		myView = getLayoutInflater().inflate(R.layout.layout_popupwindow_pop,
				null);
		// 为页面视图设置背景颜色
		myView.setBackgroundResource(R.drawable.layout_popupwindow_round_win);
		
		// 获取焦点，指的是当前的页面焦点在该view上，点击back键返回时，进进退出该页面。
		// 加入没有获得焦点，点击返回按键时，直接退出View所在的Activity
		mPopupWindow = new PopupWindow(myView, 1000, 1000, true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		
		// 注意，返回按键响应之后，mPopupWindow对象仅仅被隐藏，并非销毁
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable()); // www.linuxidc.com响应返回键必须的语句
		
//		要展示的是一个弹出组件，弹出组件设置动画即可，并非对弹出组件内的view设置动画。
		mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		
		mMyButton = (Button) findViewById(R.id.myButton);
		mMyButton.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mPopupWindow.showAsDropDown(mMyButton, 20, 20);
			}

		});

	}

}
