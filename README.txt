=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: plarkin
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
  The 2D array represent my game board. It is an integer array and is be used to represent
  whether or not the user has clicked on a cell and if they have if they hit a battleship.
  At the beginning the array is instantiated with all 0s. 0 represents an unclicked cell.
  When a user clicks on a cell it will either change to -1 which represents water or 1
  which represents a hit of a battleship. A 2D array is appropriate because the size of the
  game board is constant and known: 7x7. Additionally, it is easy to access the contents
  of each cell when a user clicks to determine if the square should be painted blue for water
  or black for a ship.

  2. Collections
  Each ship is an instance of a “ship” object. Each ship object contains the following data:
  the x and y coordinates of the starting point which is represented as ints,
  the length of the ship represented as an int, the x and y coordinates of the ending
  point represented as an int, the number of hits represented as an integer, whether
  or not the ship has been sunk represented as a boolean, and the direction represented
  as a character. These ship objects are stored in a TreeSet because there are
  no duplicate ships allowed, each ship is unique. I override the equals method so
  that for a ship to be equal to another ship it has to have the same length and the same
  starting and ending coordinates. When a user clicks on a coordinate of the 2D array grid,
  each object in the set is iterated over and checked to see if that coordinate is
  occupied by a ship. The reason that my ships are stored in a collection and not my
  2D array is because the number of ships each game is randomly generated and everytime a ship
  is created it is added to the collection. Additionally, it would not make sense to store
  a ship object which has length 3 in one cell of a 2D array because if a user clicks on a
  neighboring cell that doesn’t have a ship object in it does not necessarily mean that a
  ship isn’t occupying that space. Therefore it makes most sense to have a collection of
  ships and iterate through each one to see if it was hit.

  3. File I/O
  I will use file I/O to store the state of the game. It will store the game board array which
  shows which cells have been uncovered and if they are water or a ship. That way when a file is
  read back in and the board is repainted, it will know which cells to make black and which to
  make blue. It will store the number of ships sunk and number of ships left so that the state
  is correctly updated and displayed when the file is read back in.
  Additionally, I iterate through my set of ships storing their start coordinates,
  length, direction, number of hits, and sunk status so that when I read the file back in I can
  create these ship objects again. This file will be able to be loaded back in so the user can
  pick up with where they left off and continue to play the game.

  4. JUnit Testable Componenet
    I use JUnit tests to make sure that my code is working properly. I tested methods
    in my Ship class and methods the state of my game/logic from the Battleship class.
    Some examples of things that I tested included the isHit() method in the Ship class
    to make sure that when given a coordinate it correctly determined if that coordinate
    is occupied by the ship it is called on. I made sure to test edge cases like if it
    was given a "bad" coordinate. I also created helper methods to help test the state of
    my game because there is a lot of randomization involved. For example, I needed to be
    able to place ships where I know where they are so that I could test playTurn() on a specific
    coordinate and then make sure that the gameBoard changed to have a 1 in the cell where a ship
    is, a -1 in a cell where water is, and a 0 in cells that had not been clicked. Additionally,
    I tested to make sure the counter for the number of ships sunk changed when indeed a ship
    was sunk. JUnit tests are appropriate because I wrote my code in a way that allows for
    testing. I made sure to test methods in all of my classes and this helped make sure
    my game would run smoothly.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  I have a Battleship class which implements all of my game logic. For example, the
  reset method places/instantiates a random number of ships of random length randomly on the board.
  The playTurn method iterates through the collection of ships and calls the isHit() method
  on each ship to determine if a ship occupies that coordinate and then changes the board
  state appropriately to either -1 for water or 1 for ship. Battleship is also where I have
  the logic for the File I/O. It saves the game board state and ship information to a file
  and then reads the file back in to update the board state and create new ship objects. I have
  a Ship class where each instance of this class is a ship on the board. To create a ship you
  need to input the starting x and y coordinates, the length, the direction, the number of
  current hits, and if it has been sunk. Then there are many "getter" methods along with methods
  that like isHit() which determines if a ship occupies a given coordinate and if it does
  increments the number of hits the ship has. Additionally, you can sink() a ship which will
  check if the number of hits the ship has equals the length and then will update is isSunk
  variable to true if it is. It has a method called overlaps() which checks if another ship
  overlaps with this ship and is used when creating random ships at the beginning because
  ships cannot be on top of each other. The GameBoard class draws the game board and creates
  methods to update the information displayed on the board like how many ships have been sunk
  and how many ships are left. The RunBattleship creates the layout by adding necessary buttons
  and saying what methods to call when each button is clicked.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  The hardest part for me was the File I/O because I wasn't sure how I was going to store all
  the information about each object all with the game board and other necessary information.
  At first I just stored an "answer key" and the current state of the board which would
  correctly update the visual of the board when you loaded a file back in, but you couldn't
  correctly keep playing the game because each ship object didn't know how many hits it had
  and whether or not it was sunk. To resolve this problem I had to update how I was storing
  the information and how you instantiate a ship. The first 7 lines of the file I stored the
  2D array game state and then after that I stored how many ships there were and how many had
  already been sunk. After that, I stored information about each ship so I could instantiate
  ship objects correctly. Additionally, I first had it so that to create a ship object, you
  only needed the start coordinates, the length and the direction, but then I added that
  you needed to add the number of hits it had and whether or not it was sunk so that
  I could create partially hit or sunken ships again when a file was loaded back in.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think my design is very good and I put a lot of thought into how I would create this
  before I even started and of course made some changes along the way when I ran into
  problems like the one I mentioned above for File I/O. There is good separation of
  functionality which made it easy to test different parts of the game like how I
  have a Ship class that is separate from the Battleship class. The private state is
  well encapsulated because I return a copy of the array in the method that returns the
  board array. I don't think I would refactor anything if given the chance.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
