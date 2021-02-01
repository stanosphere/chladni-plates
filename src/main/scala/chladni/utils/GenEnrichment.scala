package chladni.utils

import org.scalacheck.Gen
import org.scalacheck.Gen.Parameters
import org.scalacheck.rng.Seed

object GenEnrichment {
  implicit class GenSyntax[A](g: Gen[A]) {
    def sampleWithSeed(seed: Long): Option[A] =
      g.apply(Parameters.default, Seed(seed))
  }
}
