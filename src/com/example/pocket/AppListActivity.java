package com.example.pocket;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AppListActivity  extends TabActivity {
	
	private TabHost tabHost; 
	private int filter = FilterApplication.FILTER_ALL_APP; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_applist);
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		
		Intent intent;
		intent=new Intent().setClass(this, FilterApplication.class);  
		filter = FilterApplication.FILTER_ALL_APP ;
		intent.putExtra("filter", filter);
        spec=tabHost.newTabSpec("AllApps").setIndicator("AllApps").setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this,FilterApplication.class);  
        filter = FilterApplication.FILTER_SYSTEM_APP ;
        intent.putExtra("filter", filter);
        spec=tabHost.newTabSpec("SystemApps").setIndicator("SystemApps").setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));  
        tabHost.addTab(spec);  
          
        intent=new Intent().setClass(this, FilterApplication.class);  
        filter = FilterApplication.FILTER_THIRD_APP ;
        intent.putExtra("filter", filter);
        spec=tabHost.newTabSpec("ThirdApps").setIndicator("ThirdApps").setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));  
        tabHost.addTab(spec);  
        
        intent=new Intent().setClass(this, FilterApplication.class);  
        filter = FilterApplication.FILTER_SDCARD_APP ;
        intent.putExtra("filter", filter);
        spec=tabHost.newTabSpec("SDCardApps").setIndicator("SDCardApps").setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));  
        tabHost.addTab(spec); 
        tabHost.setCurrentTab(0);  
        
      
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.list_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 switch (checkedId) {  
	                case R.id.btallapp:
	                    tabHost.setCurrentTabByTag("AllApps");  
	                    break;  
	                case R.id.btsystemapp:
	                    tabHost.setCurrentTabByTag("SystemApps");  
	                    break;  
	                case R.id.btthirdapp:
	                    tabHost.setCurrentTabByTag("ThirdApps");  
	                    break;  
	                case R.id.btsdcardapp:
	                    tabHost.setCurrentTabByTag("SDCardApps");  
	                    break;  
	                default:  
	                    break;  
	                }  
				
			}
		});
	}
	
	
	
	
	
	
	
	
	
	

//
//	public static final int FILTER_ALL_APP = 0; // ����Ӧ�ó���
//	public static final int FILTER_SYSTEM_APP = 1; // ϵͳ����
//	public static final int FILTER_THIRD_APP = 2; // ������Ӧ�ó���
//	public static final int FILTER_SDCARD_APP = 3; // ��װ��SDCard��Ӧ�ó���
//
//	private ListView listview = null;
//
//	private PackageManager pm;
//	private int filter = FILTER_ALL_APP; 
//	private List<AppInfo> mlistAppInfo ;
//	private BrowseApplicationInfoAdapter browseAppAdapter = null ;
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_applist);
//		listview = (ListView) findViewById(R.id.listViewApp);
//		if(getIntent()!=null){
//			filter = getIntent().getIntExtra("filter", 0) ;
//		}
//		mlistAppInfo = queryFilterAppInfo(filter); // ��ѯ����Ӧ�ó�����Ϣ
//		// ����������������ע�ᵽlistView
//		browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
//		listview.setAdapter(browseAppAdapter);
//	}
//	// ���ݲ�ѯ��������ѯ�ض���ApplicationInfo
//	private List<AppInfo> queryFilterAppInfo(int filter) {
//		pm = this.getPackageManager();
//		// ��ѯ�����Ѿ���װ��Ӧ�ó���
//		List<ApplicationInfo> listAppcations = pm
//				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//		Collections.sort(listAppcations,
//				new ApplicationInfo.DisplayNameComparator(pm));// ����
//		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // ������˲鵽��AppInfo
//		// ��������������
//		switch (filter) {
//		case FILTER_ALL_APP: // ����Ӧ�ó���
//			appInfos.clear();
//			for (ApplicationInfo app : listAppcations) {
//				appInfos.add(getAppInfo(app));
//			}
//			return appInfos;
//		case FILTER_SYSTEM_APP: // ϵͳ����
//			appInfos.clear();
//			for (ApplicationInfo app : listAppcations) {
//				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
//					appInfos.add(getAppInfo(app));
//				}
//			}
//			return appInfos;
//		case FILTER_THIRD_APP: // ������Ӧ�ó���
//			appInfos.clear();
//			for (ApplicationInfo app : listAppcations) {
//				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
//					appInfos.add(getAppInfo(app));
//				}
//			}
//			break;
//		case FILTER_SDCARD_APP: // ��װ��SDCard��Ӧ�ó���
//			appInfos.clear();
//			for (ApplicationInfo app : listAppcations) {
//				if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
//					appInfos.add(getAppInfo(app));
//				}
//			}
//			return appInfos;
//		default:
//			return null;
//		}
//		return appInfos;
//	}
//	// ����һ��AppInfo���� ������ֵ
//	private AppInfo getAppInfo(ApplicationInfo app) {
//		AppInfo appInfo = new AppInfo();
//		appInfo.setAppLabel((String) app.loadLabel(pm));
//		appInfo.setAppIcon(app.loadIcon(pm));
//		appInfo.setPkgName(app.packageName);
//		return appInfo;
//	}
}
