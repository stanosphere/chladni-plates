package chladni

import chladni.utils.GenEnrichment._
import org.scalacheck._
import EigenFunctions._

object RandomDrawing extends App {

  val genEigenFunction: Gen[(Double, Double) => Double] =
    for {
      m     <- Gen.choose[Int](1, 10)
      n     <- Gen.choose[Int](1, 10)
      coeff <- Gen.choose[Double](-1, 1)
    } yield (x: Double, y: Double) => coeff * u(m)(x) * u(n)(y)

  val genLinearCombination: Gen[(Double, Double) => Double] =
    for {
      length <- Gen.choose[Int](2, 10)
      fs     <- Gen.listOfN(length, genEigenFunction)
    } yield (x: Double, y: Double) => fs.foldLeft(0d)(_ + _(x, y))

  val seed = 65437

  println { genLinearCombination.sampleWithSeed(seed).get(0.3, 0.4) }
  println { genLinearCombination.sampleWithSeed(seed).get(0.3, 0.4) }
  println { genLinearCombination.sampleWithSeed(seed).get(0.3, 0.4) }

}
