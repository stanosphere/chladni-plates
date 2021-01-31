package chladni

import cats.effect.Console.io._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
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
      _               <- DrawToConsole.draw(m, n)
      _               <- putStrLn("""Would you like to save a (much) higher resolution PNG of this under "/output"?""")
      wouldLikeToSave <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
      _               <- if (wouldLikeToSave) saveToFile(m, n) else IO(())
    } yield ()

  private def saveToFile(m: Int, n: Int): IO[Unit] =
    for {
      _          <- putStrLn("""please specify where under "output" you would like to save your picture as a png""")
      _          <- putStrLn(
             """e.g. if you type "lovely-picture" your file will be under ../chladni-plates/output/lovely-picture.png"""
           )
      name       <- readLn
      _          <- putStrLn("Great! This might take a few seconds...")
      _          <- putStrLn("Like this code is not optimal at all lol")
      placeSaved <- DrawToPNG.drawBasisFn(m, n)(name)
      _          <- putStrLn(s"Saved under $placeSaved")

    } yield ()

  private def askIfTheyWantToDrawAgain: IO[Boolean] =
    for {
      _               <- putStrLn("I hope you enjoy the above image")
      _               <- putStrLn("Would you like to draw another?")
      willDrawAnother <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
    } yield willDrawAnother

}
