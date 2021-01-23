package chladni

import cats.effect.Console.io._
import cats.effect.IO
import eu.timepit.refined._
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.{And, Or}
import eu.timepit.refined.generic.Equal
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

  def askYesOrNoQuestion: String => IO[Boolean] = {

    def processInput(input: String): Either[String, Boolean] =
      (refineV[Equal["y"]](input), refineV[Equal["n"]](input)) match {
        case (Right(_), _) => Right(true)
        case (_, Right(_)) => Right(false)
        case _             => Left("""Please type "y" for yes or "n' for no""")
      }

    def go(message: String): IO[Boolean] =
      for {
        _     <- putStrLn(message)
        input <- readLn
        res   <- processInput(input).fold(go, IO(_))
      } yield res

    go
  }
}
