# Math Game

**Math Game** is a 1v1 math game where you can challenge a friend or play against a bot. Both players are presented with the same math question and answer options. The screen is divided into two sections: the first player answers from the bottom section, while the second player answers from the top section. The first player to select the correct answer earns a point. If a player selects the wrong option, the opponent earns the point. 

The bot has three difficulty levels (Easy, Medium, Hard) that affect the accuracy of the answers and the response time. Challenge your friends or sharpen your skills against the bot at various difficulty levels.

## Features

- **1v1 gameplay**: Challenge your friends or play against the bot.
- **Math questions**: Both players see the same question and options.
- **Point system**: Correct answers score a point; wrong answers give the opponent a point.
- **Three bot levels**: Play against a bot with easy, medium, or hard difficulty settings.
  - **Easy**: The bot has a higher chance of selecting the correct answer and answers more quickly.
  - **Medium**: The bot has a balanced accuracy and response time.
  - **Hard**: The bot has lower accuracy and takes longer to respond, making it a tougher challenge.
- **Two-player screen split**: The screen is divided into two, with each player answering from their respective section.
- **Fast-paced**: Quick rounds to test your math skills under pressure.
- **Remote Config Support**: 
  - Dynamically change **theme colors** without updating the app.
  - Adjust **bot difficulty settings** remotely.

## Screenshots

Here are some screenshots of the game in action:

![Screenshot 1](https://github.com/user-attachments/assets/21e46e8b-0392-4b22-83ba-095481d93740)

## Getting Started

Clone the repository to get started with the game:
https://github.com/amEya911/MathGame.git

Follow the instructions to set up the game on your local machine.

### How to Play

1. Choose to play against a friend or the bot.
2. If playing with a friend, ensure both players are connected.
3. The game starts by showing a math question and multiple options on both screens.
4. The first player to select the correct answer gets a point.
5. If you select the wrong answer, your opponent gains a point.
6. The game continues until a set number of rounds or points are reached.

### Bot Difficulty Levels

- **Easy**: The bot has lower accuracy and takes longer to respond.
- **Medium**: The bot has balanced accuracy and response time.
- **Hard**: The bot answers with higher accuracy and responds quickly.

## Technical Overview

The **Math Game** app is built using **Jetpack Compose** and **Kotlin**, leveraging the power of modern Android development frameworks to create a smooth, dynamic user interface. The app uses **Event-Driven Architecture** to handle interactions and game state updates. This architecture ensures that the UI reacts to events like player actions, bot responses, and game state changes, making the app more responsive and scalable.

- **Jetpack Compose**: Used for building the UI in a declarative way, making it easier to create and manage UI components.
- **Kotlin**: The primary language for app development, providing a modern, concise, and safe way to write code.
- **Event-Driven Architecture**: The game’s flow is managed through events that trigger actions and updates in the UI, ensuring that the game responds in real time to player inputs and game logic.
- **Firebase Remote Config**: Allows real-time updates for theme colors and bot behavior without requiring users to update the app.

## Contributions

Feel free to contribute by opening issues or submitting pull requests. All contributions are welcome!
