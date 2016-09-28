package me.hupeng.android.SuperShare.UI;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import me.hupeng.android.SuperShare.Mina.MinaUtil;
import me.hupeng.android.SuperShare.Mina.SimpleMinaListener;
import me.hupeng.android.SuperShare.R;
import me.hupeng.android.SuperShare.util.wifi.WifiApAdmin;
import org.apache.mina.core.session.IoSession;

import java.io.FileNotFoundException;

/**
 * 发送Activity的逻辑
 * */
public class SendActivity extends Activity {
    private WifiApAdmin wifiApAdmin = null;
    private Button btnChoice = null;
    private ImageView imageView = null;
    private Bitmap bitmap = null;
    private MinaUtil minaUtil = null;
    private TextView tvClientStatus = null;

    private int clientNum = 0;


    private void init(){
        //设置Wifi热点
        wifiApAdmin = new WifiApAdmin(this);
        wifiApAdmin.startWifiAp("SuperShare_abc123","88888888");

        //控件变量绑定
        btnChoice = (Button)findViewById(R.id.btn_choice);
        imageView = (ImageView)findViewById(R.id.imageView);
        tvClientStatus = (TextView)findViewById(R.id.tv_client_status);

        //控件动作绑定
        btnChoice.setOnClickListener(new MyOnClickListener());

        //设置Socket连接,获取一个MinaUtil服务器实例
        minaUtil = MinaUtil.getInstance(new MySimpleMinaListener(),true,null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        init();
    }

    /**
     * 实现单击事件
     * */
    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.btn_choice:
                    choiceImage();
                    break;
            }
        }
    }

    private void choiceImage(){
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }

    /**
     * 返回图片对象
     * */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
                this.bitmap = bitmap;
                if (clientNum > 0){
                    minaUtil.send(bitmap);
                }
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 简易Mina监听器
     * */
    class MySimpleMinaListener implements SimpleMinaListener{
        //主机接收到消息
        @Override
        public void onReceive(Object obj, IoSession ioSession) {

        }
        //主机上线
        @Override
        public void onLine(IoSession session) {
            tvClientStatus.setText("接收端已连接");
            clientNum ++;
            if (bitmap != null){
                minaUtil.send(bitmap);
            }
        }
        //主机下线
        @Override
        public void offLine(IoSession session) {
            clientNum --;
            if (clientNum ==0){
                tvClientStatus.setText("正在等待接收端连接");
            }
        }
    }
}
