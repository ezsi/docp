name := "docp"
 
version := "1.0"
 
scalaVersion := "2.11.4"

scalacOptions += "-feature"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.7" % "test"

scalariformSettings

mainClass in Compile := Some("com.udacity.docp.problem1.Poker")
