package chladni.utils

import scala.math.abs

object MathsHelpers {
  def isEven(x: Int): Boolean = x % 2 == 0

  def square(x: Double): Double = x * x

  def areApproxEqual(precision: Double)(a: Double, b: Double): Boolean =
    abs(a - b) < precision
}
