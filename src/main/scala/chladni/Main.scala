package chladni

import cats.effect.{ExitCode, IO, IOApp}
import chladni.console.{EigenmodePatternIO, InputHelpers, RandomPatternIO}
import chladni.model.RunMode

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    InputHelpers.askWhichRunModeTheyWant.flatMap {
      case RunMode.EigenmodeDrawing => EigenmodePatternIO.program
      case RunMode.RandomDrawing    => RandomPatternIO.program
    }
}
