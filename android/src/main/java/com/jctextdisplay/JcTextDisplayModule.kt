package com.jctextdisplay

import android.os.Handler
import android.os.Looper
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = JcTextDisplayModule.NAME)
class JcTextDisplayModule(reactContext: ReactApplicationContext) :
  NativeJcTextDisplaySpec(reactContext) {

  companion object {
    const val NAME = "JcTextDisplay"
    
    // Constants
    const val DEFAULT_BRIGHTNESS = 1
    const val RED = 0xa1
    const val GREEN = 0xa2
    const val BLUE = 0xa3
    const val TEAL = 0xa4
    const val PINK = 0xa5
    const val YELLOW = 0xa6
    const val WHITE = 0xa7
    
    private val ALL_COLORS = intArrayOf(RED, YELLOW, GREEN, TEAL, BLUE, PINK, WHITE)
    
    init {
      System.loadLibrary("jnielc")
    }
  }

  // Properties
  private val ledHandler = Handler(Looper.getMainLooper())
  private var ledBrightness = DEFAULT_BRIGHTNESS
  private var ledBrightnessIsIncreasing = true
  private var activeColor = 0
  private var activeIsBlinking = false
  private var colorIterator = 0

  // Native method declarations
  private external fun ledoff(): Int
  private external fun seekstart(): Int
  private external fun seekstop(): Int
  private external fun ledseek(flag: Int, progress: Int): Int

  override fun getName(): String {
    return NAME
  }

  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }

  override fun greet(name: String): String {
    return "Hello, $name!"
  }

  @ReactMethod
  override fun getRED() = RED.toDouble()

  @ReactMethod
  override fun getGREEN() = GREEN.toDouble()

  @ReactMethod
  override fun getBLUE() = BLUE.toDouble()

  @ReactMethod
  override fun getTEAL() = TEAL.toDouble()

  @ReactMethod
  override fun getPINK() = PINK.toDouble()

  @ReactMethod
  override fun getYELLOW() = YELLOW.toDouble()

  @ReactMethod
  override fun getWHITE() = WHITE.toDouble()

  @ReactMethod
  override fun turnLedsOffAndDisableCallbacks() {
    try {
      activeColor = 0
      ledHandler.removeCallbacksAndMessages(null)
      seekstart()
      ledoff()
      seekstop()
    } catch (ignored: Exception) {
    }
  }

  private fun turnLedsOffTemporarily() {
    try {
      activeColor = 0
      seekstart()
      ledoff()
      seekstop()
    } catch (ignored: Exception) {
    }
  }

  @ReactMethod
  override fun startLedSolid(ledColor: Double, serialUsbDevice: String?, rgbColorForSerial: String?) {
    try {
      val intLedColor = ledColor.toInt()
      if (!activeIsBlinking && activeColor == intLedColor) return
      ledHandler.removeCallbacksAndMessages(null)
      seekstart()
      ledseek(intLedColor, DEFAULT_BRIGHTNESS)
      ledseek(intLedColor + 16, DEFAULT_BRIGHTNESS)
      activeColor = intLedColor
      activeIsBlinking = false
      
      loopInSolidHandler(intLedColor, null, rgbColorForSerial)
      seekstop()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun loopInSolidHandler(ledColor: Int, serialUsbDevice: Any?, rgbColorForSerial: String?) {
    ledHandler.postDelayed({
      loopInSolidHandler(ledColor, serialUsbDevice, rgbColorForSerial)
      seekstart()
      ledseek(ledColor, ledBrightness)
      ledseek(ledColor + 16, ledBrightness)
      seekstop()
    }, 10000)
  }

  @ReactMethod
  override fun loopInCyclingColors() {
    ledHandler.postDelayed({
      try {
        ledHandler.removeCallbacksAndMessages(null)
        loopInCyclingColors()
        if (colorIterator >= ALL_COLORS.size) colorIterator = 0
        activeColor = ALL_COLORS[colorIterator]
        colorIterator++
        ledBrightness = DEFAULT_BRIGHTNESS
        seekstart()
        ledseek(activeColor, ledBrightness)
        ledseek(activeColor + 16, ledBrightness)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }, 2000)
  }

  override fun getConstants(): Map<String, Any> {
    val constants = HashMap<String, Any>()
    constants["RED"] = RED
    constants["GREEN"] = GREEN
    constants["BLUE"] = BLUE
    constants["TEAL"] = TEAL
    constants["PINK"] = PINK
    constants["YELLOW"] = YELLOW
    constants["WHITE"] = WHITE
    return constants
  }
}
