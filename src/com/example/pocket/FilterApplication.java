package com.example.pocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.pocket.BrowseApplicationInfoAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class FilterApplication  extends Activity{

	public static final int FILTER_ALL_APP = 0; // ����Ӧ�ó���
	public static final int FILTER_SYSTEM_APP = 1; // ϵͳ����
	public static final int FILTER_THIRD_APP = 2; // ������Ӧ�ó���
	public static final int FILTER_SDCARD_APP = 3; // ��װ��SDCard��Ӧ�ó���

	private ListView listview = null;
	ViewHolder filterholder = null;

	private PackageManager pm;
	private int filter = FILTER_ALL_APP; 
	private List<AppInfo> mlistAppInfo ;
	private BrowseApplicationInfoAdapter browseAppAdapter = null ;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);
		listview = (ListView) findViewById(R.id.listviewApp);
		if(getIntent()!=null){
			filter = getIntent().getIntExtra("filter", 0) ;
		}
		mlistAppInfo = queryFilterAppInfo(filter); // ��ѯ����Ӧ�ó�����Ϣ
		// ����������������ע�ᵽlistView
		browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
	}
	// ���ݲ�ѯ��������ѯ�ض���ApplicationInfo
	private List<AppInfo> queryFilterAppInfo(int filter) {
		pm = this.getPackageManager();
		// ��ѯ�����Ѿ���װ��Ӧ�ó���
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// ����
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // ������˲鵽��AppInfo
		// ��������������
		switch (filter) {
		case FILTER_ALL_APP: // ����Ӧ�ó���
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				appInfos.add(getAppInfo(app));
			}
			return appInfos;
		case FILTER_SYSTEM_APP: // ϵͳ����
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			return appInfos;
		case FILTER_THIRD_APP: // ������Ӧ�ó���
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			break;
		case FILTER_SDCARD_APP: // ��װ��SDCard��Ӧ�ó���
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			return appInfos;
		default:
			return null;
		}
		return appInfos;
	}
	// ����һ��AppInfo���� ������ֵ
	private AppInfo getAppInfo(ApplicationInfo app) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		return appInfo;
	}

}
