package chladni.console

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO}
import cats.implicits._
import chladni.EigenFunctions
import chladni.console.OutputHelpers._
import chladni.draw.DrawToConsole

object EigenmodePatternIO {
  def program: IO[ExitCode] =
    for {
      _ <- showIntroduction
      _ <- (handleSingleFigure *> askIfTheyWantToDrawAgain).iterateUntil(_ == false)
      _ <- putStrLn("Thanks for playing, see you soon!")
    } yield ExitCode.Success

  private def showIntroduction: IO[Unit] =
    for {
      _ <- putStrLn("Hello! I understand you would like to see a lovely Chaldni plate drawing")
      _ <- putStrLn("Well you've come to the right place")
      _ <- putStrLn("These figures are characterised by two numbers: m and n")
      _ <- putStrLn("They're kind of like the periodicity in each direction")
    } yield ()

  private def handleSingleFigure: IO[Unit] =
    for {
      m               <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for m")
      n               <- InputHelpers.askForEigenModeInput("Please give me an integer between 0 and 10 for n")
      basisFunction    = EigenFunctions.w(m, n) _
      _               <- DrawToConsole.draw(basisFunction)
      _               <- putStrLn("""Would you like to save a (much) higher resolution PNG of this under "/output"?""")
      wouldLikeToSave <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
      _               <- if (wouldLikeToSave) saveToFile(basisFunction) else IO(())
    } yield ()
}
