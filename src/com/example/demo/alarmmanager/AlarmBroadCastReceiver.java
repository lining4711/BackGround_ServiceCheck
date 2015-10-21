package com.example.demo.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 *
 * @author  lining
 * 
 * @date    2014年10月22日
 * 
 * @描述    对于广播而言，只要在Manisfast.xml文件中注册，设置了Filter，
 *        不管程序是否启动，都会捕获响应的广播
 *
 */
public class AlarmBroadCastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
//		Toast.makeText(context, "single", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		if (intent.getAction().equals("single"))
		{
			Log.i("AlarmBroadCastReceiver", "single");
			Toast.makeText(context, "single", Toast.LENGTH_SHORT).show();
		}
		else if(intent.getAction().equals("repeating"))
		{
			Log.i("AlarmBroadCastReceiver", "repeating");
			Toast.makeText(context, "repeating", Toast.LENGTH_SHORT)
					.show();
		}
		else if(intent.getAction().equals("repeating2"))
		{
			Log.i("AlarmBroadCastReceiver", "repeating2");
			Toast.makeText(context, "repeating2", Toast.LENGTH_SHORT)
					.show();
		}

	}

}
