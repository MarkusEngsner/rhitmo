name := "rhitmo"

version := "0.1"

scalaVersion := "2.13.3"

enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := true
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0"
