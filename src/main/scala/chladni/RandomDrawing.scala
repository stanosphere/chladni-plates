package chladni

import chladni.utils.GenEnrichment._
import org.scalacheck._

object RandomDrawing extends App {

  val myGen = for {
    a <- Gen.choose[Int](-12, 30)
    b <- Gen.choose[Int](678, 4230)
    c <- Gen.choose[Int](90, 100)
  } yield a + b * c

  println { myGen.sampleWithSeed(10) }
  println { myGen.sampleWithSeed(10) }
  println { myGen.sampleWithSeed(10) }

}
