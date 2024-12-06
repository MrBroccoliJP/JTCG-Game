Congratulations, you have a really nice MVP!
Actually, I think it's one or two steps beyond a MVP, since you were able to add features like score and different types of `Orb` objects, with different behaviours and points.

Overall, it's clear that you were concerned with dealing with the complexity of the program. You created a package for each "family" of entities, most of your classes are well defined in terms of what entities they represent and you are on top of your encapsulation game (well defined methods with good names).

Having said this, I think the class `Game` is a bit "heavy" in terms of responsabilities.
Although each phase of the game (start menu, game and end game) is neatly encapsulated in several methods, allowing for chainning the invocation of each when necessary, this class is doing a bit much.

Following the same reasoning that probably led you to create the `ScoreSystem` class, you can improve your game by extracting to a different class the logic pertaing to displaying the scores (for instance). That is, you can encapsulate in specialized classes the game logic (collisions and movements) and the graphics logic (displaying results).
You might encounter one of the challenges of this assignment, that is, passing references between objects. But you might gain in the long term, because it will help with the maintainability of your code (when you want to add more features).
On the other hand, you created two different classes to handle the keyboard, depending on the phase of the game, which some might say is an overkill! :)

To improve your game, you might want to try and apply your recent knowledge of Java I/O, to save the game scores. Since a lot of orbs and snakeBlocks are created, you can try to apply the Factory DP. Also, looking at `orbCheck` method, that logic might be a good candidate to try and apply the Strategy DP.

At last, let's deal with the exception being thrown in the `main` method in a different way, and avoid non used properties and packge level properties (if they don't make sense).

Good job!!!!
