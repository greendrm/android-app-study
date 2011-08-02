package greendrm.Notification;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends Activity {
	private static final int ID_NOTI = 12345;
	private Timer timer = new Timer();
	private int count = 0;
	private NotificationManager mNotiMgr;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mNotiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
        Button btNoti = (Button)findViewById(R.id.buttonNotice);
        Button btCancel = (Button)findViewById(R.id.buttonCacel);
        
        btNoti.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TimerTask task = new TimerTask() {
					public void run() {
						notifyEvent();
					}
				};
				
				timer.schedule(task, 2000); // 2 sec
				
			}
		});
        
        btCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mNotiMgr.cancel(ID_NOTI);
			}
		});
    }
    
    private void notifyEvent() {
    	Notification note = new Notification(R.drawable.s_ball,
    			"축구공을 받으세요", System.currentTimeMillis());
    	PendingIntent i = PendingIntent.getActivity(this, 0,
    			new Intent(this, MyNotification.class), 0);
    	note.setLatestEventInfo(this, "제목: 축구공 이벤트",
    			"축구공 이벤트 알림입니다", i);
    	note.number = 0; //++count;
    	mNotiMgr.notify(ID_NOTI, note);
    }
}