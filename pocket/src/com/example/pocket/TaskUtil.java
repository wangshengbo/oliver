package com.example.pocket;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * ɱ�����н���
 * @author Administrator
 *
 */
public class TaskUtil {
	/**
	 * ɱ�����н���
	 * @author Administrator
	 *
	 */
	public static void killAllProcess(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		 List<RunningAppProcessInfo> runningApps=am.getRunningAppProcesses();
		 for (RunningAppProcessInfo info : runningApps) {
			String packname=info.processName;
			am.killBackgroundProcesses(packname);
		}
	}
	/**
	 * ɱ�����н���
	 * @author Administrator
	 *
	 */
	public static int getProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		 List<RunningAppProcessInfo> runningApps=am.getRunningAppProcesses();
		 return runningApps.size();
	}
	
	/**
	 * ��ȡ��ǰϵͳ��ʣ��Ŀ����ڴ���Ϣ byte long
	 */
	public static String getMemeorySize(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(outInfo);
		return TextFormater.getDataSize(outInfo.availMem);

	}
}
