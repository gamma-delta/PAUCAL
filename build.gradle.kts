// Forward all shade questions to:
// - https://gist.github.com/SizableShrimp/949e7c219bfc94487a45226b64ac7749
// - https://gist.github.com/SizableShrimp/66b22f1b24c255e1491c8d98d3f11f83

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    "java"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    id("at.petra-k.PKPlugin") version "0.1.0-pre-37"
    id("at.petra-k.PKSubprojPlugin") version "0.1.0-pre-37" apply false

    // This needs to be in the root
    // https://github.com/FabricMC/fabric-loom/issues/612#issuecomment-1198444120
    // Also it looks like property lookups don"t work this early
    id("fabric-loom") version "1.0-SNAPSHOT" apply false
}

pkpcpbp {
    modInfo {
        val modID: String by project
        val minecraftVersion: String by project
        val modVersion: String by project
        modID(modID)
        mcVersion(minecraftVersion)
        modVersion(modVersion)
    }
}
