import sbt.Keys.libraryDependencies
import sbt._

object Deps {
  val circeVersion   = "0.12.3"
  val catsVersion    = "2.2.0"
  val refinedVersion = "0.9.20"

  lazy val deps = {
    libraryDependencies ++= List(
      // Cats
      "org.typelevel"  %% "cats-core"     % catsVersion,
      "org.typelevel"  %% "cats-effect"   % catsVersion,
      "dev.profunktor" %% "console4cats"  % "0.8.1",
      // Circe
      "io.circe"       %% "circe-core"    % circeVersion,
      "io.circe"       %% "circe-literal" % circeVersion,
      "io.circe"       %% "circe-parser"  % circeVersion,
      "io.circe"       %% "circe-generic" % circeVersion,
      // Refined Types
      "eu.timepit"     %% "refined"       % refinedVersion
    )
  }
}
