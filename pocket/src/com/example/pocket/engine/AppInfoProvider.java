package com.example.pocket.engine;

import java.util.ArrayList;
import java.util.List;

import com.example.pocket.domain.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;



/**
 * Ӧ�ó������ ҵ����
 * @author Administrator
 *
 */
public class AppInfoProvider {
	private static final String TAG = "AppInfoProvider";
	private Context context;
	private PackageManager packManager;
	public AppInfoProvider(Context context) {
		this.context = context;
		packManager=context.getPackageManager();
	}

	/**
	 * ���ص�ǰ�ֻ����氲װ������Ӧ�ó�����Ϣ�ļ���
	 * @return
	 */
	public List<AppInfo> getAllApps(){
		List<AppInfo> appinfos =new ArrayList<AppInfo>();
		List<PackageInfo> packinfos=packManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES); //������е�Ӧ�ó����
		for(PackageInfo info:packinfos){
			AppInfo myApp = new AppInfo();
			String packname=info.packageName; //����
			myApp.setPackname(packname);
			ApplicationInfo appinfo=info.applicationInfo;
			Drawable icon=appinfo.loadIcon(packManager); //Ӧ�ó����ͼ��
			myApp.setIcon(icon);
			String appname=appinfo.loadLabel(packManager).toString(); //Ӧ�ó��������
			myApp.setAppname(appname);
			if(filterApp(appinfo)){
				Log.i(TAG,"�û�Ӧ��");
				myApp.setSystemApp(false);
			}else{
				Log.i(TAG,"ϵͳӦ��");
				myApp.setSystemApp(true);
			}
			appinfos.add(myApp);
		}
		return appinfos;
	}
	/**
	 * �ж�ĳ��Ӧ�ó����ǲ��ǵ�������Ӧ�ó���
	 * @param info  �ǵ������Ļ�����true
	 * @return
	 */
	public boolean filterApp(ApplicationInfo info){
		if((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)!=0){
			return true;
		}else if((info.flags & ApplicationInfo.FLAG_SYSTEM)==0){
			return true;
		}
		return false;
	}
}
