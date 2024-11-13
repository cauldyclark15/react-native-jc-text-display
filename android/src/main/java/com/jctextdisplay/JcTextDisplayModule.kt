package com.jctextdisplay

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = JcTextDisplayModule.NAME)
class JcTextDisplayModule(reactContext: ReactApplicationContext) :
  NativeJcTextDisplaySpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  companion object {
    const val NAME = "JcTextDisplay"
  }
}
