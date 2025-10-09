# Project Report

## Design Decisions

### Architecture
1. How did you separate model, view, and controller? 
   - Model, View, and Controller are each separate packages in the structure 
of this project. Each of these packages has additional necessary subclasses. However, 
each class in that package only has access to the classes in the other package that it requires.
2. What interfaces/abstractions did you create? 
   - I created no interfaces or abstract classes. I did not find this necessary in this project.
3. Why did you choose Swing vs JavaFX?
   - Personally, I found Swing easier to use than JavaFX. I also thought that it would be easier for
   others to run on their machine that way as they would not need to manually change the configuration on
   the project. 

### Data Structures
1. How do you represent game state? (arrays, maps, objects?)
  - I defined a model, GameModel, which consists of a Board object. 
    That Board is then defined by BoardCells, each of which contains a certain number of cards.
    The controller enables changes to the game state by calling functions of the model.
    The view is for the UI and contains no game logic, relying on calls to the controller and the model
    to determine the display.
  - Additionally, the BoardCell and Board class both use arrays to contain member objects. The Board class contains
  a 2D array of BoardCells (16 in total). Each BoardCell contains an array of Card objects with length 
  - HashMaps were also put into play when assigning ids in the Board class and when setting up the buttons in the Swing UI. 
equal to the level of difficulty in the game. 
2. Why did you choose these structures?
   - I felt that these structures were the most natural choice. I divided all the components needed to run a game into
"has-a" relationships. What does a board have? Cells. What do cells have? Cards. What do cards have? ids. From there I
naturally gravitated to arrays (rather than lists), as the number of cells or cards would not be changing throughout the game. 
   
### Algorithms 
 1. **Uniform Distribution of Ids**: In the Board class, shuffleCards() 
takes the set ids and uniformly distributes them over the cards. There must always be an even number of each id, 
so that it is possible to match them. 
    - Steps: 
      1. Create a list of ids. 
      2. Go through the rows and columns of the grid
         - Create a list of candidate ids size level
      3. For each id in the candidate list: 
         1. Check if the id is already in the list of ids for that cell
         2. If it is not put it in the list, if it is swap that id with another in the master list of ids.
         3. Do step 2 until a valid match is found.
      4. When the id list is complete, initialize a board cell with those ids. 
    - Complexity > O(rows * cols * level). For a board with 16 cells and 3 levels, this 
    will run at least 48 times, but will likely run more because of the swaps.

## Challenges Faced
1. **Uniform Distribution of Ids**: I ran into a problem when play testing the game where sometimes there would be an 
uneven number of ids, causing the last card(s) in a game to be unmatched. This resulted in a lengthy dive 
into the difference between distribution algorithms. 
    - **Solution:** Through some searching, I found that I was completing the assignment of ids with a distribution scheme
that was average, rather than uniform. I switched to a uniform distribution. With the requirement that no cell would have the 
same id twice, this required the algorithm described above. 

2. **Upper levels calling lower level constructor** For the board class, I implemented two constructors. Board(int rows, int cols,
long seed) and Board(int rows, int cols, int level, long seed). When I would attempt to call the second one, the first one was triggered. 
    - **Solution:** This was occurring because of something called primative widening. I'm still not certain why it applied here, 
seeing as each of the constructors were of varying parameter length. However, in the function call int level and long seed were being 
combined to call the first constructor. Switching the order of these parameters in the method declaration resolved this issue. 

## What We Learned
- I learned a lot about Java Swing. This is my first time ever creating a GUI for a Java program, and I am really happy that 
I was able to pick it up without as many issues as I thought. 
- I also learned to trust my design choices. I wasted a lot of time at the beginning because I thought I was silly for wanting 
to create a BoardCell class to represent each cell in the Board. However, this ended up being the best way for me to model it. 
It was also the most simple way to get multiple cards in each board cell. I wish I had created it sooner. 

## If We Had More Time
1. Make it pretty! 
   - With more time I would have liked to make this project pretty. I would have resolved some of the
problems with the layout that make it a bit less readable and figured out how to stylize buttons in Swing. 
I also would have made the glyphs for the buttons into images instead. 
2. Make matches require ids be on the same level. 
   - Right now, it doesn't matter what level id you are attempting to match, if there is any matching id
on the two selected tiles, it will clear. I didn't complete this because this would have required a rework
of the id assignment system to ensure that ids would be random but still on the same layer. 

