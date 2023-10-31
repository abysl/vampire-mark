# Tile Map System

The Tile Map System is a robust framework that allows for the creation, modification, and interaction with the game's world represented by tiles. It embraces a data-oriented approach, focusing on representing the tile data while separating the rendering and interaction logic to other systems. This separation of concerns ensures a clean architecture, making the game easily extendable and maintainable.

## TileSets and Serialization

TileSets are collections of tiles, each having distinct properties and types. These TileSets can be serialized to and deserialized from a file, allowing for easy loading and saving of tile data. One of the fascinating features is the ability to hot reload TileSets. When a TileSet file is updated, the game can reload it on the fly, instantly reflecting the changes in the game world without needing a restart. This feature significantly speeds up the development and testing process.

```
val tileSet = TileSet(
"world",
listOf(
Tile(
name = "Poison",
aliases = listOf("acid", "acid_a1", "acid_a2"),
properties = listOf(TileProperty.Poison(stacks = 1))
)
)
)

// Serialization
val encoded = gameSettings.jsonSettings.encodeToString(tileSet)
Gdx.files.local("data/tilesets/world.tiles").writeString(encoded, false)

// Deserialization
val decodedTileSet: TileSet = gameSettings.jsonSettings.decodeFromString(Gdx.files.local("data/tilesets/world.tiles").readString())
```


## Tile Properties

Each tile in a TileSet can have various properties that dictate how it interacts with the game world. For instance, a Poison tile can have a property that applies a poison stack to entities standing on it. The properties system is flexible and extensible, allowing for complex interactions.

Here's an example of a Poison property and a Filter property that can be added to a tile:

```
// Defining Poison and Filter properties
data class Poison(val stacks: Int) : TileProperty
data class Filter(val all: List<String>, val none: List<String>) : TileProperty

val poisonTile = Tile(
name = "Poison",
aliases = listOf("acid", "acid_a1", "acid_a2"),
properties = listOf(
Poison(stacks = 1),
Filter(all = listOf("Player"), none = listOf("PoisonImmune"))
)
)
```

The `Filter` property here ensures that only entities with a "Player" component get affected, and those with a "PoisonImmune" component are ignored.

## Poison System Example

To illustrate how these tile properties interact with the game entities, let's look at an example PoisonSystem:

```
@All(PositionComponent::class)
class PoisonSystem(
    private val tileMap: TileMap
) : IteratingSystem() {

    override fun process(entityId: Int) {
        val positionComp = world.getEntity(entityId).getComponent(PositionComponent::class.java)
        val tileCoord = positionComp.position.toTileCoord()
        val tile = tileMap.getTile(tileCoord)

        tile.properties
            .filterIsInstance<TileProperty.Filter>()
            .forEach { filter ->
                val filterAspect = filter.toAspect(world)
                if (filterAspect.isInterested(world.getEntity(entityId))) {
                    applyPoisonStack(entityId)
                }
            }
    }

    private fun applyPoisonStack(entityId: Int) {
        val poisonComp = world.getEntity(entityId).edit().create(PoisonComponent::class.java)
        poisonComp.stacks += 1  // Assuming PoisonComponent has a 'stacks' property
    }
}
```

The function "toAspect" is simply an adapter that takes a TileProperty.Filter object and converts it to Artemis' version of component filters called an "Aspect".

> ### :sparkles: Kotlin Tip: Extension Functions :sparkles:
> Extension functions in Kotlin allow you to extend a class with new functionality without inheriting from the class. They are defined outside of the class, and can be called as if they were part of the class. This is a handy feature for adding functions to classes in a readable and organized manner, especially when you don't own the class or it's final (can't be inherited).
>
> Here's a basic example of an extension function:
> ```kotlin
> fun String.exclaim() = "$this!"
> 
> val greeting = "Hello"
> println(greeting.exclaim())  // Output: Hello!
> ```
>
> The `exclaim` function is an extension function on the `String` class, and can be called on any `String` object, just like a regular method.
>
> Learn more about extension functions [here](https://kotlinlang.org/docs/extensions.html).



## TileMap Interface

The `TileMap` interface is a crucial part of the Tile Map System. It abstracts the underlying structure of the tile map, allowing for different implementations suited to different needs.

### ChunkedTileMap

`ChunkedTileMap` is an implementation of `TileMap` that handles an infinite space by organizing the tiles into chunks. When a particular tile or chunk of tiles is accessed, `ChunkedTileMap` ensures they are loaded into memory, allowing for efficient usage of resources in an infinitely expanding world.

{code_block_4}

### FixedTileMap (Hypothetical)

Although not yet implemented, a `FixedTileMap` could be another implementation of `TileMap`. Unlike `ChunkedTileMap`, `FixedTileMap` would have defined bounds, and attempting to access tiles outside these bounds would result in an error. This implementation would be suited for games with a known, fixed-size world.

{code_block_5}

These different implementations of `TileMap` provide flexibility in handling the game world's spatial representation, allowing developers to choose the approach that best fits the game's requirements.
