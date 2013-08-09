package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.actors;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;
import de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test.reactors.BluetoothServiceReactor;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.Log;

//BIG QUESTION HERE:
//if fetchUuidsWithSdp is done, does it affect the getUuids() from existing devices? (if yes, this would make things a lot easier
public class BluetoothServiceActor extends BroadcastActor<BluetoothServiceReactor>{

	private final ConcurrentHashMap<String,BluetoothDevice> devices;
	public BluetoothServiceActor(BluetoothServiceReactor reactor) {
		super(reactor);
		devices = new ConcurrentHashMap<String,BluetoothDevice>();
		
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
	     if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
	    	 //device found
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 added(device);
	    	 
	     }else if(intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
	    	 //device discovery finished
	    	 BluetoothDevice[] ldevices = new BluetoothDevice[devices.size()];
	    	 ldevices = devices.values().toArray(ldevices);
	    	 for (BluetoothDevice d:ldevices){
	    		 d.fetchUuidsWithSdp();
	    	 }
	    	 reactor.onDeviceDiscoveryFinished(ldevices,devices);
	    	 
	    	 
	     }else if(intent.getAction().equals(BluetoothDevice.ACTION_UUID)){
	    	 BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	    	 devices.put(device.getAddress(), device);
	         reactor.onServicesDiscovered(device);
	         
	     }else if(intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
	    	 Log.d("bt","dd started recv");
	    	reactor.onServiceDiscoveryStarted();
	    	Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	    	if (pairedDevices.size() > 0) {
	    	    for (BluetoothDevice device : pairedDevices) {	    	    	
	   	    	 	added(device);
	    	    }
	    	}
	     }
	       
	        
		
	}
	
	private void added(BluetoothDevice device){
		if (devices.get(device.getAddress()) != null){
   		 //device already discovered
	   		 Log.d("bt","already: "+device.getName());
	   		 return;
	   	 }else{
	   		 Log.d("bt","new : "+device.getName());
	   	 }
	   	 devices.put(device.getAddress(),device);
	   	 reactor.onDeviceFound(device);
	}
	
	public void register(Activity a){
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    filter.addAction(BluetoothDevice.ACTION_UUID);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
	    filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	    a.registerReceiver(this, filter);
	}
	


}
