package com.example.demo.notification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

import com.example.demo.R;

public class CustomFallActivity extends Activity implements
		OnClickListener, OnTouchListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 先去除应用程序标题栏 注意：一定要在setContentView之前
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ad_fall);
		Button btn_close = (Button) findViewById(R.id.close);
		btn_close.setOnClickListener(this);

		// 触摸后浏览广告
		View touchArea = findViewById(R.id.text);
		touchArea.setOnTouchListener(this);

		// int screenWidth;// 屏幕宽度
		// int screenHeight;// 屏幕高度
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// screenWidth = display.getWidth();
		// screenHeight = display.getHeight();

		overridePendingTransition(R.anim.ad_enter_top,
				R.anim.ad_exit_bottom);
	}

	@Override
	public void onClick(View paramView)
	{
		// TODO Auto-generated method stub
		switch (paramView.getId())
		{
		case R.id.close:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	{
		// TODO Auto-generated method stub
		if (paramView.getId() == R.id.text)
		{
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("http://www.e-lining.com/");
			intent.setData(content_url);

			// 启动浏览器浏览内容
			startActivity(intent);

			// 关闭广告页
			CustomFallActivity.this.finish();
		}
		else if (paramView.getId() == R.id.close)
		{
			finish();
		}

		return false;
	}
}
