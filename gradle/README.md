# Gradle Script Utils

## Features

### Groovy DSL
build.gradle
```groovy
/**
 * Need to type full path because dynamic import
 * is not available yet, after this any import can be omitted
 */
Gradle.Common.context(this) {
    Gradle.Common.construct()
}

context(this) {
    scriptImport listOf("printHello", "getWorld"), from('functions.gradle')
    printHello(getWorld("Beautiful"))
}
```

functions.gradle
```groovy
context(this) {
    /**
     * Use flag `expose_exports` because by default all exports 
     * are not being imported to global.
     * You can still access to imported objects with,
     * `
     * def logger = scriptImport from('/std$LoggerUtils')
     * logger.llog("Hey!")
     * `
     * Or use the keyword `being` to import to a global variable instead
     * `
     * scriptImport from('/std$LoggerUtils'), being('logger')
     * logger.llog("Hey!")
     * `
     * Or specify the export list manually to import those as global variables
     * `
     * scriptImport listOf("llog"), from('/std$LoggerUtils')
     * llog("Hey!")
     * `
     */
    scriptImport from('/std$LoggerUtils'), with(includeFlags('expose_exports'))
}

def printHello = { world ->
    linfo("Hello $world!")
}
def getWorld = { modifier ->
    return "$modifier World"
}
def __printOutConstructing = {
    ldebug("functions.gradle constructing")
}
def __printOutDestructing = {
    ldebug("functions.gradle deconstructing")
}

context(this) {
    scriptApply(this)
    scriptConstruct {
        __printOutConstructing()
    }
    scriptDestruct {
        __printOutDestructing()
    }
}

scriptExport printHello, being("printHello")
scriptExport getWorld, being("getWorld")
```

Console Result:
```
[DEBUG] functions.gradle constructing
[INFO] Hello Beautiful World!
[DEBUG] functions.gradle deconstructing
```

### Kotlin DSL
Still under development

build.gradle.kts
```kotlin
import Gradle.Common.context
import Gradle.Common.construct
import Gradle.Strategies.KeywordsUtils.from
import Gradle.DynamicScripting.Scripting.scriptImport
import Gradle.GroovyKotlinInteroperability.expandImport2
import Gradle.GroovyKotlinInteroperability.cls

context(this) {
    /**
     * On main module, this function must be called
     */
    construct()
}

context(this) {
    /**
     * `expandImport'N'<ReturnType0, ..., ReturnTypeN>(scriptImport): (ReturnType0, ..., ReturnTypeN)`
     * It allows to destructure the imported script, but since kotlin is static language
     * explicit return types are still required. `cls` is typealias for `Closure`
     */
    val (printHello, getWorld) = expandImport2<cls<Unit>, cls<String>>(
        scriptImport(listOf("printHello", "getWorld"), from("functions.gradle.kts")))
    printHello(getWorld("Beautiful"))
}
```

functions.gradle.kts
```kotlin
import Gradle.Common.context
import Gradle.Strategies.KeywordsUtils.from
import Gradle.DynamicScripting.Scripting.scriptImport
import Gradle.DynamicScripting.Scripting.scriptApply
import Gradle.DynamicScripting.Scripting.scriptConstruct
import Gradle.DynamicScripting.Scripting.scriptDestruct
import Gradle.DynamicScripting.Scripting.scriptExport
import Gradle.GroovyKotlinInteroperability.expandImport1
import Gradle.GroovyKotlinInteroperability.cls
import Gradle.Strategies.LoggerUtils.ldebug

/**
 * All access to classes are also compatible with scriptImport.
 * This will be useful in groovy DSL
 */
// std$LoggerUtils is a collection standard functions
val (linfo) = context(this) { expandImport1<cls<Unit>>(
    scriptImport(listOf("linfo"), from("/std\$LoggerUtils"))) }

val printHello = { world: String ->
    linfo("Hello $world!")
}
val getWorld = { modifier: String ->
    return "$modifier World"
}
val __printOutConstructing = {
    ldebug("functions.gradle constructing")
}
val __printOutDestructing = {
    ldebug("functions.gradle deconstructing")
}

context(this) {
    scriptApply(this)
    scriptConstruct {
        __printOutConstructing()
    }
    scriptDestruct {
        __printOutDestructing()
    }
}

scriptExport(printHello, being("printHello"))
scriptExport(getWorld, being("getWorld"))
```

Console Result:
```
[DEBUG] functions.gradle constructing
[INFO] Hello Beautiful World!
[DEBUG] functions.gradle deconstructing
```

## Known Limitations
1. Mixing up groovy DSL with kotlin DSL. Somehow kotlin loads different class from groovy. (Hopefully this gets patched by kotlin DSL team, or maybe research the workaround)
2. Threading is not and will never be intended feature. It will mess up the context stacks both in gradle and this project.

## Bugs
- [ ] Destructor call is currently unstable -- especially when the script is failing, because it relies on `afterTask`, but not all tasks will be guaranteed to be called from that event.
      Fallback that will call destructor exactly before the constructor is possible, but not preferable because it will create memory leaks
      in Gradle daemon. Predicting on which script will be executed based on the graph is currently the most reliable way. But in the mean time, I will be researching more about the event handlers.
- [ ] Destructor call is not being called, when the script is completely failing on configuration stage.
