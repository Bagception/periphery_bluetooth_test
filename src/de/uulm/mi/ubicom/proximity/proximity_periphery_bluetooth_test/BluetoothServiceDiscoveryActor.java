package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothServiceDiscoveryActor extends BroadcastActor<BluetoothServiceReactor>{
	
	public BluetoothServiceDiscoveryActor(BluetoothServiceReactor reactor) {
		super(reactor);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){

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
