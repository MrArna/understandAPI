lazy val commonSettings = Seq(
  organization := "edu.uic",
  version := "final"
)

mainClass in (Compile, run) := Some("Main")

crossPaths := false

autoScalaLibrary := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies += "org.jgrapht" % "jgrapht-core" % "1.0.0"
libraryDependencies += "org.jgrapht" % "jgrapht-dist" % "1.0.0"
libraryDependencies += "jgraph" % "jgraph" % "5.13.0.0"
libraryDependencies += "commons-cli" % "commons-cli" % "1.3.1"
libraryDependencies += "junit" % "junit" % "4.12"


lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "understandAnalizer-Arnaboldi"
  ).
  settings(
    mainClass in assembly := Some("Main")
  ).
  enablePlugins(AssemblyPlugin)

resolvers in Global ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server"          at "http://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".txt" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}