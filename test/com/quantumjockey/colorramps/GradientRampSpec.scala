package com.quantumjockey.colorramps

import javafx.scene.paint.Color

import org.scalatest._

class GradientRampSpec extends FlatSpec with Matchers {

  /////////// Setup /////////////////////////////////////////////////////////////////////////

  var stops: Array[Color] = Array(Color.VIOLET, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED)
  var ramp = new GradientRamp(stops)

  /////////// Requirements //////////////////////////////////////////////////////////////////

  "A Gradient Ramp" should "return the color sampled from a specified range" in {
    ramp.getRampColorValue(0.6, 0.0, 1.0) should be (Color.YELLOW)
  }

  it should "return highest color if given input above limit" in {
    ramp.getRampColorValue(2.0, 0.0, 1.0) should be (Color.RED)
  }

  it should "return lowest color if given input below limit" in {
    ramp.getRampColorValue(-1.0, 0.0, 1.0) should be (Color.VIOLET)
  }

  it should "return highest color if input equal to max bound" in {
    ramp.getRampColorValue(1.0, 0.0, 1.0) should be (Color.RED)
  }

  it should "return lowest color if input equal to min bound" in {
    ramp.getRampColorValue(0.0, 0.0, 1.0) should be (Color.VIOLET)
  }

}
