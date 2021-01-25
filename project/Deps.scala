import sbt.Keys.libraryDependencies
import sbt._

object Deps {
  val catsVersion           = "2.2.0"
  val circeVersion          = "0.12.3"
  val consoleForCatsVersion = "0.8.1"
  val refinedVersion        = "0.9.20"
  val betterFilesVersion    = "3.9.1"

  lazy val deps = {
    libraryDependencies ++= List(
      // Better files (because the java file stuff is literally beyond me)
      "com.github.pathikrit" %% "better-files"  % betterFilesVersion,
      // Cats
      "org.typelevel"        %% "cats-core"     % catsVersion,
      "org.typelevel"        %% "cats-effect"   % catsVersion,
      "dev.profunktor"       %% "console4cats"  % consoleForCatsVersion,
      // Circe
      "io.circe"             %% "circe-core"    % circeVersion,
      "io.circe"             %% "circe-literal" % circeVersion,
      "io.circe"             %% "circe-parser"  % circeVersion,
      "io.circe"             %% "circe-generic" % circeVersion,
      // Refined Types
      "eu.timepit"           %% "refined"       % refinedVersion
    )
  }
}
