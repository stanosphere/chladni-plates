package chladni

import java.awt.image.BufferedImage
import java.io.File

import cats.effect.IO
import javax.imageio.ImageIO

object DrawToPNG {
  def draw(m: Int, n: Int)(filePath: String): IO[Unit] = {
    val coords            = (BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.001)).map(_.toDouble).zipWithIndex
    val zValues           = calculateZValues(coords)(EigenFunctions.w(m, n))
    val normalisedZValues = normalise(zValues)

    writeImage(normalisedZValues, coords.length, filePath)
  }

  def calculateZValues(
      coords: IndexedSeq[(Double, Int)]
  )(f: (Double, Double) => Double): IndexedSeq[(Int, Int, Double)] =
    for {
      (x_val, x_int) <- coords
      (y_val, y_int) <- coords
    } yield (x_int, y_int, f(x_val, y_val))

  def normalise(grid: IndexedSeq[(Int, Int, Double)]): IndexedSeq[(Int, Int, Double)] = {
    val values      = grid.map { case (_, _, value) => value }
    val absoluteMax = math.max(math.abs(values.max), math.abs(values.min))
    grid.map { case (x, y, value) => (x, y, value / absoluteMax) }
  }

  def writeImage(normalisedValues: IndexedSeq[(Int, Int, Double)], size: Int, filePath: String): IO[Unit] =
    IO {
      val out = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)

      normalisedValues.foreach { case (x, y, value) => out.setRGB(x, y, getColour(value)) }

      ImageIO.write(out, "png", new File(filePath))
    }

  def getColour(value: Double): Int =
    math.floor(50 * math.abs(value)) match {
      case 0 => 0x123442
      case _ => 0x9cdaf9
    }

}
