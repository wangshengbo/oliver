package com.example.pocket.service;

import java.util.ArrayList;
import java.util.List;

import com.example.pocket.EncryptActivity;
import com.example.pocket.LockScreenActivity;

import com.example.pocket.db.dao.AppLockDao;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * ���Ź�ʵ�� (����û��򿪵ĳ���)
 * 
 * @author Administrator
 * 
 */
public class WatchDogService extends Service {
	private AppLockDao dao;
	private List<String> lockapps;
	private ActivityManager am;
	private Intent lockappintent;
	private boolean flag;
	private MyBinder binder;
	private List<String> tempstopapps;
	private KeyguardManager keyguardManager;
	
	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}

	public class MyBinder extends Binder implements IService {

		public void callAppProtectStart(String packname) {
			// TODO Auto-generated method stub
			AppProtectStart(packname);
		}

		public void callAppProtectStop(String packname) {
			// TODO Auto-generated method stub
			AppProtectStop(packname);
		}

	}

	/**
	 * ���¿�����Ӧ�õı���
	 * 
	 * @param packname
	 */
	public void AppProtectStart(String packname) {
		if (tempstopapps.contains(packname)) {
			tempstopapps.remove(packname);
		}
	}

	/**
	 * ��ʱֹͣ��app�ı���
	 * 
	 * @param packname
	 */
	public void AppProtectStop(String packname) {
		tempstopapps.add(packname);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("�������� ");
		getContentResolver().registerContentObserver(
				Uri.parse("content://cn.test.applockprovider"), true,
				new MyObserver(new Handler()));
	
		// keyguardManager.inKeyguardRestrictedInputMode();

		dao = new AppLockDao(this);
		binder = new MyBinder();
		tempstopapps = new ArrayList<String>();
		flag = true;

		lockappintent = new Intent(this, LockScreenActivity.class);
		// �����ǲ���������ջ�ģ�Ҫ�ڷ����￪��Activity�Ļ��������һ��flag
		lockappintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
	
		
		lockapps = dao.getPackName();
		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		new Thread() {

			@Override
			public void run() {
				// �������Ź�
				while (flag) {
					try {
						keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
						
						// �ж���Ļ�Ƿ�Ϊ����״̬ ���������״̬
						if (keyguardManager.inKeyguardRestrictedInputMode()) {
							// �����ʱ�ļ���
							tempstopapps.clear();
							
						}

						// �õ���ǰ���г���İ���
						// ����ϵͳ��������ջ����Ϣ������taskinfo�ļ�����ֻ��һ��Ԫ��
						// �����ǵ�ǰ�������н��̶�Ӧ������ջ
						List<RunningTaskInfo> taskinfos = am.getRunningTasks(1);
						RunningTaskInfo currenttask = taskinfos.get(0);
						// ��ȡ��ǰ�û��ɼ���Activity �İ���
						String packname = currenttask.topActivity
								.getPackageName();
						
						if (lockapps.contains(packname)) { // ������ݿ��ڴ������������֤����Ҫ����
							System.out.println("��⵽�д˰��� ");
							// �����ǰ��Ӧ�ó�����Ҫ��ʱ�ı���ֹ����
							if (tempstopapps.contains(packname)) {
								sleep(1000);
								continue;
							}

							// ������һ�������Ľ��棬���û���������
							lockappintent.putExtra("packname", packname);
							startActivity(lockappintent);
						
						} else {
							// ����
						}
						sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

		}.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
	}

	private class MyObserver extends ContentObserver {

		public MyObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}

		// �����ݷ����ı��ʱ�����
		@Override
		public void onChange(boolean selfChange) {

			super.onChange(selfChange);
			// ���¸���lockapps���������
			Log.i("change", "-------------���ݿ����ݱ仯��");
			lockapps = dao.getPackName();
		}

	}
}
