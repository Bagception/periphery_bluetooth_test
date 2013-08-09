package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.bluetooth.*;
public class MainActivity extends Activity implements BluetoothStateChangeReactor, BluetoothServiceReactor {
	private BluetoothAdapter bluetooth;
	private BluetoothStateActor btStateActor;
	private BluetoothServiceActor btServiceActor;
	private ArrayAdapter<String> bt_devicesAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStateActor = new BluetoothStateActor(this);
        btServiceActor = new BluetoothServiceActor(this);
        bt_devicesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
         
        ListView btDeviceListView =(ListView) findViewById(R.id.bt_devices);
        btDeviceListView.setAdapter(bt_devicesAdapter);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
      
        btStateActor.register(this);
        btServiceActor.register(this);
        
    	bluetooth = BluetoothAdapter.getDefaultAdapter();
    	onBluetoothEnabledChanged(bluetooth.isEnabled());
    }
    
 
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	btStateActor.unregister(this);
    	btServiceActor.unregister(this);
    }
    
    
    
    public void onScan(View v){
    	Log.d("bt","scanning");
    	bluetooth.startDiscovery();
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    //BluetoothStateReactor
    
	@Override
	public void onBluetoothEnabledChanged(boolean isEabled) {
		Switch bt_enabled_switch = (Switch) findViewById(R.id.bt_enabled_switch);
    	bt_enabled_switch.setChecked(isEabled);
    	
    	Button scanButton = (Button) findViewById(R.id.bt_scan_services);
		scanButton.setEnabled(isEabled);
	}

	
	//BluetoothServiceReactor
	

	@Override
	public void onServiceDiscoveryStarted() {
		Button scanButton = (Button) findViewById(R.id.bt_scan_services);
		scanButton.setEnabled(false);
		
	}

	@Override
	public void onDeviceFound(BluetoothDevice device) {
		
		bt_devicesAdapter.add(device.getName());
	}

	@Override
	public void onDeviceDiscoveryFinished(BluetoothDevice[] devices) {
		
		
	}

	@Override
	public void onServicesDiscovered(BluetoothDevice device, Vector<String> uuid) {
		synchronized (this) {
			Log.d("sd","onServicesDS "+device.getName()+":");
			for (String s:uuid){
				Log.d("sd"," "+s);
			}
		}
	}

	@Override
	public void onServiceDiscoveryFinished(
			ConcurrentHashMap<BluetoothDevice, Vector<String>> devicesWithServices) {
		Button scanButton = (Button) findViewById(R.id.bt_scan_services);
		scanButton.setEnabled(true);
		
	}
    

    

    
    
}
