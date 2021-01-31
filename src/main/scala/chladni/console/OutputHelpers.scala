package chladni.console

import cats.effect.Console.io._
import cats.effect.IO
import chladni.draw.DrawToPNG

object OutputHelpers {
   def askIfTheyWantToDrawAgain: IO[Boolean] =
    for {
      _               <- putStrLn("I hope you enjoy the above image")
      _               <- putStrLn("Would you like to draw another?")
      willDrawAnother <- InputHelpers.askYesOrNoQuestion("""(Type "y" for yes or "n" for no)""")
    } yield willDrawAnother

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
}
