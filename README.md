# P.A.U.C.A.L.

> Petra's Assorted, Useful Collection of Assistance Library

Library mod for my mods, and a centralized place for tracking contributors and friends.

Also adds a little bit of content: shift-rightclicking a player with an empty hand will give them headpats!

[Curseforge Link](https://www.curseforge.com/minecraft/mc-mods/paucal)

## API

This mod is hosted on Cursemaven, cause I can't be bothered to figure out how to properly set up Maven :)

[As per the instructions:](https://www.cursemaven.com/)

```groovy
repositories {
    // ... other repos ...
    maven {
        url "https://cursemaven.com"
        content {
            includeGroup "curse.maven"
        }
    }
}

dependencies {
    // ... other dependencies ...
    compileOnly fg.deobf("curse.maven:paucal-597824:<MOST RECENT FILE ID>")
    runtimeOnly fg.deobf("curse.maven:paucal-597824:<MOST RECENT FILE ID>")
}
```
