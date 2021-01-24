package chladni

import java.io.File

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

object DoodleMain2 extends App {
  val coords = (BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.001)).map(_.toDouble)

  val q                = makeSquareGrid(coords)(EigenFunctions.w(3, 8))
  val normalisedValues = normalise(q)
  val flattenedCoords  =
    for {
      (ys, x)    <- normalisedValues.map(_.zipWithIndex).zipWithIndex
      (value, y) <- ys
    } yield (x, y, value)

  val out = new BufferedImage(q.length, q.length, BufferedImage.TYPE_INT_RGB)

  flattenedCoords.foreach { case (x, y, value) => out.setRGB(x, y, getColour(value)) }

  ImageIO.write(out, "png", new File("test.png"))

  def getColour(value: Double) =
    math.floor(30 * math.abs(value)) match {
      case 0 => 0xffffff
      case _ => 0x000000
    }

  def normalise(grid: List[List[Double]]): List[List[Double]] = {
    val absoluteMax = math.max(math.abs(grid.flatten.max), math.abs(grid.flatten.min))
    grid.map(_.map(_ / absoluteMax))
  }

  private def makeSquareGrid(coords: IndexedSeq[Double])(f: (Double, Double) => Double): List[List[Double]] =
    (for { x <- coords; y <- coords } yield f(x, y))
      .grouped(coords.length)
      .toList
      .map(_.toList)
}
