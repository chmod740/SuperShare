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
import me.hupeng.android.SuperShare.R;
import me.hupeng.android.SuperShare.util.wifi.WifiApAdmin;

import java.io.FileNotFoundException;

/**
 * Created by HUPENG on 2016/9/22.
 */
public class SendActivity extends Activity {
    private WifiApAdmin wifiApAdmin = null;
    private Button btnChoice = null;
    private ImageView imageView = null;
    private Bitmap bitmap = null;

    private void init(){
        //设置Wifi热点
        wifiApAdmin = new WifiApAdmin(this);
        wifiApAdmin.startWifiAp("SuperShare_abc123","88888888");

        //控件变量绑定
        btnChoice = (Button)findViewById(R.id.btn_choice);
        imageView = (ImageView)findViewById(R.id.imageView);

        //控件动作绑定
        imageView.setOnClickListener(new MyOnClickListener());
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
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
