package chladni

import cats.effect.{ExitCode, IO, IOApp}
import chladni.console.{EigenmodeIO, InputHelpers}
import chladni.model.RunMode

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    InputHelpers.askWhichRunModeTheyWant.flatMap {
      case RunMode.EigenmodeDrawing => EigenmodeIO.program
      case RunMode.RandomDrawing    => EigenmodeIO.program
    }
}
