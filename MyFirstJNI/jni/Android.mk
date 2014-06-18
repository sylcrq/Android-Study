LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MyFirstJNI
LOCAL_SRC_FILES := MyFirstJNI.cpp

#MY_LOG_TAG := MyFirstJNI

#ifeq ($(APP_OPTIM),release)
#	MY_LOG_LEVEL := MY_LOG_LEVEL_ERROR
#else
#	MY_LOG_LEVEL := MY_LOG_LEVEL_VERBOSE
#endif

#LOCAL_CFLAGS += -DMY_LOG_TAG=$(MY_LOG_TAG)
#LOCAL_CFLAGS += -DMY_LOG_LEVEL=$(MY_LOG_LEVEL)

LOCAL_LDLIBS    += -llog

include $(BUILD_SHARED_LIBRARY)
