package utils

 object PlatformX {
     val isWindows = System.getProperty("os.name").startsWith("Windows")
 }