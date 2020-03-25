name := "cinemateamone"
 
version := "1.0" 
      
lazy val `cinemateamone` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.3-play28",
  "org.slf4j" % "slf4j-api" % "1.7.25"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
libraryDependencies ++= Seq(
  javaWs
)


libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.3-play28",
  "org.slf4j" % "slf4j-api" % "1.7.25"
)
