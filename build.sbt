name := "docp"
 
version := "1.0"
 
scalaVersion := "2.11.0"

scalacOptions += "-feature"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.7" % "test"

scalariformSettings

mainClass in Compile := Some("com.udacity.docp.problem1.Poker")
