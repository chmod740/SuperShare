package me.hupeng.android.SuperShare.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import me.hupeng.android.SuperShare.R;


public class MainActivity extends Activity {
    /**
     * 控件绑定成员变量
     * */
    private Button btn_send = null;
    private Button btn_receive = null;

    private void init(){
        //绑定控件
        btn_receive = (Button)findViewById(R.id.btn_receive);
        btn_send = (Button)findViewById(R.id.btn_send);

        //初始化监听器
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        //绑定监听器
        btn_receive.setOnClickListener(myOnClickListener);
        btn_send.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            //获得触发监听器的控件的ID
            int id = view.getId();
            switch (id){
                case R.id.btn_receive:
                    receive();
                    break;
                case R.id.btn_send:
                    send();
                default:
                    break;
            }

        }
    }

    private void receive(){
        Intent intent = new Intent(MainActivity.this, ReceiveActivity.class);
        startActivity(intent);
    }

    private void send(){
        Intent intent = new Intent(MainActivity.this, SendActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
