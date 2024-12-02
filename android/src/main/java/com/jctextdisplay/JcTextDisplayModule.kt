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
  fun turnLedsOffAndDisableCallbacks() {
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
  fun startLedSolid(ledColor: Int, serialUsbDevice: Any? = null, rgbColorForSerial: String? = null) {
    try {
      if (!activeIsBlinking && activeColor == ledColor) return
      ledHandler.removeCallbacksAndMessages(null)
      seekstart()
      ledseek(ledColor, DEFAULT_BRIGHTNESS)
      ledseek(ledColor + 16, DEFAULT_BRIGHTNESS)
      activeColor = ledColor
      activeIsBlinking = false
      
      // Note: SerialUsbDevice functionality would need to be implemented
      // serialUsbDevice?.WritePortColor(rgbColorForSerial)
      
      loopInSolidHandler(ledColor, serialUsbDevice, rgbColorForSerial)
      seekstop()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @ReactMethod
  fun startLedBlinking(ledColor: Int, serialUsbDevice: Any? = null, rgbColorForSerial: String? = null) {
    try {
      if (activeIsBlinking && activeColor == ledColor) return
      ledHandler.removeCallbacksAndMessages(null)
      activeColor = ledColor
      activeIsBlinking = true
      ledBrightness = DEFAULT_BRIGHTNESS
      seekstart()
      ledseek(ledColor, ledBrightness)
      ledseek(ledColor + 16, ledBrightness)
      
      // Note: SerialUsbDevice functionality would need to be implemented
      // serialUsbDevice?.WritePortColor(rgbColorForSerial)
      
      loopInBlinkingHandler(ledColor, serialUsbDevice, rgbColorForSerial)
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
      
      // Note: SerialUsbDevice functionality would need to be implemented
      // serialUsbDevice?.WritePortColor(rgbColorForSerial)
    }, 10000)
  }

  private fun loopInBlinkingHandler(ledColor: Int, serialUsbDevice: Any?, rgbColorForSerial: String?) {
    ledHandler.postDelayed({
      loopInBlinkingHandler(ledColor, serialUsbDevice, rgbColorForSerial)
      incrementLedBrightness()
      if (ledBrightness == 0) {
        turnLedsOffTemporarily()
        // Note: SerialUsbDevice functionality would need to be implemented
        // when (ledColor) {
        //     BLUE -> serialUsbDevice?.WritePortColor("0,0,0")
        //     ledoff() -> serialUsbDevice?.WritePortColor("0,0,0")
        // }
      } else {
        seekstart()
        ledseek(ledColor, ledBrightness)
        ledseek(ledColor + 16, ledBrightness)
        seekstop()
        // serialUsbDevice?.WritePortColor(rgbColorForSerial)
      }
    }, 800)
  }

  private fun incrementLedBrightness() {
    ledBrightness = if (ledBrightness == DEFAULT_BRIGHTNESS) 0 else DEFAULT_BRIGHTNESS
  }

  @ReactMethod
  fun startHolidayBlinking(serialUsbDevice: Any? = null) {
    try {
      ledHandler.removeCallbacksAndMessages(null)
      loopInHolidayHandler(serialUsbDevice)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun loopInHolidayHandler(serialUsbDevice: Any?) {
    ledHandler.postDelayed({
      loopInHolidayHandler(serialUsbDevice)
      // alternate green and red
      activeColor = if (activeColor == ledoff()) GREEN else ledoff()
      activeIsBlinking = true
      ledBrightness = DEFAULT_BRIGHTNESS
      seekstart()
      ledseek(activeColor, ledBrightness)
      ledseek(activeColor + 16, ledBrightness)
      
      // Note: SerialUsbDevice functionality would need to be implemented
      // if (serialUsbDevice != null) {
      //     if (activeColor == ledoff()) {
      //         serialUsbDevice.WritePortColor("255,0,0")
      //     } else {
      //         serialUsbDevice.WritePortColor("0,255,0")
      //     }
      // }
    }, 300)
  }

  @ReactMethod
  fun loopInCyclingColors() {
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
