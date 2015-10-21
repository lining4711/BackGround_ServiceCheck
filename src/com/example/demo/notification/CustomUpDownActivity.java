package com.example.demo.notification;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.example.demo.R;

public class CustomUpDownActivity extends Activity implements OnClickListener,
		OnTouchListener
{
	View mAd_up = null;
	View mAd_down = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 先去除应用程序标题栏 注意：一定要在setContentView之前
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ad_up_down);
		Button btn_close_up = (Button) findViewById(R.id.close1);
		btn_close_up.setOnClickListener(this);
		Button btn_close_down = (Button) findViewById(R.id.close2);
		btn_close_down.setOnClickListener(this);

		mAd_up = findViewById(R.id.ad1);
		mAd_down = findViewById(R.id.ad2);
		
		// 触摸后浏览广告
		View touchArea_up = mAd_up.findViewById(R.id.text1);
		touchArea_up.setOnTouchListener(this);
		
		View touchArea_down = mAd_down.findViewById(R.id.text2);
		touchArea_down.setOnTouchListener(this);

		View touchArea_ad_blank = findViewById(R.id.ad_blank);
		touchArea_ad_blank.setOnTouchListener(this);
		
		// int screenWidth;// 屏幕宽度
		// int screenHeight;// 屏幕高度
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// screenWidth = display.getWidth();
		// screenHeight = display.getHeight();

		overridePendingTransition(R.anim.ad_enter_left, R.anim.ad_exit_right);
	}

	@Override
	public void onClick(View paramView)
	{
		// TODO Auto-generated method stub
		switch (paramView.getId())
		{
		case R.id.close1:
			mAd_up.setVisibility(View.INVISIBLE);
			if (mAd_down.getVisibility() == View.INVISIBLE)
			{
				// 关闭广告Activity
				finish();	
			}
			
			break;
			
		case R.id.close2:
			mAd_down.setVisibility(View.INVISIBLE);
			if (mAd_up.getVisibility() == View.INVISIBLE)
			{
				// 关闭广告Activity
				finish();	
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
	{
		// TODO Auto-generated method stub
		if (paramView.getId() == R.id.text1)
		{
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("http://www.jd.com/");
			intent.setData(content_url);

			// 启动浏览器浏览内容
			startActivity(intent);

			// 关闭广告页
			CustomUpDownActivity.this.finish();
		}
		else if (paramView.getId() == R.id.text2)
		{
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse("http://www.suning.com/");
			intent.setData(content_url);

			// 启动浏览器浏览内容
			startActivity(intent);

			// 关闭广告页
			CustomUpDownActivity.this.finish();
		}
		else if(paramView.getId() == R.id.ad_blank)
		{
			finish();
		}

		return false;
	}
}
