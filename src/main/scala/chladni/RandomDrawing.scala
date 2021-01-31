package chladni

import chladni.utils.GenEnrichment._
import org.scalacheck._
import EigenFunctions._

object RandomDrawing extends App {

  // TODO would be cool to see what function actually gets generated in maths notation
  // i.e. u1(x) * u5(y) + 0.782 * u4(x) * u7(y) + ...
  type ChladniFunction = (Double, Double) => Double

  // I've checked on paper that the biharmonic operator is linear so this is a perfectly legit move
  // i.e. L(f + g) == Lf + Lg and L(cf) == cLf, where L is a linear operator, f and g are functions, and c is a constant
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
