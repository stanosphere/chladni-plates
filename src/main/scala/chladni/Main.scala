package chladni

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- showIntroduction
      _ <- (drawSingleFigure *> askIfTheyWantToDrawAgain).iterateUntil(_ == false)
      _ <- putStrLn("Thanks for playing, see you soon!")
    } yield ExitCode.Success

  private def showIntroduction: IO[Unit] =
    for {
      _ <- putStrLn("Hello! I understand you would like to see a lovely Chaldni plate drawing")
      _ <- putStrLn("Well you've come to the right place")
      _ <- putStrLn("These figures are characterised by two numbers: m and n")
      _ <- putStrLn("They're kind of like the periodicity in each direction")
    } yield ()

  private def drawSingleFigure: IO[Unit] =
    for {
      m <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for m")
      n <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for n")
      _ <- DrawToConsole.draw(m, n)
    } yield ()

  private def askIfTheyWantToDrawAgain: IO[Boolean] =
    for {
      _               <- putStrLn("I hope you enjoy the above image")
      _               <- putStrLn("Would you like to draw another?")
      willDrawAnother <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
    } yield willDrawAnother
}
