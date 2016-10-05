package me.hupeng.android.SuperShare.UI;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ImageView;
import me.hupeng.android.SuperShare.Mina.MinaUtil;

import me.hupeng.android.SuperShare.Mina.SimpleMinaListener;
import me.hupeng.android.SuperShare.R;
import org.apache.mina.core.session.IoSession;

/**
 * Created by HUPENG on 2016/9/28.
 */
public class ReceiveImageActivity extends Activity{
    private ImageView imageView = null;
    private MinaUtil minaUtil = null;

    private void init(){
        imageView = (ImageView)findViewById(R.id.imageView);
        minaUtil = MinaUtil.getInstance(new MySimpleMinaListener(),false,"192.168.43.1");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_image);
        init();

        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
    }

    class MySimpleMinaListener implements SimpleMinaListener{

        @Override
        public void onReceive(Object obj, IoSession ioSession) {


            Message message = new Message();
            message.obj = (Bitmap)obj;
            handler.sendMessage(message);
        }

        @Override
        public void onLine(IoSession session) {

        }

        @Override
        public void offLine(IoSession session) {

        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap)msg.obj;
            imageView.setImageDrawable(new BitmapDrawable(bitmap));

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 当ActionBar图标被点击时调用
                Intent intent = new Intent(ReceiveImageActivity.this, ReceiveActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
