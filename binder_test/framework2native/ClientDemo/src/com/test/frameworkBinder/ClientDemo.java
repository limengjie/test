package com.test.frameworkBinder;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Parcel;
import android.util.Log;
public class ClientDemo {
  private static final java.lang.String DESCRIPTOR = "my_hello";
  private static final int FUNC_CALLFUNCTION = 2;
  public static void main(String[] args) throws RemoteException {
    testService();
  }
  public static void testService() {
    Log.i("ClentDemo", "Client main ");
    Parcel _data = Parcel.obtain();
    Parcel _reply = Parcel.obtain();
    IBinder b = ServiceManager.getService(DESCRIPTOR);
    _data.writeInt32(7777);
    try {
      _data.writeInterfaceToken(DESCRIPTOR);
      b.transact(FUNC_CALLFUNCTION, _data, _reply, 0);
      _reply.readException();
      _reply.readInt();
    } catch (RemoteException e) {
      e.printStackTrace();
    } finally {
      _reply.recycle();
      _data.recycle();
    }
  }
}
