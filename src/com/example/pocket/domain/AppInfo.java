package com.example.pocket.domain;

import android.graphics.drawable.Drawable;

/**
 * Ӧ�ó���ʵ��
 * @author Administrator
 *
 */
public class AppInfo {
	private Drawable icon;
	private String appname;
	private String packname;
	private boolean isSystemApp; //������û��Լ���װ�ĳ��� ���ؾ�Ϊflase ϵͳ��Ϊtrue
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public boolean isSystemApp() {
		return isSystemApp;
	}
	public void setSystemApp(boolean isSystemApp) {
		this.isSystemApp = isSystemApp;
	}
}
