package chladni

import doodle.image._
import doodle.image.syntax._
import doodle.core._
import doodle.java2d.java2dPngWriter
import doodle.effect.Writer._

object DoodleMain extends App {


  val coords = (BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.005)).map(_.toDouble)

  val q          = makeSquareGrid(coords)(EigenFunctions.w(5, 6))
  val normalised = normalise(q)

  val finalImage = normalised.map(_.map(valueToSquare).reduce(_ beside _)).reduce(_ above _)

  finalImage.write[Png]("hello.png")

  def getColour(value: Double): Color =
    math.floor(30 * math.abs(value)) match {
      case 0 => Color.aliceBlue
      case _ => Color.black
    }

  def normalise(grid: List[List[Double]]): List[List[Double]] = {
    val absoluteMax = math.max(math.abs(grid.flatten.max), math.abs(grid.flatten.min))
    grid.map(_.map(_ / absoluteMax))
  }

  def valueToSquare(value: Double) =
    Image
      .square(1)
      .strokeWidth(0.0)
      .fillColor(getColour(value))

  private def makeSquareGrid(coords: IndexedSeq[Double])(f: (Double, Double) => Double): List[List[Double]] =
    (for { x <- coords; y <- coords } yield f(x, y))
      .grouped(coords.length)
      .toList
      .map(_.toList)

}
