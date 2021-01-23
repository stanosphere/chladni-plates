package chladni

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = program *> IO(ExitCode.Success)

  def program: IO[Unit] =
    for {
      _ <- showIntroduction
      m <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for m")
      n <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for n")
      _ <- DrawToConsole.draw(m, n)
    } yield ()

  def showIntroduction: IO[Unit] =
    for {
      _ <- putStrLn("Hello! I understand you would like to see a lovely Chaldni plate drawing")
      _ <- putStrLn("Well you've come to the right place")
      _ <- putStrLn("These figures are characterised by two numbers: m and n")
      _ <- putStrLn("They're kind of like the periodicity in each direction")
    } yield ()
}
