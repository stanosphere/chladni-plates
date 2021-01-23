package chladni

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = program *> IO(ExitCode.Success)

  val program: IO[Unit] =
    for {
      _ <- putStrLn("Hello! I understand you would like to see a lovely Chaldni plate drawing")
      _ <- putStrLn("Well you've come to the right place")
      m <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for m")
      n <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for n")
      _ <- DrawToConsole.draw(m, n)
    } yield ()
}
