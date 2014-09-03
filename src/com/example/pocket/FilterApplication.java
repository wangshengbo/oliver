package com.example.pocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.BounceInterpolator;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.example.pocket.BrowseApplicationInfoAdapter.ViewHolder;

public class FilterApplication extends Activity {

	public static final int FILTER_ALL_APP = 0; // ����Ӧ�ó���
	public static final int FILTER_SYSTEM_APP = 1; // ϵͳ����
	public static final int FILTER_THIRD_APP = 2; // ������Ӧ�ó���
	public static final int FILTER_SDCARD_APP = 3; // ��װ��SDCard��Ӧ�ó���

	private ListView listview = null;
	ViewHolder filterholder = null;
	private UninstallReceiver mUninstallReceiver;
	private PackageManager pm;
	private int filter = FILTER_ALL_APP;
	private int delposition = 0;
	private int i = 0;
	private List<AppInfo> mlistAppInfo;
	private BrowseApplicationInfoAdapter browseAppAdapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);
		SwipeMenuListView listview = (SwipeMenuListView) findViewById(R.id.listviewApp);
		if (getIntent() != null) {
			filter = getIntent().getIntExtra("filter", 0);
		}
		mlistAppInfo = queryFilterAppInfo(filter); // ��ѯ����Ӧ�ó�����Ϣ
		// ����������������ע�ᵽlistView
		browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
		// �����󻬲˵���ѡ��
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};

		// set creator
		listview.setMenuCreator(creator);
		// ���ûص�
		listview.setCloseInterpolator(new BounceInterpolator());

		listview.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				AppInfo item = mlistAppInfo.get(position);
				delposition = position;
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
					delete(item);
					break;
				}
			}
		});
		// ����ж�ع㲥
		mUninstallReceiver = new UninstallReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		this.registerReceiver(mUninstallReceiver, filter);
	}

	private void delete(AppInfo item) {
		// delete app
		try {
			Intent intent = new Intent(Intent.ACTION_DELETE);
			intent.setData(Uri.fromParts("package", item.getPkgName(), null));
			startActivity(intent);
		} catch (Exception e) {
		}
	}

	private void open(AppInfo item) {
		// open app
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(item.getPkgName());
		List<ResolveInfo> resolveInfoList = getPackageManager()
				.queryIntentActivities(resolveIntent, 0);
		if (resolveInfoList != null && resolveInfoList.size() > 0) {
			ResolveInfo resolveInfo = resolveInfoList.get(0);
			String activityPackageName = resolveInfo.activityInfo.packageName;
			String className = resolveInfo.activityInfo.name;

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName componentName = new ComponentName(
					activityPackageName, className);

			intent.setComponent(componentName);
			startActivity(intent);
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
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

	private class UninstallReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				mlistAppInfo.remove(delposition);
				browseAppAdapter.notifyDataSetChanged();
			}
		}
	}
}
