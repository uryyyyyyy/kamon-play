/* =========================================================================================
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

val play23Version     = "2.3.10"
val play24Version     = "2.4.8"
val play25Version     = "2.5.4"
val play26Version     = "2.6.0"

val kamonCore         = "io.kamon"                  %%  "kamon-core"            % "0.6.7"
val kamonScala        = "io.kamon"                  %%  "kamon-scala"           % "0.6.7"

//play 2.3.x
val play23            = "com.typesafe.play"         %%  "play"                  % play23Version
val playWS23          = "com.typesafe.play"         %%  "play-ws"               % play23Version
val playTest23        = "org.scalatestplus"         %%  "play"                  % "1.2.0"

//play 2.4.x
val play24            = "com.typesafe.play"         %%  "play"                  % play24Version
val playWS24          = "com.typesafe.play"         %%  "play-ws"               % play24Version
val playTest24        = "org.scalatestplus"         %%  "play"                  % "1.4.0-M2"
val typesafeConfig    = "com.typesafe"              %   "config"                % "1.2.1"

//play 2.5.x
val play25            = "com.typesafe.play"         %%  "play"                  % play25Version
val playWS25          = "com.typesafe.play"         %%  "play-ws"               % play25Version
val playTest25        = "org.scalatestplus.play"    %%  "scalatestplus-play"    % "1.5.0"

//play 2.6.x
val play26            = "com.typesafe.play"         %%  "play"                  % play26Version
val playWS26          = "com.typesafe.play"         %%  "play-ws"               % play26Version
val playLogBack26     = "com.typesafe.play"         %%  "play-logback"          % play26Version
val playTest26        = "org.scalatestplus.play"    %%  "scalatestplus-play"    % "3.0.0"

val resolutionRepos = Seq("typesafe repo" at "http://repo.typesafe.com/typesafe/releases/")

lazy val kamonPlay = Project("kamon-play", file("."))
  .settings(noPublishing: _*)
  .aggregate(kamonPlay23, kamonPlay24, kamonPlay25, kamonPlay26)

lazy val kamonPlay23 = Project("kamon-play-23", file("kamon-play-2.3.x"))
  .settings(Seq(
      bintrayPackage := "kamon-play",
      moduleName := "kamon-play-2.3",
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.10.6", "2.11.8"),
      testGrouping in Test := singleTestPerJvm((definedTests in Test).value, (javaOptions in Test).value)))
  .settings(aspectJSettings: _*)
  .settings(resolvers ++= resolutionRepos)
  .settings(
    libraryDependencies ++=
      compileScope(play23, playWS23, kamonCore, kamonScala) ++
      providedScope(aspectJ) ++
      testScope(playTest23))

lazy val kamonPlay24 = Project("kamon-play-24", file("kamon-play-2.4.x"))
  .settings(Seq(
      bintrayPackage := "kamon-play",
      moduleName := "kamon-play-2.4",
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.10.6", "2.11.8"),
    testGrouping in Test := singleTestPerJvm((definedTests in Test).value, (javaOptions in Test).value)))
  .settings(aspectJSettings: _*)
  .settings(resolvers ++= resolutionRepos)
  .settings(
    libraryDependencies ++=
      compileScope(play24, playWS24, kamonCore, kamonScala) ++
      providedScope(aspectJ, typesafeConfig) ++
      testScope(playTest24))

lazy val kamonPlay25 = Project("kamon-play-25", file("kamon-play-2.5.x"))
  .settings(Seq(
      bintrayPackage := "kamon-play",
      moduleName := "kamon-play-2.5",
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.11.8"),
      testGrouping in Test := singleTestPerJvm((definedTests in Test).value, (javaOptions in Test).value)))
  .settings(aspectJSettings: _*)
  .settings(resolvers ++= resolutionRepos)
  .settings(
    libraryDependencies ++=
      compileScope(play25, playWS25, kamonCore, kamonScala) ++
      providedScope(aspectJ, typesafeConfig) ++
      testScope(playTest25))

lazy val kamonPlay26 = Project("kamon-play-26", file("kamon-play-2.6.x"))
  .settings(Seq(
    bintrayPackage := "kamon-play",
    publishTo := Some(Resolver.file("file",  new File( "/Users/ko.shibata/develop/uryyyyyyy/git/" )) ),
    moduleName := "kamon-play-2.6",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.11.8"),
    testGrouping in Test := singleTestPerJvm((definedTests in Test).value, (javaOptions in Test).value)))
  .settings(aspectJSettings: _*)
  .settings(resolvers ++= resolutionRepos)
  .settings(
    libraryDependencies ++=
      compileScope(play26, playWS26, kamonCore, kamonScala) ++
        providedScope(aspectJ, typesafeConfig) ++
        testScope(playTest26, playLogBack26))

import sbt.Tests._
def singleTestPerJvm(tests: Seq[TestDefinition], jvmSettings: Seq[String]): Seq[Group] =
  tests map { test =>
    Group(
      name = test.name,
      tests = Seq(test),
      runPolicy = SubProcess(ForkOptions(runJVMOptions = jvmSettings)))
  }

enableProperCrossScalaVersionTasks
