LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

# Only compile source java files in this apk.
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := sdtest
LOCAL_DEX_PREOPT := false
LOCAL_CERTIFICATE := platform

LOCAL_SDK_VERSION := current

include $(BUILD_PACKAGE)

