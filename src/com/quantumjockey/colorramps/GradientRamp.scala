package com.quantumjockey.colorramps

import javafx.scene.paint.Color
import scala.util.control._

class GradientRamp (colors: Array[Color], _tag: String, _lowerBound: Double, _upperBound: Double) {

  /////////// (Additional) Constructors /////////////////////////////////////////////////////

  def this(colors: Array[Color], _tag: String) {
    this(colors, _tag, 0.0, 1.0)
  }

  def this(colors: Array[Color]) {
    this(colors, "Unnamed Ramp")
  }

  /////////// Initialization ////////////////////////////////////////////////////////////////

  val tag: String = _tag
  val lowerBound: Double = _lowerBound
  val upperBound: Double = _upperBound

  private val count: Int = colors.length
  private val unit: Double = 1.0 / (count.toDouble - 1.0)
  private var i: Int = 0

  val ramp: Array[RampStop] = for (stop: Color <- colors) yield {
    val limit: Double = i.toDouble * unit
    i += 1
    new RampStop(stop, limit)
  }

  /////////// Public Methods ////////////////////////////////////////////////////////////////

  def getRampColorValue(offset: Double): Color = {

    var firstStop: RampStop = ramp(0)
    var secondStop: RampStop = ramp(ramp.length - 1)

    val maxByteValue: Int = 255
    var scaledVal: Double = offset

    val flowController = new Breaks

    if (offset < lowerBound) scaledVal = lowerBound
    if (offset > upperBound) scaledVal = upperBound

    flowController.breakable {
      ramp.foreach { (stop: RampStop) => {
        if (stop.offset < scaledVal && stop.offset > lowerBound) {
          firstStop = stop
        }

        if (stop.offset > scaledVal && stop.offset < upperBound) {
          secondStop = stop
          flowController.break
        }
      }
      }
    }

    Color.rgb(
      calculateChannelValue(firstStop, secondStop, 'R', scaledVal, maxByteValue),
      calculateChannelValue(firstStop, secondStop, 'G', scaledVal, maxByteValue),
      calculateChannelValue(firstStop, secondStop, 'B', scaledVal, maxByteValue)
    )
  }

  /////////// Private Methods ///////////////////////////////////////////////////////////////

  private def calculateChannelValue(_before: RampStop, _after: RampStop, _colorComponent: Char, _offset: Double, _maxValue: Int): Int = {

    val afterOffset: Double = _after.offset
    val afterColorChannelValue: Double = getRgbChannelValue(_after.color, _colorComponent)

    val beforeOffset: Double = _before.offset
    val beforeColorChannelValue: Double = getRgbChannelValue(_before.color, _colorComponent)

    val max: Int = _maxValue / 255

    val channelRange: Double = afterColorChannelValue - beforeColorChannelValue
    val scaleFactor: Double = (_offset - beforeOffset) / (afterOffset - beforeOffset)

    val newChannel: Float = scaleFactor.toFloat * channelRange.toFloat
    val result: Float = newChannel + beforeColorChannelValue.toFloat
    val byteValue: Int = ((if (result < max) result else max) * 255).toInt

    byteValue
  }

  private def getRgbChannelValue(_color: Color, _component: Char): Double = {
    _component match {
      case 'R' => _color.getRed
      case 'G' => _color.getGreen
      case 'B' => _color.getBlue
      case _ => _color.getRed
    }
  }

}
