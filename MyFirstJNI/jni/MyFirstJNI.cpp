#include <jni.h>

#include "com_example_myfirstjni_NativeClass.h"
#include "my_log.h"

/*
 * Class:     com_example_myfirstjni_NativeClass
 * Method:    HelloWorld
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_myfirstjni_NativeClass_HelloWorld
  (JNIEnv *, jclass)
{
	MY_LOG_INFO("Hello World From JNI!");
	//MY_LOG_ERROR("Hello World From JNI!");
}
