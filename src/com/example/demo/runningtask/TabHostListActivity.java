package com.example.demo.runningtask;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.example.demo.R;

public class TabHostListActivity extends TabActivity 
//	implements	android.widget.CompoundButton.OnCheckedChangeListener
{
	TabHost tabHost = null;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.layout_tabhost_listactivity);

		// 获取当前activity的标签,该方法的实现中已经执行了setContentView(com.android.internal.R.layout.tab_content);
		 tabHost = getTabHost();

		// 使用了android系统的id
//		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		// tabHost.setup();

		// Intent intent1 = new Intent(this, ListV.class);
		// tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("p1").setContent(intent1));
		// tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("p2").setContent(intent1));

		// tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		
		tabHost.addTab(tabHost.newTabSpec("process").setIndicator("后台进程")
				.setContent(new Intent(this, ProcMgrActivity.class)));

		tabHost.addTab(tabHost.newTabSpec("service")
				.setIndicator("后台服务")
				.setContent(new Intent(this, ServiceMgrActivity.class)));
		
		tabHost.addTab(tabHost.newTabSpec("runningapp")
				.setIndicator("后台应用")
				.setContent(new Intent(this, TaskMgrActivity.class)));
		// tabHost.setCurrentTabByTag("process");
	}

//	private void initRadios()
//	{
//		((RadioButton) findViewById(R.id.main_index_button))
//				.setOnCheckedChangeListener(this);
//		((RadioButton) findViewById(R.id.main_running_button))
//				.setOnCheckedChangeListener(this);
//		((RadioButton) findViewById(R.id.main_uninstall_button))
//				.setOnCheckedChangeListener(this);
//	}
//
//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//	{
//		if (isChecked)
//		{
//			switch (buttonView.getId())
//			{
//			case R.id.main_index_button:
//				tabHost.setCurrentTabByTag("process");
//				break;
//			case R.id.main_running_button:
//				tabHost.setCurrentTabByTag("RunManager");
//				break;
//			case R.id.main_uninstall_button:
//				tabHost.setCurrentTabByTag("UninstallManager");
//				break;
//			}
//		}
//	}

}