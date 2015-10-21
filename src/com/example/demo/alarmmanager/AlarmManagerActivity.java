package com.example.demo.alarmmanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.demo.R;

public class AlarmManagerActivity extends Activity
{
	private AlarmManager am = null;
//	Calendar calendar = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_js_java);

		// 设定一个五秒后的时间
//		calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.add(Calendar.SECOND, 5);

		am = (AlarmManager) getSystemService(ALARM_SERVICE);

		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction("single");
		// intentFilter.addAction("repeating");
		//
		// registerReceiver(new AlarmBroadCastReceiver(), intentFilter);

		// 对于闹钟，repeating以及set之间不相互影响。
		// 但是repeating或者set 重复设置各自，会覆盖掉之前的设置
		initSetRepeatingAlarm();
		initSetAlarm();
		initSetRepeatingAlarm2();
	}

	private void initSetAlarm()
	{
		Intent intent = new Intent(/*this, AlarmBroadCastReceiver.class*/);
		intent.setAction("single");
		// sendBroadcast(intent);

		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
				0);

		// 5秒一个周期，不停的发送广播
		am.set(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + 7000, sender);
	}

	private void initSetRepeatingAlarm()
	{
		Intent intent = new Intent(/*this, AlarmBroadCastReceiver.class*/);
		intent.setAction("repeating");
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
				0);

		am.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 4000, sender);

	}
	private void initSetRepeatingAlarm2()
	{
		// Intent不一样，就不会覆盖之前设置的 AlarmManager
		Intent intent = new Intent(/*this, AlarmBroadCastReceiver.class*/);
		intent.setAction("repeating2");
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
				0);

		am.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 2000, sender);

	}
}
