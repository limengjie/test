package org.crazyit.io;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbDevice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;

import me.jahnen.libaums.core.UsbMassStorageDevice;
import me.jahnen.libaums.core.fs.FileSystem;
import me.jahnen.libaums.core.fs.UsbFile;
import me.jahnen.libaums.core.fs.UsbFileOutputStream;
import me.jahnen.libaums.core.partition.Partition;


/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2020, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com<br>
 * @version 1.0
 */
public class MainActivity extends Activity {
	private static final String FILE_NAME = "/crazyit.bin";
	private EditText edit1;
	private TextView edit2;
	private static final String ACTION_USB_PERMISSION =
		"com.android.example.USB_PERMISSION";
	private static final String TAG = "wr_usb";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		// 获取两个按钮
		Button read = findViewById(R.id.read);
		Button write = findViewById(R.id.write);
		// 获取两个文本框
		edit1 = findViewById(R.id.edit1);
		edit2 = findViewById(R.id.edit2);
		// // 为write按钮绑定事件监听器
		// write.setOnClickListener(view ->
		// 	// 运行时请求获取写入SD卡的权限
		// 	requestPermissions(new String[]
		// 		{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x123)); // ①

		// read.setOnClickListener(view ->
		// 	requestPermissions(new String[]
		// 		{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x456));

		//U盘
		Log.d(TAG, "test");
		UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		registerReceiver(usbReceiver, filter);
		Log.d(TAG, "test0");

		UsbMassStorageDevice[] devices = UsbMassStorageDevice.getMassStorageDevices(this /* Context or Activity */);

		if (devices != null && devices.length > 0) {
			Log.d(TAG, "request permission");
			usbManager.requestPermission(devices[0].getUsbDevice(), permissionIntent);
			try {
				devices[0].init();
				//// Only uses the first partition on the device
				List<Partition> partitions = devices[0].getPartitions();
				if (partitions != null && partitions.size() > 0) {
					Log.d(TAG, "found partition");
					FileSystem currentFs = partitions.get(0).getFileSystem();
					if (currentFs != null) {
						Log.d(TAG, "Capacity: " + currentFs.getCapacity());
						Log.d(TAG, "Occupied Space: " + currentFs.getOccupiedSpace());
						Log.d(TAG, "Free Space: " + currentFs.getFreeSpace());
						Log.d(TAG, "Chunk size: " + currentFs.getChunkSize());
						UsbFile root = currentFs.getRootDirectory();
						Log.d(TAG, "test1");
						UsbFile[] files = root.listFiles();
						for(UsbFile file: files) {
							Log.d(TAG, file.getName());
							//if(file != null && file.isDirectory()) {
							//	Log.d(TAG, String.valueOf(file.getLength()));
							//}
						}

						UsbFile newDir = root.createDirectory("foo");
						UsbFile file = newDir.createFile("bar.txt");

						// write to a file

						OutputStream os = new UsbFileOutputStream(file);
						for(int i = 0; i < 10000; i++){
							os.write("hello ".getBytes());
						}
						os.close();
					} else
						Log.d(TAG, "empty fs");
				} else
					Log.d(TAG, "no partition");
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if(device != null){
						//call method to set up device communication
					}
					}
					else {
						Log.d(TAG, "permission denied for device " + device);
					}
				}
			}
		}
	};

	//private void writeUSB() {

	//}

	private String read() {
		// 如果手机插入了SD卡，而且应用程序具有访问SD卡的权限
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			// 获取SD卡对应的存储目录
			//File sdCardDir = Environment.getExternalStorageDirectory();
			//File sdCardDir = "/storage/photos/";
			try(
				// 获取指定文件对应的输入流
				//FileInputStream fis = new FileInputStream("/data/local/tmp/1.txt");
				//FileInputStream fis = new FileInputStream("/mnt/vendor/qnx_multimedia_nfs/photos/1.txt");
				//FileInputStream fis = new FileInputStream("/storage/photos/1.txt");
				//FileInputStream fis = new FileInputStream("/data/local/1.txt");
				FileInputStream fis = new FileInputStream("/storage/map/1.txt");
				//FileInputStream fis = new FileInputStream("/mnt/vendor/res2/map/1.txt");
				//FileInputStream fis = new FileInputStream("/mnt/runtime/write/map/1.txt");
				// 将指定输入流包装成BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(fis))
			) {
				StringBuilder sb = new StringBuilder();
				String line = null;
				// 循环读取文件内容
				while ((line = br.readLine()) != null)
				{
					sb.append(line);
				}
				return sb.toString();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	private void write(String content)
	{
		// 获取SD卡的目录
		File sdCardDir = Environment.getExternalStorageDirectory();
		try
		{
			File targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
			// 以指定文件创建 RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
			// 将文件记录指针移动到最后
			raf.seek(targetFile.length());
			// 输出文件内容
			raf.write(content.getBytes());
			// 关闭RandomAccessFile
			raf.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
		String[] permissions, int[] grantResults)
	{
		if (requestCode == 0x123)
		{
			// 如果用户同意授权访问
			if(grantResults != null &&  grantResults[0] ==
				PackageManager.PERMISSION_GRANTED )
			{
				write(edit1.getText().toString());
				edit1.setText("");
			}
			else
			{
				// 提示用户必须允许写入SD卡的权限
				Toast.makeText(this, R.string.writesd_tip, Toast.LENGTH_LONG)
						.show();
			}
		}
		if (requestCode == 0x456)
		{
			// 如果用户同意授权访问
			if(grantResults != null && grantResults[0] ==
				PackageManager.PERMISSION_GRANTED )
			{
				// 读取指定文件中的内容，并显示出来
				edit2.setText(read());
			}
			else
			{
				// 提示用户必须允许写入SD卡的权限
				Toast.makeText(this, R.string.writesd_tip, Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}
