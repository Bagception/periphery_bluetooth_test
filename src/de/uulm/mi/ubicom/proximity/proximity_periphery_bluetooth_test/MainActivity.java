package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.bluetooth.*;
public class MainActivity extends Activity implements BluetoothStateChangeReactor, BluetoothServiceReactor {
	private BluetoothAdapter bluetooth;
	private BluetoothStateActor btStateActor;
	private BluetoothServiceActor btServiceActor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btStateActor = new BluetoothStateActor(this);
        btStateActor.register(this);
        
        btServiceActor = new BluetoothServiceActor(this);
        btServiceActor.register(this);
        
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
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
		Log.d("sd","SD started");
		
	}

	@Override
	public void onDeviceFound(BluetoothDevice device) {
		Log.d("sd","D found "+device);
		
	}

	@Override
	public void onDeviceDiscoveryFinished(BluetoothDevice[] devices) {
		synchronized (this) {
			Log.d("sd","DD finished: ");
			for (BluetoothDevice d:devices){
				Log.d("sd"," "+d);
			}
		}
		
	}

	@Override
	public void onServicesDiscovered(BluetoothDevice device, Vector<String> uuid) {
		synchronized (this) {
			Log.d("sd","Services for "+device+":");
			for (String s:uuid){
				Log.d("sd"," "+s);
			}
		}
	}

	@Override
	public void onServiceDiscoveryFinished(
			ConcurrentHashMap<BluetoothDevice, Vector<String>> devicesWithServices) {
		synchronized (this) {
			Log.d("sd","SD finished");
			for (Entry<BluetoothDevice, Vector<String>> entry : devicesWithServices.entrySet()) {
			    BluetoothDevice device = entry.getKey();
			    Vector<String> uuid = entry.getValue();
			    Log.d("sd"," Services for "+device+":");
				for (String s:uuid){
					Log.d("sd","  "+s);
				}
			    
			    
			}
		}
		
	}
    

    

    
    
}
