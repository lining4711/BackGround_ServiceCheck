package com.example.demo.runningtask;

/**
 * ActivityManager.RunningAppProcessInfo {
 *     public int importance                // 进程在系统中的重要级别
 *     public int importanceReasonCode        // 进程的重要原因代码
 *     public ComponentName importanceReasonComponent    // 进程中组件的描述信息
 *     public int importanceReasonPid        // 当前进程的子进程Id
 *     public int lru                        // 在同一个重要级别内的附加排序值
 *     public int pid                        // 当前进程Id
 *     public String[] pkgList                // 被载入当前进程的所有包名
 *     public String processName            // 当前进程的名称
 *     public int uid                        // 当前进程的用户Id
 * }
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.demo.R;

public class ProcMgrActivity extends ListActivity
{

	private static List<RunningAppProcessInfo> procList = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.layout_listitem);

		procList = new ArrayList<RunningAppProcessInfo>();
		getProcessInfo();

		showProcessInfo();
	}

	public void showProcessInfo()
	{

		// 更新进程列表
		List<HashMap<String, String>> infoList = new ArrayList<HashMap<String, String>>();
		for (Iterator<RunningAppProcessInfo> iterator = procList.iterator(); iterator
				.hasNext();)
		{
			RunningAppProcessInfo procInfo = iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("proc_name", procInfo.processName);
			map.put("proc_id", procInfo.pid + "");
			infoList.add(map);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,
				R.layout.layout_listitem, new String[]
				{ "proc_name", "proc_id" }, new int[]
				{ R.id.listitem_title, R.id.task_id });
		setListAdapter(simpleAdapter);
	}

	public int getProcessInfo()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		procList = activityManager.getRunningAppProcesses();
		return procList.size();
	}
}