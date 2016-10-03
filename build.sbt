name := "cs474-HW2"

version := "1.0"

crossPaths := false

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")


libraryDependencies += "org.jgrapht" % "jgrapht-core" % "1.0.0"
libraryDependencies += "org.jgrapht" % "jgrapht-dist" % "1.0.0"
libraryDependencies += "jgraph" % "jgraph" % "5.13.0.0"
libraryDependencies += "commons-cli" % "commons-cli" % "1.3.1"
