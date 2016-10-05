package me.hupeng.android.SuperShare.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;
import me.hupeng.android.SuperShare.R;

import java.lang.reflect.Field;

/**
 * 再按一次功能关联变量
 * */


public class MainActivity extends Activity {
    /**
     * 控件绑定成员变量
     * */
    private Button btn_send = null;
    private Button btn_receive = null;

    /**
     * 再按一次功能关联变量
     * */
    private long exitTime = 0;
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


        //force to display overflow menu in action bar
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, Menu.FIRST, Menu.FIRST, "关于");
        menu.add(1, Menu.FIRST+1, Menu.FIRST+1, "退出");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 添加响应事件
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id){
            case Menu.FIRST:
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case Menu.FIRST +1:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 监听返回键，实现再按一次退出功能
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
