package chladni

import java.io.File

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import EigenFunctions._

object Timed {
  def apply[A](name: String)(block: => A): A = {
    val start = System.currentTimeMillis()
    val res   = block
    val end   = System.currentTimeMillis()
    println(s"$name took ${end - start} ms to run")
    res
  }
}

object DoodleMain2 extends App {
  val coords =
    Timed("making coord array")((BigDecimal(-1.0) to BigDecimal(1.0) by BigDecimal(0.001)).map(_.toDouble).zipWithIndex)

  val something = Timed("calculating values") {
    for {
      (x_val, x_int) <- coords
      (y_val, y_int) <- coords
    } yield (x_int, y_int, w(2,3)(x_val, y_val) + w(5,7)(x_val, y_val) + u(11)(x_val) * u(13)(y_val))
  }

  val normalisedValues = Timed("normalisation")(normalise(something))

  val out = new BufferedImage(coords.length, coords.length, BufferedImage.TYPE_INT_RGB)

  Timed("updating the bufferedimage") {
    normalisedValues.foreach { case (x, y, value) => out.setRGB(x, y, getColour(value)) }
  }

  Timed("writing the image to file") {
    ImageIO.write(out, "png", new File("test.png"))
  }

  def getColour(value: Double) =
    math.floor(50 * math.abs(value)) match {
//      case 0 => 0xffffff
//      case 1 => 0x000000
      case 0  => 0x123442
//      case 2  => 0x1b475d
//      case 3  => 0x245877
//      case 4  => 0x2e6890
//      case 5  => 0x3877a8
//      case 6  => 0x4484bf
//      case 7  => 0x4f92d6
//      case 8  => 0x5c9eec
//      case 9  => 0x66aaef
//      case 10 => 0x71b5f1
//      case 11 => 0x7bbff3
//      case 12 => 0x86c9f5
//      case 13 => 0x91d2f7
      case _  => 0x9cdaf9
    }

  def normalise(grid: IndexedSeq[(Int, Int, Double)]): IndexedSeq[(Int, Int, Double)] = {
    val values      = grid.map { case (_, _, value) => value }
    val absoluteMax = math.max(math.abs(values.max), math.abs(values.min))
    grid.map { case (x, y, value) => (x, y, value / absoluteMax) }
  }

}
