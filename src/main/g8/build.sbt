// Include the Android plugin
androidDefaults

// Specific settings for Eclipse projects
inConfig(Compile)(AndroidEclipseDefaults.settings)

// Name of your app
name := "$name$"

// Version of your app
version := "0.1"

// Version number of your app
versionCode := 0

// Version of Scala
scalaVersion := "$scala_version$"

// Version of the Android platform SDK
platformName := "android-$api_level$"
