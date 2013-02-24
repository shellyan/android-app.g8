import sbt._

import Keys._
import AndroidKeys._
import AndroidNdkKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "$name$",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "$scala_version$",
    platformName in Android := "android-$api_level$"
  )

  lazy val proguardSettings = inConfig(Android) (Seq (
    useProguard := $useProguard$,
    proguardOptimizations += "-keep class $package$.** { *; }"
  ))

  lazy val ndkSettings = inConfig(Android) (Seq(
    jniClasses := Seq(),
    javahOutputFile := Some(new File("native.h"))
  ))

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    AndroidNdk.settings ++
    TypedResources.settings ++
    proguardSettings ++
    ndkSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      libraryDependencies += "org.scalatest" %% "scalatest" % "$scalatest_version$" % "test"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "main",
    file("."),
    settings = General.fullAndroidSettings ++ AndroidEclipse.settings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidEclipse.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "$name$Tests"
    )
  ) dependsOn main
}
