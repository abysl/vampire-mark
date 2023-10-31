# Unit System

In our game project, we frequently interact with different units of measurement to represent space. For instance, we may think in terms of tiles when designing the world, but then we need to convert to pixels when rendering the scene. To avoid confusion and bugs, it's crucial to have a clear, type-safe way to work with these different units. That's where our `NamedUnit` system comes into play.

## Units Defined

There are four primary units we work with:

1. **Pixel**: The fundamental unit in our game, representing the basic game unit for the base resolution. A pixel is not directly correlated to a screen pixel due to the pixel-perfect scaling algorithm employed in rendering. For instance, 1 game pixel might scale to a 2x2 or 3x3 square of screen pixels to fit a target resolution without distortion.
2. **Tile**: A larger unit representing a square area of the game world. Each tile consists of a certain number of pixels.
3. **Chunk**: Even larger than a tile, a chunk represents a square area containing multiple tiles.
4. **Layer**: Layers represent different physical tile map layers, differentiated by their Z-axis value. For example, an entity on z = 0 won't be affected by a tile effect on z = 1. This is different from "graphical" layers, where multiple transparent images are laid on top of each other on the same Z-axis.

## NamedUnit System

The `NamedUnit` system is a way of creating a type-safe environment for working with these units. Instead of using plain integers (`Int`) everywhere, which can lead to confusion and bugs (like passing a value in tiles to a function expecting a value in pixels), we define distinct classes for each unit type.

> ### :sparkles: Kotlin Tip: Sealed Classes :sparkles:
> Sealed classes in Kotlin are used to represent restricted class hierarchies, wherein a value can have one of the types from a limited set. They are declared using the `sealed` modifier. All subclasses of a sealed class are known at compile-time, which makes it easier to verify exhaustiveness during `when` expressions.
>
> Here's a basic example of a sealed class:
> ```kotlin
> sealed class Expr {
>     data class Const(val number: Double) : Expr()
>     data class Sum(val e1: Expr, val e2: Expr) : Expr()
>     object NotANumber : Expr()
> }
>
> fun eval(expr: Expr): Double = when(expr) {
>     is Expr.Const -> expr.number
>     is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
>     Expr.NotANumber -> Double.NaN
> }
> ```
>
> In this example, `Expr` is a sealed class with three subclasses: `Const`, `Sum`, and `NotANumber`. The `eval` function demonstrates how `when` expressions can be used with sealed classes to handle different cases in a type-safe manner without needing an `else` clause.
>
> Learn more about sealed classes [here](https://kotlinlang.org/docs/sealed-classes.html).

## Conversion Methods

A significant advantage of this system is that we can define methods to convert between these units. For instance, the `Tile` class has methods `toPixels()` and `toChunks()` to convert a tile value to pixel and chunk values, respectively. These methods use a predefined configuration (`SpatialConfig`) to ensure accurate conversions.

This way, whenever you're working with a value in tiles and need to convert it to pixels, you can use the `toPixels()` method on a `Tile` object, and the conversion will be handled for you. This helps to avoid bugs and makes the code more readable and self-explanatory.

## Why Not Just Use Int?

You might wonder why not just use `Int` values everywhere. The problem is, it's easy to mix up units when you do that. For instance, if you have a function `movePlayer(distance: Int)`, it's unclear what unit `distance` is supposed to be in. But with our `NamedUnit` system, you could define the function as `movePlayer(distance: Pixel)`, and it's clear to anyone reading the code what unit is expected.
