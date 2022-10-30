# MEtcd

A Plugin which provides a simple interface to report metrics to etcd.




## How to use it
### The only method
```kotlin
 MEtcd.setGameStatus(GameStatus.WAITING)

// WAITING, LOADING, STARTING, RUNNING, ENDING, EXITED
```






[![](https://jitpack.io/v/Agenda-Minecraft/MEtcd.svg)](https://jitpack.io/#Agenda-Minecraft/MEtcd)

### Gradle kts
```kotlin
repositories {
    maven(url = "https://jitpack.io")
    maven(url = "https://repo.codemc.org/repository/maven-public/")
    
}

dependencies {
    implementation("com.github.Agenda-Minecraft:MEtcd:$version")
}
```

### Gradle groovy
```groovy
repositories {
    maven { url = 'https://jitpack.io' }
    maven { url = 'https://repo.codemc.org/repository/maven-public/' }
}

dependencies {
    implementation 'com.github.Agenda-Minecraft:MEtcd:$version'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <repository>
        <id>codemc-repo</id>
        <url>https://repo.codemc.org/repository/maven-public/</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.Agenda-Minecraft</groupId>
    <artifactId>MEtcd</artifactId>
    <version>$version</version>
</dependency>

```






