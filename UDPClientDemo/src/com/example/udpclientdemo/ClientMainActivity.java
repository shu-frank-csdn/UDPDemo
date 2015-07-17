package com.example.udpclientdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ClientMainActivity extends Activity {

	public static TextView show;  
	public static Button press;  
	public static boolean flag;  
	/*private static final int MAX_DATA_PACKET_LENGTH = 40;//�������ݰ�����
	private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];  
	private DatagramPacket dataPacket; 
	private DatagramSocket socket;  */
   
	// Called when the activity is first created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_main);
        press = (Button)findViewById(R.id.sendudp);  
        press.setOnClickListener(new Button.OnClickListener()  
        {  
            @Override  
            public void onClick(View v)  
            {  
            	Thread threadClientSocket = new Thread(new ThreadClient());//��ҪҪ�����߳��з������������߳��з��������ǲ��е�
    			threadClientSocket.start();   
            }  
        });  
    }
	public void connectServerWithUDPSocket() {  
	    try {  
	        //����DatagramSocket����ָ��һ���˿ںţ�ע�⣬����ͻ�����Ҫ���շ������ķ�������,   
	        //����Ҫʹ������˿ں���receive������һ��Ҫ��ס   
	    	//System.out.println("��Ӧ��ť");
	        DatagramSocket socket = new DatagramSocket(9400);  
	        //ʹ��InetAddress(Inet4Address).getByName��IP��ַת��Ϊ�����ַ     
	        InetAddress serverAddress = InetAddress.getByName("192.168.1.120");  
	        String str = "123";//����Ҫ���͵ı���    
	        byte data[] = str.getBytes();//���ַ���str�ַ���ת��Ϊ�ֽ�����     
	        System.out.println("��������"+ data);
	        //����һ��DatagramPacket�������ڷ������ݡ�     
	        //����һ��Ҫ���͵�����  �����������ݵĳ���  ������������˵������ַ  �����ģ��������˶˿ں�    
	        DatagramPacket packet = new DatagramPacket(data, data.length ,serverAddress ,9400);    
	        socket.send(packet);//�����ݷ��͵�����ˡ� 
	        socket.close();//Ҫ�����close()������ӵĻ�ֻ���յ�һ������
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	    } catch (UnknownHostException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }    
	}
	public class ThreadClient implements Runnable{
    	public void run(){
    		try{
    	   connectServerWithUDPSocket();//��һ��ȥ����
           System.out.println("�Ѿ����е��߳����һ����");
           Thread.sleep(50);	 
    	}
    	catch(Throwable t){
		}
    }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_main, menu);
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);  
    		builder.setTitle("��ʾ");  
    		builder.setMessage("�ף���ȷ��Ҫ�˳�������");  
    		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    	            System.exit(0);
    			}    		  
    		});  
    		builder.setNegativeButton("ȡ��", null);  
    		builder.show();
        }
		return true;
    }
}
