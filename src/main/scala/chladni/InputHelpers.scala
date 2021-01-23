package chladni

import cats.effect.Console.io._
import cats.effect.IO
import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.And
import eu.timepit.refined.numeric._

object InputHelpers {
  def askForEigenModeInput: String => IO[Int] = {

    def go(message: String): IO[Int] =
      for {
        _           <- putStrLn(message)
        input       <- readLn
        messageOrRes = input.toIntOption
                         .toRight(s""""$input" is not an inger""")
                         .flatMap(refineV[Positive And Less[10]](_))
                         .map(_.toInt)
        res         <- messageOrRes.fold(go, IO(_))
      } yield res

    go
  }
}
