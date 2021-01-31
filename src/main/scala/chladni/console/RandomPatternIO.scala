package chladni.console

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO}
import cats.implicits._
import chladni.Superposition
import chladni.console.OutputHelpers._
import chladni.draw.DrawToConsole

object RandomPatternIO {

  def program: IO[ExitCode] =
    for {
      _ <- showIntroduction
      _ <- (handleSingleFigure *> askIfTheyWantToDrawAgain).iterateUntil(_ == false)
      _ <- putStrLn("Thanks for playing, see you soon!")
    } yield ExitCode.Success

  private def showIntroduction: IO[Unit] =
    for {
      _ <- putStrLn("Hello! I understand you would like to generate a random superposition of Chaldni patterns")
      _ <- putStrLn("Well you've come to the right place")
      _ <- putStrLn("All you'll need to do is supply a seed")
    } yield ()

  private def handleSingleFigure: IO[Unit] =
    InputHelpers
      .askForSeedInput("Please give me a long for the seed for this random pattern")
      .map(Superposition.generateSuperpositionFunction)
      .flatMap(_.fold(putStrLn, drawFigure))

  private def drawFigure(f: (Double, Double) => Double): IO[Unit] =
    for {
      _               <- DrawToConsole.draw(f)
      _               <- putStrLn("""Would you like to save a (much) higher resolution PNG of this under "/output"?""")
      wouldLikeToSave <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
      _               <- if (wouldLikeToSave) saveToFile(f) else IO(())
    } yield ()

}
