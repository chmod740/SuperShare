package me.hupeng.android.SuperShare.UI;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import me.hupeng.android.SuperShare.R;
import me.hupeng.android.SuperShare.util.wifi.WifiAdmin;
import me.hupeng.android.SuperShare.util.wifi.WifiApAdmin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HUPENG on 2016/9/22.
 */
public class ReceiveActivity extends Activity {
    private WifiAdmin wifiAdmin = null;
    private ListView wifiListView = null;
    private List<Map<String,Object>> wifiList = null;
    private Button btnRefresh = null;
    private SimpleAdapter adapter = null;
    private void init(){
        try{
            WifiApAdmin.closeWifiAp(ReceiveActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

        wifiAdmin = new WifiAdmin(this) {
            @Override
            public Intent myRegisterReceiver(BroadcastReceiver receiver, IntentFilter filter) {
                ReceiveActivity.this.registerReceiver(receiver, filter);
                return null;
            }

            @Override
            public void myUnregisterReceiver(BroadcastReceiver receiver) {
                ReceiveActivity.this.unregisterReceiver(receiver);
            }

            @Override
            public void onNotifyWifiConnected() {
                Log.i("ss_log", "connect");
            }

            @Override
            public void onNotifyWifiConnectFailed() {
                Log.i("ss_log", "connect_fail");
            }
        };

        wifiListView = (ListView)findViewById(R.id.list_ap);
        wifiAdmin.startScan();
        wifiList = new ArrayList<>();

        List<ScanResult>list = wifiAdmin.getWifiList();
        for (int i = 0 ; i < list.size() ; i ++){

            ScanResult scanResult = list.get(i);
            if (scanResult.SSID.contains("SuperShare_")){
                Map<String,Object>map = new HashMap<>();
                map.put("tv_ap_name",scanResult.SSID.replace("SuperShare_",""));
                map.put("tv_ap_info",scanResult.BSSID);
                wifiList.add(map);
            }
        }
        adapter = new SimpleAdapter(this,wifiList,R.layout.list_wifi,new String[]{"tv_ap_name", "tv_ap_info"},new int[]{R.id.tv_ap_name, R.id.tv_ap_info});
        wifiListView.setAdapter(adapter);
        btnRefresh = (Button)findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new MyOnClickListener());
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        init();

    }

    private void refreshWifiList(){
        wifiAdmin.startScan();
        List<ScanResult>list = wifiAdmin.getWifiList();
        wifiList.clear();
        for (int i = 0 ; i < list.size() ; i ++){

            ScanResult scanResult = list.get(i);
            if (scanResult.SSID.contains("SuperShare_")){
                Map<String,Object>map = new HashMap<>();
                map.put("tv_ap_name",scanResult.SSID.replace("SuperShare_",""));
                map.put("tv_ap_info",scanResult.BSSID);
                wifiList.add(map);
                System.out.println(scanResult.SSID);
            }
        }
        adapter.notifyDataSetChanged();
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.btn_refresh:
                    refreshWifiList();
                    break;
            }
        }
    }
}
