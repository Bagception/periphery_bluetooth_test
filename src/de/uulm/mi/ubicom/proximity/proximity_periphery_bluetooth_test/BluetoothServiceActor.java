package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.Log;

public class BluetoothServiceActor extends BroadcastActor<BluetoothServiceReactor>{

	private final ConcurrentHashMap<BluetoothDevice,Vector<String>> devices;
	private int servicesDiscovered = 0;
	public BluetoothServiceActor(BluetoothServiceReactor reactor) {
		super(reactor);
		devices = new ConcurrentHashMap<BluetoothDevice,Vector<String>>();
		
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
	     if(intent.equals(BluetoothDevice.ACTION_FOUND)) {
	    	 //device found
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 devices.put(device, new Vector<String>());
	    	 Log.d("bt","NAME: "+intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_NAME));
	    	 device.fetchUuidsWithSdp();
	    	 reactor.onDeviceFound(device);
	    	 
	     }else if(intent.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
	    	 //device discovery finished
	    	 BluetoothDevice[] deviceArray = new BluetoothDevice[devices.keySet().size()];
	    	 deviceArray = devices.keySet().toArray(deviceArray);
	    	 reactor.onDeviceDiscoveryFinished(deviceArray);
	    	 
	     }else if(intent.equals(BluetoothDevice.ACTION_UUID)){
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 Vector<String> uuids = devices.get(device);
	         Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
	         for (int i=0; i<uuidExtra.length; i++) {
	           uuids.add(uuidExtra[i].toString());
	         }
	         reactor.onServicesDiscovered(device, uuids);
	         if (servicesDiscovered==devices.size()){
	        	 reactor.onServiceDiscoveryFinished(devices);
	         }
	         servicesDiscovered++;
	     }else if(intent.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
	    	 reactor.onServiceDiscoveryStarted();
	     }
	       
	        
		
	}
	
	public void register(Activity a){
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    filter.addAction(BluetoothDevice.ACTION_UUID);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	    a.registerReceiver(this, filter);
	}
	


}
