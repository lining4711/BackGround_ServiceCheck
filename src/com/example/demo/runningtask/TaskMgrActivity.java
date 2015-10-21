package com.example.demo.runningtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.demo.R;

/**
 * 
 * 
 * @author lining
 * 
 * @date 2014年10月23日
 * 
 * @描述 获取后台运行的任务栈，需要一定的权限
 * 
 */
public class TaskMgrActivity extends ListActivity
{

	private static List<RunningTaskInfo> taskList = null;
	private static final int maxTaskNum = 100;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_listitem);

		taskList = getTaskInfo();

		showTaskInfo();
	}

	public void showTaskInfo()
	{

		// 更新进程列表
		List<HashMap<String, String>> infoList = new ArrayList<HashMap<String, String>>();
		for (Iterator<RunningTaskInfo> iterator = taskList.iterator(); iterator
				.hasNext();)
		{
			RunningTaskInfo taskInfo = iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("task_name", taskInfo.baseActivity.toString());
			map.put("task_id", taskInfo.topActivity.toString());
			infoList.add(map);
		}
		//
		// SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,
		// R.layout.layout_listitem, new String[]
		// { "task_name", "task_id" }, new int[]
		// { R.id.listitem_title, R.id.task_id });
		// setListAdapter(simpleAdapter);

		setListAdapter(new MySimpleAdapter(this, infoList));
	}

	public ArrayList<RunningTaskInfo> getTaskInfo()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		return (ArrayList<RunningTaskInfo>) activityManager
				.getRunningTasks(maxTaskNum);

	}

	class MySimpleAdapter extends BaseAdapter
	{
		private Context context;
		private List<HashMap<String, String>> infoList;
		private LayoutInflater inflater;

		public MySimpleAdapter(Context context,
				List<HashMap<String, String>> infoList)
		{
			super();
			this.context = context;
			this.infoList = infoList;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return infoList.size();
		}

		@Override
		public Object getItem(int position)
		{
			return infoList.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2)
		{
			if (convertView == null)
			{
				// 获得行显示信息
				HashMap<String, String> item = infoList.get(position);
				// 行布局模板
				convertView = inflater.inflate(R.layout.layout_listitem,
						null);
				// 打到行布局模板中要显示的控件
				TextView title = (TextView) convertView
						.findViewById(R.id.listitem_title);
				TextView content = (TextView) convertView
						.findViewById(R.id.task_id);
				
				title.setText((String)item.get("task_name"));
				content.setText((String)item.get("task_id"));
				
				final RunningTaskInfo runningTaskInfo = taskList.get(position);
				 ((Button) convertView.findViewById(R.id.kill_service)).setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View arg0)
						{
							// TODO Auto-generated method stub
							 int pid = android.os.Process.getUidForName("com.kuaihuoyun.freight.service.PushService");
							 android.os.Process.killProcess(pid);
						}
					});
			}
			return convertView;
		}
	}

}
