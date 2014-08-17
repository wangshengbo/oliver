package com.example.pocket.receiver;


import com.example.pocket.TaskUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
/**
 * ����Ļ������ʱ��Ĺ㲥������
 * @author Administrator
 *
 */
public class LockScreenReceiver extends BroadcastReceiver {

	private static final String TAG = "LockScreenReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i(TAG, "����");
		//����Ļ������ʱ���������onReceive����
		SharedPreferences sp=context.getSharedPreferences("config", Context.MODE_PRIVATE);
		
		boolean killprocess=sp.getBoolean("killprocess", false);
		
		if(killprocess){
			TaskUtil.killAllProcess(context);
			Log.i(TAG, "ɱ�����н���");
		}
		//TaskUtil.killAllProcess(context);

	}

}
