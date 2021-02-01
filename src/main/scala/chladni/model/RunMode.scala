package chladni.model

sealed trait RunMode

object RunMode {
  case object EigenmodeDrawing extends RunMode
  case object RandomDrawing    extends RunMode
}
