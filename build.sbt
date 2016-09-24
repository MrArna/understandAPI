name := "cs474-HW2"

version := "1.0"

crossPaths := false

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")


libraryDependencies += "org.jgrapht" % "jgrapht-core" % "1.0.0"