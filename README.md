🟡 Pac-Man in Java (Swing)

Pac-Man game project developed in Java using Swing, focused on clean code organization, game states, and realistic ghost behavior.

This project was created for study and practice of object-oriented programming, game logic, and software structure.

🎮 Features

✔️ Main menu system
✔️ Score system
✔️ Pac-Man life counter
✔️ Power Pellet (ghost frightened mode)
✔️ Ghosts with pathfinding
✔️ Ghost states:

chase

frightened

eaten

locked in spawn

✔️ Victory screen
✔️ Game over screen
✔️ Position reset after death
✔️ Package-based organized structure

👻 Ghost behavior

The ghosts use a state system implemented with an enum:

CHASE
FRIGHTENED
EATEN
LOCKED

When Pac-Man eats a Power Pellet:

➡️ ghosts turn blue
➡️ they run away from the player
➡️ they move slower

If a ghost is eaten:

➡️ it becomes only the eyes
➡️ it returns to the spawn point
➡️ it stays locked for a few seconds
➡️ then returns to chase mode

🕹️ Controls

⬆️ Up
⬇️ Down
⬅️ Left
➡️ Right

ENTER → confirm menu options

💻 Requirements

Java 21 or higher

An IDE (IntelliJ, Eclipse, VSCode or any other)

▶️ How to run

Clone the repository:

git clone https://github.com/marcogasparotto/PacMan.git


Open the project in your IDE

Run the class:

br.com.marco.pacman.Main


Done 😄
The game will start normally.

⭐ Final note

This project is not an official version of the original Pac-Man.
It is a non-profit educational project, inspired by the classic game.
