# scalaswatch

An SBT module written in Scala that maps a range of numeric values to an array of color values and facilitates sampling from those colors. This is an attempt at further performance optimization of the [gradientswatch module](http://github.com/quantumjockey/gradientswatch) for Java applications. Though the exercise of creating another implementation of the module in Scala was an interesting one, the performance of the Java module surpasses that of the SBT project module by orders of magnitude. Additional effort aimed at closing and eventually eliminating this gap will be exercised as time permits.

# Requirements

In order to begin development with this repository, the JDK, the latest release for Java 8, and the latest version of Scala should be installed on your machine.

If the JDK and/or Java 8 is/are not installed on your machine:

1.  Go to the [Java SE Development Kit 8 page](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) on Oracle's website
2.  Read/accept the [Oracle binary Code License Agreement for Java SE](http://www.oracle.com/technetwork/java/javase/terms/license/index.html)
3.  Select the version that best suits your OS/development environment and click on the file link to download it
4.  Open the installer you downloaded and follow all instructions/prompts until Java 8 and the JDK are installed on your machine

Your development environment is now ready for Java.

If Scala isn't installed on your machine:

1.  If you're working in Windows, go to the [Scala Language website](http://www.scala-lang.org/) and retrieve the latest binaries from the [downloads page](http://www.scala-lang.org/download/). If you're working with OSX, you can either use latest package installer (available on the same page) or download the appropriate JARs using a package manager like [Homebrew](http://brew.sh/).
2.  Next, follow the instructions provided in either installer.

That's all there is to it. Your development environment is now ready for Scala.

# Instructions

Next, clone this repository to your machine. Also, be sure to update build.sbt and build.properties to reflect the latest versions of Scala, the included SBT, and ScalaTest.

If you're developing the old-school way using your favorite text editor and the Terminal (or Command Prompt for you Windows Junkies), that's all you need!

If you're planning to wrap this module in a project with your favorite IDE, please see instructions below for some tips on how to get started.

## When using IntelliJ

If IntelliJ IDEA is not installed, download and install the latest version from the [JetBrains website](https://www.jetbrains.com/idea/).

Next, create the project files:

1.  Open up IntelliJ IDEA and select "Import Project" from the startup menu
2.  Navigate to the directory your repository was cloned to
3.  Open the directory and select it from within the file dialog - the name of the repository should now be listed as the project name
4.  Unless you have special reasons for making changes, just leave all default options checked/unchecked and click through the prompts.

After a a few seconds (closer o a minute on older machines), a complete directory structure with sources and test roots should be marked. If for some reason this has not happened, you must now set up the sources and test roots.

To set up the sources roots:

1.  Open the Project tool window using either Command + 1 or the "View" menu
2.  With secondary click enabled, right-click on the "src/main/scala" folder to pull up the project item context menu
3.  Navigate to the bottom of the menu and hover over the "Mark Directory As" sub-menu
4.  Click on "Sources Root"
5.  Repeat steps 2-4 for "src/main/java" and all scala source directories associated with version-specific functionality.

The directory's folder icons should turn blue in the Project tool window, indicating that the content of the directories therein will be recognized as module sources by the Scala compiler.

To set up the tests roots:

1.  With the Project tool window still open, right-click on the "src/test/scala" folder to pull up the project item context menu
2.  Navigate to the bottom of the menu and hover over the "Mark Directory As" sub-menu
3.  Click on "Test Sources Root"
4.  Repeat steps 2-3 for "src/test/java" and all scala test directories associated with version-specific functionality.

The directories' folder icons should turn green in the Project tool window, indicating that they are now the root directories for storing project unit tests.

Next, you need to confirm that the Project Structure is properly configured.

1.  Go to "File" -> "Project Structure..." (or hit Command + ;)
2.  Go to "Project" under "Project Settings"
3.  Confirm that the Project SDK it at least version 1.8 - if this is not the case, locate the directory where SDK 1.8 is located and select it
4.  Confirm that the Project Language Level for Java is set to 8.0 - if this is not the case, click on the ChoiceBox and select PLL 8.0
5.  Save these settings by clicking "Apply"

Your project structure is ready to go!

Happy development!
