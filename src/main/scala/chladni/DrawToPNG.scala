package chladni

import java.awt.image.BufferedImage

import better.files._
import cats.effect.IO
import javax.imageio.ImageIO

object DrawToPNG {
  def draw(m: Int, n: Int)(fileName: String): IO[String] = {
    val coords            = (BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.001)).map(_.toDouble).zipWithIndex
    val zValues           = calculateZValues(coords)(EigenFunctions.w(m, n))
    val normalisedZValues = normalise(zValues)

    writeImage(normalisedZValues, coords.length, fileName)
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

  // This function is reasonably filthy but it appears to work so I am pleased
  def writeImage(normalisedValues: IndexedSeq[(Int, Int, Double)], size: Int, fileName: String): IO[String] =
    IO {
      val outputImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
      val file        = File(s"output/$fileName.png")
      val jFile       = file.createFileIfNotExists(createParents = true).toJava

      normalisedValues.foreach { case (x, y, value) => outputImage.setRGB(x, y, getColour(value)) }
      ImageIO.write(outputImage, "png", jFile)

      jFile.getAbsolutePath
    }

  def getColour(value: Double): Int =
    math.floor(50 * math.abs(value)) match {
      case 0 => 0x123442
      case _ => 0x9cdaf9
    }

}