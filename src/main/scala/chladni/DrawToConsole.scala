package chladni

import EigenFunctions.{w => freeSquarePlateEigenFunction}
import MathsHelpers._
import cats.effect.IO

object DrawToConsole {

  def draw(m: Int, n: Int): IO[Unit] =
    IO {
      val coords = (BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.02)).map(_.toDouble)
      val grid   = makeSquareGrid(coords)(freeSquarePlateEigenFunction(m, n))

      showInConsole(grid)
    }

  private def makeSquareGrid(coords: IndexedSeq[Double])(f: (Double, Double) => Double): List[List[Double]] =
    (for { x <- coords; y <- coords } yield f(x, y))
      .grouped(coords.length)
      .toList
      .map(_.toList)

  private def closeToZero(x: Double): Boolean =
    areApproxEqual(0.2)(x, 0)

  private val rowToStringRep: List[Double] => String =
    _.map(i => if (closeToZero(i)) "  " else "##").mkString

  def showInConsole(grid: List[List[Double]]): Unit =
    grid.foreach(rowToStringRep andThen println)
}