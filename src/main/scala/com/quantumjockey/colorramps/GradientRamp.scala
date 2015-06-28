package com.quantumjockey.colorramps

import javafx.scene.paint.Color

class GradientRamp (colors: Array[Color], val tag: String, val lowerBound: Double, val upperBound: Double) {

  // Constants

  val MAX_BYTE_VALUE: Int = 255

  // (Additional) Constructors

  def this(colors: Array[Color], _tag: String) = this(colors, _tag, 0.0, 1.0)

  def this(colors: Array[Color]) = this(colors, "Unnamed Ramp")

  // Initialization

  private val unit: Double = 1.0 / (colors.length.toDouble - 1.0)
  private var i: Int = 0

  val ramp: Array[RampStop] = for (stop: Color <- colors) yield {
    val limit: Double = i.toDouble * unit
    i += 1
    new RampStop(stop, limit)
  }

  // Public Methods

  def getRampColorValue(offset: Double): Color = {

    var firstStop: RampStop = ramp(0)
    var secondStop: RampStop = ramp(ramp.length - 1)

    val scaledVal: Double = offset match {
      case x if x < lowerBound => lowerBound
      case x if x > upperBound => upperBound
      case _ => offset
    }

    def getFirst = (stop: RampStop) => {
      if (stop.offset < scaledVal && stop.offset > lowerBound) {
        firstStop = stop
      }
    }

    def getSecond = (stop: RampStop) => {
      if (stop.offset > scaledVal && stop.offset < upperBound) {
        secondStop = stop
      }
    }

    ramp.foreach { (stop: RampStop) => {
      getFirst(stop)
      getSecond(stop)
    }
    }

    Color.rgb(
      calculateChannelValue(firstStop, secondStop, 'R', scaledVal, MAX_BYTE_VALUE),
      calculateChannelValue(firstStop, secondStop, 'G', scaledVal, MAX_BYTE_VALUE),
      calculateChannelValue(firstStop, secondStop, 'B', scaledVal, MAX_BYTE_VALUE)
    )
  }

  // Private Methods

  private def calculateChannelValue(_before: RampStop, _after: RampStop, _colorComponent: Char, _offset: Double, _maxValue: Int): Int = {

    val afterOffset: Double = _after.offset
    val afterColorChannelValue: Double = getRgbChannelValue(_after.color, _colorComponent)

    val beforeOffset: Double = _before.offset
    val beforeColorChannelValue: Double = getRgbChannelValue(_before.color, _colorComponent)

    val max: Int = _maxValue / MAX_BYTE_VALUE

    val channelRange: Double = afterColorChannelValue - beforeColorChannelValue
    val scaleFactor: Double = (_offset - beforeOffset) / (afterOffset - beforeOffset)

    val newChannel: Float = scaleFactor.toFloat * channelRange.toFloat
    val result: Float = newChannel + beforeColorChannelValue.toFloat
    val byteValue: Int = ((if (result < max) result else max) * MAX_BYTE_VALUE).toInt

    byteValue
  }

  private def getRgbChannelValue(_color: Color, _component: Char): Double = _component match {
    case 'R' => _color.getRed
    case 'G' => _color.getGreen
    case 'B' => _color.getBlue
    case _ => _color.getRed
  }

}
