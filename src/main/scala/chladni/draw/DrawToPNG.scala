package chladni.draw

import java.awt.image.BufferedImage

import better.files.File
import cats.effect.IO
import chladni.EigenFunctions
import javax.imageio.ImageIO

object DrawToPNG {
  def draw(f: (Double, Double) => Double)(fileName: String): IO[String] =
    for {
      coords           <- IO((BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.001)).map(_.toDouble).zipWithIndex)
      zValues           = calculateZValues(coords)(f)
      normalisedZValues = normalise(zValues)
      fullFilePath     <- writeImage(normalisedZValues, coords.length, fileName)
    } yield fullFilePath

  def drawBasisFn(m: Int, n: Int)(fileName: String): IO[String] =
    draw(EigenFunctions.w(m, n))(fileName)

  private def calculateZValues(
      coords: IndexedSeq[(Double, Int)]
  )(f: (Double, Double) => Double): IndexedSeq[(Int, Int, Double)] =
    for {
      (x_val, x_int) <- coords
      (y_val, y_int) <- coords
    } yield (x_int, y_int, f(x_val, y_val))

  private def normalise(grid: IndexedSeq[(Int, Int, Double)]): IndexedSeq[(Int, Int, Double)] = {
    val values      = grid.map { case (_, _, value) => value }
    val absoluteMax = math.max(math.abs(values.max), math.abs(values.min))
    grid.map { case (x, y, value) => (x, y, value / absoluteMax) }
  }

  // This function is reasonably filthy but it appears to work so I am pleased
  private def writeImage(normalisedValues: IndexedSeq[(Int, Int, Double)], size: Int, fileName: String): IO[String] =
    IO {
      val outputImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
      val file        = File(s"output/$fileName.png")
      val jFile       = file.createFileIfNotExists(createParents = true).toJava

      normalisedValues.foreach { case (x, y, value) => outputImage.setRGB(x, y, getColour(value)) }
      ImageIO.write(outputImage, "png", jFile)

      jFile.getAbsolutePath
    }

  private def getColour(value: Double): Int =
    math.floor(20 * value) match {
      case 0  => 0x660051
      case 1  => 0x85004e
      case 2  => 0xa30041
      case 3  => 0xba042f
      case 4  => 0xcf0d18
      case 5  => 0xe23018
      case 6  => 0xe83436
      case 7  => 0xed5066
      case 8  => 0xf26d91
      case 9  => 0xf68ab5
      case 10 => 0xf9a8d3
      case _ => 0xfcc7ea
    }

}
