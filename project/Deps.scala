import sbt.Keys.libraryDependencies
import sbt._

object Deps {
  val catsVersion           = "2.2.0"
  val circeVersion          = "0.12.3"
  val consoleForCatsVersion = "0.8.1"
  val doodleVersion         = "0.9.20"
  val refinedVersion        = "0.9.20"

  lazy val deps = {
    libraryDependencies ++= List(
      // Cats
      "org.typelevel"     %% "cats-core"     % catsVersion,
      "org.typelevel"     %% "cats-effect"   % catsVersion,
      "dev.profunktor"    %% "console4cats"  % consoleForCatsVersion,
      // Circe
      "io.circe"          %% "circe-core"    % circeVersion,
      "io.circe"          %% "circe-literal" % circeVersion,
      "io.circe"          %% "circe-parser"  % circeVersion,
      "io.circe"          %% "circe-generic" % circeVersion,
      // Doodle
      "org.creativescala" %% "doodle"        % doodleVersion,
      // Refined Types
      "eu.timepit"        %% "refined"       % refinedVersion
    )
  }
}
