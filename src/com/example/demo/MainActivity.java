package com.example.demo;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.demo.alarmmanager.AlarmManagerActivity;
import com.example.demo.jsjava.JsJavaActivity;
import com.example.demo.notification.CustomFallActivity;
import com.example.demo.notification.CustomUpDownActivity;
import com.example.demo.popupwindow.PopUpWindowActivity;
import com.example.demo.runningtask.TabHostListActivity;
import com.example.demo.sonfatherthread.SonFatherThreadCommuicateActivity;

public class MainActivity extends Activity implements OnClickListener
{
	private MyHandler mHandler = new MyHandler();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn1 = (Button) this.findViewById(R.id.button1);
		btn1.setOnClickListener(this);

		Button btnjs_java = (Button) this.findViewById(R.id.js_java);
		btnjs_java.setOnClickListener(this);

		Button btnson_ui_thread = (Button) this
				.findViewById(R.id.son_ui_thread);
		btnson_ui_thread.setOnClickListener(this);

		Button btnalarm_manager = (Button) this
				.findViewById(R.id.alarm_manager);
		btnalarm_manager.setOnClickListener(this);

		Button btnprc_service_activity = (Button) this
				.findViewById(R.id.prc_service_activity);
		btnprc_service_activity.setOnClickListener(this);

		Button btn_send_ad_notify = (Button) this
				.findViewById(R.id.send_ad_notify);
		btn_send_ad_notify.setOnClickListener(this);

		Button btn_send_ad_fall = (Button) this.findViewById(R.id.send_ad_fall);
		btn_send_ad_fall.setOnClickListener(this);
		Button btn_send_ad_up_down = (Button) this
				.findViewById(R.id.send_ad_up_down);
		btn_send_ad_up_down.setOnClickListener(this);

		// MulticastListener.start();
		new MutiCastSocketListenerThread(mHandler).start();
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.button1:
			// Intent popIntent = new Intent("com.example.demo.POPUP_WINDOW");
			// startActivity(popIntent);
			Intent popIntent = new Intent(this, PopUpWindowActivity.class);
			startActivity(popIntent);
			break;
		case R.id.js_java:
			Intent js_javaIntent = new Intent(this, JsJavaActivity.class);
			startActivity(js_javaIntent);
			break;
		case R.id.son_ui_thread:
			Intent son_ui_threadIntent = new Intent(this,
					SonFatherThreadCommuicateActivity.class);
			startActivity(son_ui_threadIntent);
			break;

		case R.id.alarm_manager:
			Intent alarm_managerIntent = new Intent(this,
					AlarmManagerActivity.class);
			startActivity(alarm_managerIntent);
			break;

		case R.id.prc_service_activity:
			Intent prc_service_activity = new Intent(this,
					TabHostListActivity.class);
			startActivity(prc_service_activity);
			break;

		case R.id.send_ad_notify:
			sendNotify();
			break;
		case R.id.send_ad_fall:
			showFallAD();
			break;
		case R.id.send_ad_up_down:
			// showLeftInAD();
			sendData("lining");
			break;
		default:
			break;

		}
	}

	public void sendData(String data)
	{
		try
		{
			// InetAddress ip = InetAddress.getByName(this.host);
			//
			// // 往对应的ip地址、端口上广播数据
			// DatagramPacket packet = new DatagramPacket(data.getBytes(),
			// data.length(), ip, this.port);
			//
			// MulticastSocket ms = new MulticastSocket();
			//
			// // 发送广播，能发送到所有在该端口上建立监听的？
			// ms.send(packet);
			// ms.close();
			MulticastSocket multicastSocket = new MulticastSocket();
			InetAddress address = InetAddress.getByName("239.0.0.1"); // 必须使用D类地址
			multicastSocket.joinGroup(address); // 以D类地址为标识，加入同一个组才能实现广播

			byte[] buf = data.getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
			datagramPacket.setAddress(address); // 接收地址和group的标识相同
			datagramPacket.setPort(10000); // 发送至的端口号

			multicastSocket.send(datagramPacket);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author lining
	 * 
	 * @描述 发送通知的形式发送广告</br>
	 * 
	 */
	private void sendNotify()
	{
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 实例化一个notification
		String tickerText = "李宁5周年回馈广大用户";
		long when = System.currentTimeMillis();
		// Notification notification = new Notification(
		// R.drawable.download_failed_small, tickerText, when);

		Notification notification = new Notification(R.drawable.lining_logo,
				tickerText, when);

		// 不能手动清理
		// notification.flags= Notification.FLAG_NO_CLEAR;

		// 点击后自动清除，图示项只有这样设置后才能在点击后自动删除
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// 添加音乐
		// notification.sound = Uri.parse("/sdcard/haha.mp3");

		// 通知时发出默认的声音
		notification.defaults = Notification.DEFAULT_SOUND;

		// 设置用户点击notification的动作
		// pendingIntent 延期的意图

		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse("http://www.e-lining.com/");
		intent.setData(content_url);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		notification.contentIntent = pendingIntent;

		// 自定义界面,加载任意Package下的页面
		RemoteViews rv = new RemoteViews(getPackageName(),
				R.layout.a_notification);
		rv.setTextViewText(R.id.tv_rv_title, "李宁品牌回馈新老用户");
		rv.setTextViewText(R.id.tv_rv_describe, "李宁旗舰店为了感谢广大用户，4折优惠大酬宾。点击查看详情");
		rv.setImageViewResource(R.id.pb_rv, R.drawable.lining_logo);
		// rv.setProgressBar(R.id.pb_rv, 80, 20, false);
		notification.contentView = rv;

		// 把定义的notification 传递给 notificationmanager
		notificationManager.notify(0, notification);
	}

	/**
	 * 
	 * @author lining
	 * 
	 * @描述 展示下拉广告</br>
	 * 
	 */
	private void showFallAD()
	{

		Intent intent = new Intent(this, CustomFallActivity.class);
		startActivity(intent);
	}

	/**
	 * 
	 * @author lining
	 * 
	 * @描述 展示下拉广告</br>
	 * 
	 */
	private void showLeftInAD()
	{

		Intent intent = new Intent(this, CustomUpDownActivity.class);
		startActivity(intent);
	}

	/**
	 * 
	 * 
	 * @author lining
	 * 
	 * @date 2014年11月14日
	 * 
	 * @描述 自定义内部类Handler</br>
	 * 
	 */
	private class MyHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				// Toast.makeText(context, "数据收到了", Toast.LENGTH_SHORT).show();
				sendNotify();
				break;
			case 1:
				showFallAD();
				break;
			case 2:
				showLeftInAD();
				break;
			default:
				break;
			}

		}

	}
}

class MutiCastSocketListenerThread extends Thread
{
	Handler handler = null;

	MutiCastSocketListenerThread(Handler hdler)
	{
		handler = hdler;
	}

	@Override
	public void run()
	{
		while (true)
		{
			byte[] data = new byte[256];
			MulticastSocket ms = null;
			try
			{
				InetAddress ip = InetAddress.getByName("239.0.0.1");
				ms = new MulticastSocket(10000);

				// 将该客户端加入该组
				ms.joinGroup(ip);

				DatagramPacket packet = new DatagramPacket(data, data.length);

				// receive()是阻塞方法，会等待客户端发送过来的信息
				ms.receive(packet);

				// 接收完之后，消息收集到packet中
				String message = new String(packet.getData(), 0,
						packet.getLength());
				Log.i("com.example.demo", "MutiCastSocketListenerThread" + message);
				
				int what = 0;
				if ("notify".equals(message))
				{
					what = 0;
				}
				else if ("fall".equals(message))
				{
					what = 1;
				}
				else if ("up_down".equals(message))
				{
					what = 2;
				}

				Message m = handler.obtainMessage(what, 1, 1, message);// 构造要传递的消息
				handler.sendMessage(m);

				Log.i("com.example.demo", message);

			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				ms.close();
			}
		}
	}

}
