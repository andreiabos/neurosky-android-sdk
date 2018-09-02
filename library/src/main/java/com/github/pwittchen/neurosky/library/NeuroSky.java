package com.github.pwittchen.neurosky.library;

import android.bluetooth.BluetoothAdapter;
import com.github.pwittchen.neurosky.library.listeners.DeviceMessageListener;
import com.neurosky.thinkgear.TGDevice;

//TODO: create builder
//TODO: create rx methods
//TODO: create enum representing device states, signals and brainwaves
public class NeuroSky {
  private TGDevice device;
  private boolean rawSignalEnabled = false;

  public NeuroSky(final DeviceMessageListener listener) {
    if (Preconditions.isBluetoothAdapterInitialized()) {
      DeviceMessageHandler handler = new DeviceMessageHandler(listener);
      device = new TGDevice(BluetoothAdapter.getDefaultAdapter(), handler);
    }
  }

  public void connect() {
    if(Preconditions.isBluetoothEnabled()) {
      //TODO: display message about enabling bluetooth
      return;
    }

    if (Preconditions.canConnect(device)) {
      device.connect(rawSignalEnabled);
    }
  }

  public void disconnect() {
    if (Preconditions.isConnected(device)) {
      device.close();
      device = null;
    }
  }

  public void enableRawSignal() {
    rawSignalEnabled = true;
  }

  public void disableRawSignal() {
    rawSignalEnabled = false;
  }

  public void startMonitoring() {
    if (Preconditions.isConnected(device)) {
      device.start();
    }
  }

  public void stopMonitoring() {
    if (Preconditions.isConnected(device)) {
      device.stop();
    }
  }
}
