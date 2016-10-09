mainClass in (Compile, run) := Some("Main")


resourceDirectory in assembly := baseDirectory.value

parallelExecution in assembly := false
parallelExecution in Test := false
crossPaths := false
autoScalaLibrary := false
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies += "org.jgrapht" % "jgrapht-core" % "1.0.0"
libraryDependencies += "org.jgrapht" % "jgrapht-dist" % "1.0.0"
libraryDependencies += "jgraph" % "jgraph" % "5.13.0.0"
libraryDependencies += "commons-cli" % "commons-cli" % "1.3.1"
libraryDependencies += "junit" % "junit" % "4.12"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
