name := "MinimalApplication"

version := "0.3.0"

scalaVersion := "2.13.3"

// Run dependencies
libraryDependencies += "com.lihaoyi" % "cask_2.13" % "0.6.2"
libraryDependencies += "com.lihaoyi" % "scalatags_2.13" % "0.9.2"

// Test dependencies
libraryDependencies += "com.lihaoyi" % "utest_2.13" % "0.7.5" % "test"
libraryDependencies += "com.lihaoyi" % "requests_2.13" % "0.6.5" % "test"
