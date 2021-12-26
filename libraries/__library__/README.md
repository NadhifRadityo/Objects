# Library Processing
Utility to make custom tasks, in modular environment, and very easy to use.

## Adding The Library Modules to Build
Use the function `module` from `libraryModules.gradle`, and specify the directory name which the manifest file will be in.
There are no restrictions on the naming, as long as the OS allow that directory name.
Example:
```groovy
context(this) {
  scriptImport listOf('module'), from('libraryModules.gradle'), being('libraryModules')
  libraryModules.module('GameLibrary - LWJGL')
  ...
}
```

## Library Module Structure
Each of the library module needs to have manifest file and the entry main class.

### Manifest File
```manifest
# Fully qualified name of the main class
Module-Entry: LWJGL.Main
# Prefix if something needs to be unique (task names)
Module-Unique: LWJGL
# Description of the library module
Module-Description: LWJGL libraries module

# You can also give any custom properties here
name: PropertiesNameHere
testProperty: Hello There! You can import me in the script later!
```

### Entry Class
Entry class needs to extends `LibraryModule` and implements its `run` method.
```kotlin
package LWJGL

import io.github.NadhifRadityo.Library.Utils.LoggerUtils.*

class Main: LibraryModule() {
  override fun run() {
    llog("This will run on every configuration")
    // The task name will be resolved to `library.LWJGL.customTaskName`
    // Function `unique` is equal to `library.${manifest["Module-Unique"]}.${name}` 
    task(unique("customTaskName")).apply {
      doLast {
        llog("This will run only on task")
        _llog("And this is my custom property: %s", property("PropertiesNameHere.testProperty"))
      }
    }
  }
}
```

## Running Library Modules
1. Compile and load the library to daemon using `gradlew libraryLoad`
2. If you work on IDE, you probably want to reload gradle project to update the tasks list from the loaded script.
3. Run the script as usual.

## Multi Build Projects
This will also work on multi build projects. The script will load corresponding configurations each build,
and unload it if it's not required.

## Features
- [x] Maven
  - [x] Fetch configurations from repository
  - [ ] Fetch actual contents
    - The repository just updated the layout, so currently it can't fetch anything.
  - [ ] Verify the downloaded contents
    - [ ] Hash SHAN, MDN
    - [ ] PGP
- [ ] Sonatype
  - [ ] Fetch configurations from repository
  - [ ] Fetch actual contents
  - [ ] Verify the downloaded contents
- [ ] Java Script Generator
