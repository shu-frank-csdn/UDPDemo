package com.example.tcpserverdemo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
public class ServerMainActivity extends Activity {

	private final int MSG_REFRESH = 0;
    private Handler myHandler;
    private TextView txtView;
    private MediaPlayer mediaPlayer;
    String result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_main);
		txtView = (TextView)findViewById(R.id.txtResult);//�������
		Thread threadSocketServer = new Thread(new ServerThread());
        threadSocketServer.start();
        myHandler = new MyHandler();
	}
     public class ServerThread implements Runnable {
	    	public void run() {
	    		try{   
	    		ServerReceviedByUdp();
	    		Thread.sleep(50);
	    		}
	    		catch(Throwable t){
	    		}
	    	}
	 }
	public void ServerReceviedByUdp(){  
	    //����һ��DatagramSocket���󣬲�ָ�������˿ڡ���UDPʹ��DatagramSocket��     
	    DatagramSocket socket;
	    while(true){
	    try {  
	        socket = new DatagramSocket(9400);  
	        //����һ��byte���͵����飬���ڴ�Ž��յ�������     
	        byte data[] = new byte[4*1024];    
	        //����һ��DatagramPacket���󣬲�ָ��DatagramPacket����Ĵ�С     
	        DatagramPacket packet = new DatagramPacket(data,data.length);    
	        //��ȡ���յ�������     
	        socket.receive(packet);    
	        //�ѿͻ��˷��͵�����ת��Ϊ�ַ�����     
	        //ʹ������������String����������һ�����ݰ� ����������ʼλ�� �����������ݰ���     
	        //���Ե�ʱ����ֵ����⣺��Ϊ֮ǰд����String result = new String(packet.getData(),packet,getOffset())����
	        result = new String(packet.getData(),packet.getOffset() ,packet.getLength());
	    	result = result.trim();//ȥ���ո�
	        System.out.println("���������յ���������"+ result);
	        if(result != null) {
				myHandler.sendEmptyMessage(MSG_REFRESH);   //���浽UI���棬����ͼ�� ��sendEmptyMessage������Handler������Ϣ
														   //���͵���Ϣ��MSG_REFRESH
				 if(result.equals("nikonphoto")){ //������ط��ӡ���
			        	//Toast.makeText(ServerMainActivity.this,"��¼�ɹ�", Toast.LENGTH_SHORT).show();֮ǰ������������⡣
						mediaPlayer=MediaPlayer.create(ServerMainActivity.this, R.raw.nikon_shot);
						System.out.println("�Ѿ�����������");
						mediaPlayer.start(); //�������߻ָ�����
						mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//
					        public void onCompletion(MediaPlayer arg0) {  
					        mediaPlayer.release(); 
					      }
						});  
		        	}
	        }                        
	        /*String line;
    		line=result.readLine();*/
    		//һ����⵽�����ݾͻ�������º��������ݷ��͵�Handler��handleMessage()���������д���
	      
	    } catch (SocketException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	    }
	}
	class MyHandler extends Handler{
    	public void handleMessage(Message msg) { //handler��handleMessage����ȥ������Ϣ��Ȼ�������Ϣ�Ĳ�ִͬ�в�ͬ�Ĳ���
			// TODO Auto-generated method stub   
			switch (msg.what) {                 //�ж�whatֵ
			case MSG_REFRESH:                   //what����MSG_REFRESHʱ
				System.out.println("Handler���������յ���������"+ result);
				//֮ǰ ����ط����������NULL�����ϱߵ��Ǹ����������ȷ�ġ�
				//txtView.setText(result); setText()  ����ǰ�����ݳ���ˣ���append()����ǰ�����ݺ������
				txtView.append(result);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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