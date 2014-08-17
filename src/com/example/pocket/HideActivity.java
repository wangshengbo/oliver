package com.example.pocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class HideActivity extends Activity{
	public static final int FILTER_ALL_APP = 0; // 所有应用程序
	private ListView listview_hide = null;
	private PackageManager pm;
	private int filter = FILTER_ALL_APP; 
	private List<AppInfo> mlistAppInfo ;
	private HideApplicationInfoAdapter hideAppAdapter = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hide);
		
		listview_hide = (ListView) findViewById(R.id.listView_hide);
		if(getIntent()!=null){
			filter = getIntent().getIntExtra("filter", 0) ;
		}
		mlistAppInfo = queryFilterAppInfo(filter); // 查询所有应用程序信息
		// 构建适配器，并且注册到listView
		hideAppAdapter = new HideApplicationInfoAdapter(this, mlistAppInfo);
		listview_hide.setOnItemClickListener(itemListener);
		listview_hide.setAdapter(hideAppAdapter);
	}

	private List<AppInfo> queryFilterAppInfo(int filter) {
		pm = this.getPackageManager();
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				appInfos.add(getAppInfo(app));
			}
			return appInfos;
	}
	// 构造一个AppInfo对象 ，并赋值
	private AppInfo getAppInfo(ApplicationInfo app) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		return appInfo;
	}
	
	OnItemClickListener itemListener = new OnItemClickListener() {
		  @Override
		  public void onItemClick(AdapterView<?> l, View v, int position, long id) {  
			  //l.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		     LinearLayout view1 = (LinearLayout) l.getChildAt(position);
		     RelativeLayout view = (RelativeLayout) view1.getChildAt(1);
		     CheckBox box = (CheckBox) view.getChildAt(4);
		     box.setChecked(box.isChecked());
		  }
	};
		 
	/*
	private AdapterView.OnItemSelectedListener  onItemSelectedListener =   
		    new AdapterView.OnItemSelectedListener(){  
		    @Override  
		    public void onItemClick(AdapterView<?> l, View v, int position, long id) {  
		    	 
		        LinearLayout view1 = (LinearLayout) l.getChildAt(position);
		        RelativeLayout view = (RelativeLayout) view1.getChildAt(0);
		        CheckBox box = (CheckBox) view.getChildAt(2);
		        box.setChecked(!box.isChecked());

		         }
		      
		    @Override  
		    public void onNothingSelected(AdapterView<?> parent) {  
		        parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);  
		    }  
		};  
		*/
}
