package com.example.pocket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
/**
 * �绰���������ݿⴴ��
 * @author Administrator
 *
 */
public class AppLockrDBHelper extends SQLiteOpenHelper{

	public AppLockrDBHelper(Context context) {
		super(context, "applock.db", null, 1);
		
	}
	/**
	 * ��һ�δ������ݿ��ʱ��ִ��
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table applock (_id integer primary key autoincrement,packname varchar(30))");
	}

	/**
	 * �������ݿ�Ĳ���
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
