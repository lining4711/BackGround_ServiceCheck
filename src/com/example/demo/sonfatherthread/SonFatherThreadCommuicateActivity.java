package com.example.demo.sonfatherthread;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;

/**
 * 
 * 
 * @author lining
 * 
 * @date 2014年10月17日
 * 
 * @描述 测试工作线程刷新界面。实际上是主线程刷新的界面。 方法包含：1.view的post方法 2、Activity的runOnUiThread()
 *     3.handle 的post方法
 * 
 */
public class SonFatherThreadCommuicateActivity extends Activity
{
	private TextView txView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i("RootyInfo", "oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_work_ui_thread_activity);

		txView = (TextView) findViewById(R.id.textshow);
		Button button1 = (Button) findViewById(R.id.myButton1);
		Button button2 = (Button) findViewById(R.id.myButton2);
		button1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 创建一个用于显现前三种后台线程和UI线程交互的线程
				new TestThread(SonFatherThreadCommuicateActivity.this).start();
			}
		});

		button2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 创建一个用于显现AsyncTask实现交互的TestAsyncTask
				new TestAsyncTask().execute("Test", " AsyncTask");
			}
		});
	}

	class TestAsyncTask extends AsyncTask<String, Integer, String>
	{
		// TestAsyncTask被后台线程履行后,被UI线程被调用,一般用于初始化界面控件,如进度条
		// 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		// doInBackground履行完后由UI线程调用,用于更新界面操纵
		/**
		 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
		 * 在doInBackground方法执行结束之后在运行，UI线程执行onPostExecute ()方法
		 */
		@Override
		protected void onPostExecute(String result)
		{
			// TODO Auto-generated method stub
			txView.setText(result);
			super.onPostExecute(result);
		}

		/**
		 * 这里的Intege参数对应AsyncTask中的第二个参数
		 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
		 * onProgressUpdate是在UI线程中执行，所以可以对UI空间进行操作
		 */
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			// txView.setText(values[0]);
			super.onProgressUpdate(values);
		}

		// 在PreExcute履行后被启动AysncTask的后台线程调用,将成果返回给UI线程
		/**
		 * 这里的String参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
		 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
		 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
		 * 
		 * 这个方法的参数来自于TestAsyncTask的excute()方法的调用
		 */
		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			StringBuffer sb = new StringBuffer();
			// int i = 1111;
			for (String string : params)
			{
				// publishProgress(i++);
				// try
				// {
				// TimeUnit.SECONDS.sleep(1);
				// } catch (InterruptedException e)
				// {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				sb.append(string);
			}

			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}

	}

	// 用于线程间通信的Handler
	class TestHandler extends Handler
	{
		public TestHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{
			System.out.println("123");
			txView.setText((String) msg.getData().get("tag"));
			super.handleMessage(msg);
		}

	}

	// 后台线程类
	class TestThread extends Thread
	{
		Activity activity;

		public TestThread(Activity activity)
		{
			this.activity = activity;
		}

		@Override
		public void run()
		{
			// 下面代码用来演示Activity.runOnUIThread(Runnable)办法的实现
			activity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					txView.setText("Test runOnUIThread:"
							+ Thread.currentThread().getId());
				}
			});

			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 下面代码用来演示Activity.runOnUIThread(Runnable)办法的实现
			txView.post(new Runnable()
			{
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					txView.setText("Test View.post(Runnable):"
							+ Thread.currentThread().getId());
				}
			});

			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 下面代码用来演示Activity.runOnUIThread(Runnable)办法的实现
			txView.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					txView.setText("Test View.postDelay(Runnable,long):"
							+ Thread.currentThread().getId());
				}
			}, 4000);

			try
			{
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 下面代码用来演示Handler办法的实现
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("tag", "Test Handler");
			msg.setData(bundle);

			// 将消息发送到住Looper对应的队列
			new TestHandler(Looper.getMainLooper()).sendMessage(msg);

			super.run();
		}

	}

}