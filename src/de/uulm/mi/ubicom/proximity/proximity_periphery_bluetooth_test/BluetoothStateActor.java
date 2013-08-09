package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import de.uulm.mi.ubicom.proximity.lib.BroadcastActor;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothStateActor extends BroadcastActor<BluetoothStateChangeReactor>{

	public BluetoothStateActor(BluetoothStateChangeReactor reactor) {
		super(reactor);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
			final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR);
				
	           switch (state) {
	            case BluetoothAdapter.STATE_OFF:
	            	reactor.onBluetoothEnabledChanged(false);
	                break;
	            case BluetoothAdapter.STATE_TURNING_OFF:
	                break;
	            case BluetoothAdapter.STATE_ON:
	            	reactor.onBluetoothEnabledChanged(true);
	                break;
	            case BluetoothAdapter.STATE_TURNING_ON:
	                break;
	            }
			
		}
		
		
	}
	
	public void register(Activity a){
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        a.registerReceiver(this, filter);
	}


}
