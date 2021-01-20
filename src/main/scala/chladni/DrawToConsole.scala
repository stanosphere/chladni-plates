package chladni

import EigenFunctions.{w => freeSquarePlateEigenFunction}
import MathsHelpers._

object DrawToConsole extends App {

  val N = 5
  val M = 6

  val xs   = BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.02)
  val ys   = xs
  val blah = (for { x <- xs; y <- ys } yield freeSquarePlateEigenFunction(M, N)(x.toDouble, y.toDouble)).toList
    .grouped(xs.length)
    .toList

  def closeToZero(x: Double): Boolean =
    areApproxEqual(0.2)(x, 0)

  val rowToStringRep: List[Double] => String =
    _.map(i => if (closeToZero(i)) "  " else "##").mkString

  def showInConsole(values: List[List[Double]]): Unit =
    values.foreach(rowToStringRep andThen println)

  showInConsole(blah)
}
