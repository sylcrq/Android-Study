package com.example.myfirstjni;

public class NativeClass {

	static
	{
		System.loadLibrary("MyFirstJNI");
	}
	
	public static native void HelloWorld();
}
