package com.example.pocket.domain;

import android.graphics.drawable.Drawable;

/**
 * ����ʵ��
 * @author Administrator
 *
 */
public class TaskInfo {

	private String appName;
	private Drawable appIcon;
	private int pId; //process id ���̵�ID
	private int memorySize; //����kb��Ϊ��λ
	private boolean isChecked;
	private boolean isSystemApp;
	private String packName;
	
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public boolean isSystemApp() {
		return isSystemApp;
	}
	public void setSystemApp(boolean isSystemApp) {
		this.isSystemApp = isSystemApp;
	}

	
	
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	
}
