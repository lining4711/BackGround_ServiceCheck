package com.example.demo.runningtask;

/**
 * ActivityManager.RunningServiceInfo {
 *     public long activeSince        // 服务第一次被激活的时间 (启动和绑定方式)
 *     public int clientCount        // 连接到该服务的客户端数目
 *     public int clientLabel        // 【系统服务】为客户端程序提供用于访问标签
 *     public String clientPackage    // 【系统服务】绑定到该服务的包名
 *     public int crashCount        // 服务运行期间，出现crash的次数
 *     public int flags                // 服务运行的状态标志
 *     public boolean foreground    // 服务是否被做为前台进程执行
 *     public long lastActivityTime    // 该服务的最后一个活动的时间
 *     public int pid                // 非0值，表示服务所在的进程Id
 *     public String process        // 服务所在的进程名称
 *     public long restarting        // 如果非0，表示服务没有执行，将在参数给定的时间点重启服务
 *     public ComponentName service    // 服务组件信息
 *     public boolean started        // 标识服务是否被显示的启动
 *     public int uid                // 拥有该服务的用户Id
 * }
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.runningtask.TaskMgrActivity.MySimpleAdapter;

public class ServiceMgrActivity extends ListActivity
{
	private static List<RunningServiceInfo> serviceList = null;
	private static final int maxServiceNum = 100;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_listitem);

		serviceList = new ArrayList<RunningServiceInfo>();
		getServiceInfo();

		showServiceInfo();
	}

	public void showServiceInfo()
	{

		// 更新进程列表
		List<HashMap<String, Object>> infoList = new ArrayList<HashMap<String, Object>>();
		for (Iterator<RunningServiceInfo> iterator = serviceList.iterator(); iterator
				.hasNext();)
		{
			RunningServiceInfo serviceInfo = iterator.next();
			if (isUserApp(serviceInfo.service.getPackageName()))
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("service_name", serviceInfo.service.toString());
				map.put("service_id", serviceInfo.clientCount + "");
				map.put("runningServiceInfo", serviceInfo);
				infoList.add(map);
			}
		}

		// 用infoList填充那个页面的那些字段
//		SimpleAdapter simpleAdapter = new SimpleAdapter(this, infoList,
//				R.layout.layout_listitem, new String[]
//				{ "service_name", "service_id" }, new int[]
//				{ R.id.listitem_title, R.id.task_id });
//		setListAdapter(simpleAdapter);
		setListAdapter(new MySimpleAdapter(this, infoList));
	}

	private boolean isUserApp(String packageName)
	{
		PackageInfo pinfo = null;
		try
		{
			pinfo = this.getPackageManager().getPackageInfo(
					packageName, 0);
		} catch (NameNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("服务对应的应用不存在");
		}
		ApplicationInfo applicationInfo = pinfo.applicationInfo;
		// 用户安装过的APP
		if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
		{
			return true;
		}

		return false;
	}

	public int getServiceInfo()
	{
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		serviceList = activityManager.getRunningServices(maxServiceNum);
		return serviceList.size();
	}
	
	class MySimpleAdapter extends BaseAdapter
	{
		private Context context;
		private List<HashMap<String, Object>> infoList;
		private LayoutInflater inflater;

		public MySimpleAdapter(Context context,
				List<HashMap<String, Object>> infoList)
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
				final HashMap<String, Object> item = infoList.get(position);
				// 行布局模板
				convertView = inflater.inflate(R.layout.layout_listitem,
						null);
				// 打到行布局模板中要显示的控件
				TextView title = (TextView) convertView
						.findViewById(R.id.listitem_title);
				TextView content = (TextView) convertView
						.findViewById(R.id.task_id);
				
				title.setText((String)item.get("service_name"));
				content.setText((String)item.get("service_id"));
				
				 ((Button) convertView.findViewById(R.id.kill_service)).setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View arg0)
						{
							RunningServiceInfo runningServiceInfo = (RunningServiceInfo)item.get("runningServiceInfo");
							
							// TODO Auto-generated method stub
							 int pid = android.os.Process.getUidForName("com.kuaihuoyun.freight.service.PushService");
							 android.os.Process.killProcess(runningServiceInfo.pid);
						}
					});
			}
			return convertView;
		}
	}
	
	
}