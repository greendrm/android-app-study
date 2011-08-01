package com.cengen.android.pchost;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements Runnable
{
  private final Logger logger = LoggerFactory.getLogger("PCHost");

  private UsbManager usbManager;

  UsbAccessory accessory;
  ParcelFileDescriptor accessoryFileDescriptor;
  FileInputStream accessoryInput;
  FileOutputStream accessoryOutput;

  private final BroadcastReceiver usbBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context context, Intent intent)
    {
      String action = intent.getAction();
      if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action))
      {
        synchronized (this)
        {
          accessory = UsbManager.getAccessory(intent);
        }
      }
      else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action))
      {
        UsbAccessory accessory = UsbManager.getAccessory(intent);
        if (accessory != null)
        {
          // call your method that cleans up and closes communication with the accessory
        }
      }
    }
  };

  Handler messageHandler = new Handler()
  {
    @Override
    public void handleMessage(Message msg)
    {
      switch (msg.what)
      {
        case 1:
          logger.info("Got message type {}", msg.what);
//              SendFileMessage m = (SendFileMessage) msg.obj;
//              handleSendFile(m);
        break;
        case 2:
          logger.info("Got message type {}", msg.what);
//              SendFileMessage m = (SendFileMessage) msg.obj;
//              handleSendFile(m);
        break;
        case 3:
          logger.info("Got message type {}", msg.what);
//              SendFileMessage m = (SendFileMessage) msg.obj;
//              handleSendFile(m);
        break;
      }
    }
  };

  /**
   * Main USB reading loop, processing incoming data from accessory and parsing
   * it into messages via the defined format.
   */
  public void run()
  {
    int ret = 0;
    byte[] buffer = new byte[16384];
    int i;

    while (ret >= 0)
    {
      try
      {
        ret = accessoryInput.read(buffer);
        logger.debug("Read {} bytes.", ret);
      }
      catch (IOException e)
      {
        logger.debug("Exception in USB accessory input reading", e);
        break;
      }

      i = 0;
      while (i < ret)
      {
        int len = ret - i;

        switch (buffer[i])
        {
          case 0x1:
            if (len >= 3)
            {
              Message m = Message.obtain(messageHandler, 1);
//                      m.obj = new MessageTypeOne(buffer[i + 1], buffer[i + 2]);
              messageHandler.sendMessage(m);
            }
            i += 3;
            break;

          case 0x4:
            if (len >= 3)
            {
              Message m = Message.obtain(messageHandler, 1);
//                      m.obj = new MessageTypeTwo(buffer[i + 1], buffer[i + 2]);
              messageHandler.sendMessage(m);
            }
            i += 3;
            break;

          default:
            logger.debug("unknown msg: " + buffer[i]);
            i = len;
            break;
        }
      }

    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    usbManager = UsbManager.getInstance(this);

    IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
    filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
    registerReceiver(usbBroadcastReceiver, filter);

    if (getLastNonConfigurationInstance() != null)
    {
      accessory = (UsbAccessory) getLastNonConfigurationInstance();
      openAccessory(accessory);
    }
  }

  @Override
  public Object onRetainNonConfigurationInstance()
  {
    return accessory != null ? accessory : super.onRetainNonConfigurationInstance();
  }

 @Override
 public void onResume()
 {
   super.onResume();

   Intent intent = getIntent();
   if (accessoryInput != null && accessoryOutput != null)
     return;

   // TODO: verify, docs don't do this simple thing, not sure why?
   UsbAccessory accessory = UsbManager.getAccessory(intent);
   if (accessory != null)
     openAccessory(accessory);
   else
     logger.error("Failed to resume accessory.");
 }

  @Override
  public void onPause()
  {
    super.onPause();
    closeAccessory();
  }

  @Override
  public void onDestroy()
  {
    unregisterReceiver(usbBroadcastReceiver);
    super.onDestroy();
  }

  private void openAccessory(UsbAccessory accessory)
  {
    accessoryFileDescriptor = usbManager.openAccessory(accessory);
    if (accessoryFileDescriptor != null)
    {
      this.accessory = accessory;
      FileDescriptor fd = accessoryFileDescriptor.getFileDescriptor();
      accessoryInput = new FileInputStream(fd);
      accessoryOutput = new FileOutputStream(fd);
      Thread thread = new Thread(null, this, "AndroidPCHost");
      thread.start();
      logger.debug("accessory opened");
      // TODO: enable USB operations in the app
    }
    else
    {
      logger.debug("accessory open fail");
    }
  }

  private void closeAccessory()
  {
    // TODO: disable USB operations in the app
    try
    {
      if (accessoryFileDescriptor != null)
        accessoryFileDescriptor.close();
    }
    catch (IOException e)
    {}
    finally
    {
      accessoryFileDescriptor = null;
      accessory = null;
    }
  }
}