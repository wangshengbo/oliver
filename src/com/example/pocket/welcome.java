package com.example.pocket;

import android.app.Activity;  
import android.content.Intent;  
import android.os.Bundle;  
import android.os.Handler;  
import android.view.Window;  
import android.view.WindowManager;  
  
/** ������ӭ���� */  
public class welcome extends Activity {  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        //ȫ����ʾwelcome����  
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);          
        setContentView(R.layout.start);  
        //�ӳ�0.7���ִ��run�����е�ҳ����ת  
        new Handler().postDelayed(new Runnable() {            
            @Override  
            public void run() {  
                Intent intent = new Intent(welcome.this, MainActivity.class);  
                startActivity(intent);  
                welcome.this.finish();  
            }  
        }, 2000);  
    }  
}  