package de.uulm.mi.ubicom.proximity.proximity_periphery_bluetooth_test;

import de.uulm.mi.ubicom.proximity.lib.Reactor;

public interface BluetoothStateChangeReactor extends Reactor{
	public void onBluetoothEnabledChanged(boolean isEnabled);
	
}
