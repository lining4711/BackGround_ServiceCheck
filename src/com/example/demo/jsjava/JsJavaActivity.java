package com.example.demo.jsjava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;

@SuppressLint("JavascriptInterface") public class JsJavaActivity extends Activity
{

	private WebView contentWebView = null;
	private TextView msgView = null;

	@SuppressLint({ "SetJavaScriptEnabled"})
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_js_java);
		
		msgView = (TextView) findViewById(R.id.msg);
		
		// WebView可以讲html页面嵌到其中
		contentWebView = (WebView) findViewById(R.id.webview);
		// 启用javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
		// 从assets目录下面的加载html
		contentWebView.loadUrl("file:///android_asset/wst.html");

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(btnClickListener);
		
		// 将本类的实例this以wst的变量名称映射到html文件中作为一个window dom对象
		contentWebView.addJavascriptInterface(this, "wst");
	}

	OnClickListener btnClickListener = new Button.OnClickListener()
	{
		/**
		 * 点击按钮，执行js的方法
		 */
		public void onClick(View v)
		{
			// 无参数调用
			contentWebView.loadUrl("javascript:javacalljs()");
			// 传递参数调用
			contentWebView.loadUrl("javascript:javacalljswithargs("
					+ "'hello world'" + ")");
		}
	};

	/**
	 * 提供了共js调用的方法
	 */
	public void startFunction()
	{
		Toast.makeText(this, "js调用了java函数", Toast.LENGTH_SHORT).show();
		
		// 直接使用ui运行一个runnable接口
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				msgView.setText(msgView.getText() + "\njs调用了java函数");

			}
		});
	}

	public void startFunction(final String str)
	{
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		
		// 直接使用runOnUiThread运行一个runnable接口
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);

			}
		});
	}
}