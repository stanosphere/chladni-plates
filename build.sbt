import sbt.Keys._

lazy val `chladni-plates` = (project in file("."))
  .settings(
    name := "chladni-plates",
    version := "0.1",
    scalaVersion := "2.13.4",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-language:higherKinds",
      "-language:postfixOps",
      "-feature",
      "-Xfatal-warnings"
    ),
    Deps.deps,
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.11.3" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )
