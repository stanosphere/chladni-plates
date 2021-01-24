package chladni

import java.io.File

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

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
    } yield (x_int, y_int, EigenFunctions.w(3, 8)(x_val, y_val))
  }

  val normalisedValues = Timed("normalisation")(normalise(something))

  val out = new BufferedImage(coords.length, coords.length, BufferedImage.TYPE_INT_RGB)

  Timed("updating the bufferedimage") {
    something.foreach { case (x, y, value) => out.setRGB(x, y, getColour(value)) }
  }

  Timed("writing the image to file") {
    ImageIO.write(out, "png", new File("test.png"))
  }

  def getColour(value: Double) =
    math.floor(30 * math.abs(value)) match {
      case 0 => 0xffffff
      case _ => 0x000000
    }

  def normalise(grid: IndexedSeq[(Int, Int, Double)]): IndexedSeq[(Int, Int, Double)] = {
    val values      = grid.map { case (_, _, value) => value }
    val absoluteMax = math.max(math.abs(values.max), math.abs(values.min))
    grid.map { case (x, y, value) => (x, y, value / absoluteMax) }
  }

}
