package com.quantumjockey.colorramps

import javafx.scene.paint.Color

class GradientRamp (colors: Array[Color], val tag: String, val lowerBound: Double, val upperBound: Double) {

  // (Additional) Constructors
  def this(colors: Array[Color], _tag: String) = this(colors, _tag, 0.0, 1.0)

  def this(colors: Array[Color]) = this(colors, "Unnamed Ramp")

  // Constants
  val MaxByteValue: Int = 255
  val MaxScaleValue: Double = 1.0

  // Initialization
  private val unit: Double = MaxScaleValue / (colors.length.toDouble - MaxScaleValue)

  val ramp: IndexedSeq[RampStop] = for (i <- colors.indices) yield {
    val limit: Double = i.toDouble * unit
    new RampStop(colors.apply(i), limit)
  }

  // Public Methods

  def getRampColorValue(offset: Double): Color = {

    val scaledVal: Double = offset match {
      case x if x < lowerBound => lowerBound
      case x if x > upperBound => upperBound
      case _ => offset
    }

    val firstStop = {
      val matching = ramp.takeWhile((stop: RampStop) => stop.offset < scaledVal && stop.offset > lowerBound)
      if (matching.length > 0) matching(matching.length - 1) else ramp(0)
    }

    val secondStop: RampStop = {
      val matching = ramp.takeWhile((stop: RampStop) => stop.offset > scaledVal && stop.offset < upperBound)
      if (matching.length > 0) matching(0) else ramp(ramp.length - 1)
    }

    Color.rgb(
      calculateChannelValue(firstStop, secondStop, 'R', scaledVal, MaxByteValue),
      calculateChannelValue(firstStop, secondStop, 'G', scaledVal, MaxByteValue),
      calculateChannelValue(firstStop, secondStop, 'B', scaledVal, MaxByteValue)
    )
  }

  // Private Methods

  private def calculateChannelValue(_before: RampStop, _after: RampStop, _colorComponent: Char, _offset: Double, _maxValue: Int): Int = {

    val afterOffset: Double = _after.offset
    val afterColorChannelValue: Double = getRgbChannelValue(_after.color, _colorComponent)

    val beforeOffset: Double = _before.offset
    val beforeColorChannelValue: Double = getRgbChannelValue(_before.color, _colorComponent)

    val max: Int = _maxValue / MaxByteValue

    val channelRange: Double = afterColorChannelValue - beforeColorChannelValue
    val scaleFactor: Double = (_offset - beforeOffset) / (afterOffset - beforeOffset)

    val newChannel: Float = scaleFactor.toFloat * channelRange.toFloat
    val result: Float = newChannel + beforeColorChannelValue.toFloat
    val byteValue: Int = ((if (result < max) result else max) * MaxByteValue).toInt

    byteValue
  }

  private def getRgbChannelValue(_color: Color, _component: Char): Double = _component match {
    case 'R' => _color.getRed
    case 'G' => _color.getGreen
    case 'B' => _color.getBlue
    case _ => _color.getRed
  }

}
