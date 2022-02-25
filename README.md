# Conway's Game of Life

An implementation of Conway's Game of Life using Java Swing. A description of the game can be found here:

https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

In the Wikipedia article, the game is described as being on an "infinite" grid. This version uses a fixed sized grid that the user can control the size of. It is assumed that cells outside the boundary of the grid are always dead.

Features:

* Ability to change the size of the field from 10x10 up to 500x500.
* Ability to manually set / clear any cell in order to set up patterns
* Ability to clear the entire field.
* Ability to fill the field randomly.
* Ability to advance the game by pressing a button.
* Ability to set the "survive" and "birth" thresholds to custom values. The default threshold values in the classic game brings a dead cell to life if the number of neighboring live cells is greater than or equal to 2 (low birth threshold) and less than or equal to 3 (high birth threshold) and otherwise stays dead. A living cell survives if the number of neighboring live cells is greater than or equal to 3 (low survive threshold) and less than or equal to 3 (high survive threshold) and otherwise will de. 
* Ability to toggle "torus" mode on or off. In torus mode, the field is treated as if it wraps around the edges back to the other edge.
* A start/stop button that advances the game automatically using a separate thread with a delay between updates settable between 10 milliseconds and 1 second.

## How to Play

Run the main method in the GameOfLife class located at src/a8/GameOfLife.java

## Demo



https://user-images.githubusercontent.com/52138031/155639817-92299ddb-1516-4810-bce6-357f1f31fcd7.mp4



## Author

Ameer Qaqish
