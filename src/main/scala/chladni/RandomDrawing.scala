package chladni

import chladni.utils.GenEnrichment._
import org.scalacheck._
import EigenFunctions._

object RandomDrawing extends App {

  // TODO would be cool to see what function actually gets generated in maths notation

  type ChladniFunction = (Double, Double) => Double

  def generateSuperpositionFunction(seed: Long): Either[String, ChladniFunction] =
    genLinearCombination.sampleWithSeed(seed).toRight("could not generate function :(")

  private def genLinearCombination: Gen[ChladniFunction] =
    Gen.listOfN(10, genEigenFunction).map(fs => (x, y) => fs.foldLeft(0d)(_ + _(x, y)))

  private def genEigenFunction: Gen[ChladniFunction] =
    for {
      m     <- Gen.choose[Int](1, 10)
      n     <- Gen.choose[Int](1, 10)
      coeff <- Gen.choose[Double](-1, 1)
    } yield (x: Double, y: Double) => coeff * u(m)(x) * u(n)(y)

//  DrawToPNG.draw(f)("paul-test").unsafeRunSync()

}
