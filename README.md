# ⚔️ 7330 Programming Homework

## 📖 Overview

This application is designed to manage a database of players and their match records through a variety of data manipulation and query commands. It reads commands from a CSV file and performs operations such as creating tables, adding new players, recording match results, and querying player information and match histories.

## 💻 Technology Stack

- **Language**: Java
- **Build Tool**: Maven
- **Database**: MySQL

## 🚀 Getting Started

### 📋 Prerequisites

- Java JDK 21
- Maven 4.0.0

## 🏗️ Project Structure

```
dbprog
├── .gitignore                              # Specifies intentionally untracked files to ignore
├── pom.xml                                 # Maven project file, containing dependencies and build configuration
├── README.md                               # This file with project description and documentation
├── src                                     # Source files directory
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── smu
│   │   │   │   │   ├── controller
│   │   │   │   │   │   └── Commands.java   # Controller class to handle intput command  requests
│   │   │   │   │   ├── domain
│   │   │   │   │   │   ├── Matches.java    # Domain class for Matches
│   │   │   │   │   │   └── Player.java     # Domain class for Player
│   │   │   │   │   ├── factory
│   │   │   │   │   │   └── impl
│   │   │   │   │   │       ├── ACommand.java # Factory implementation for A type commands
│   │   │   │   │   │       ├── CCommand.java # Factory implementation for C type commands
│   │   │   │   │   │       ├── ...           # Other command implementations
│   │   │   │   │   │       └── Factory.java  # Factory class for creating command instances
│   │   │   │   │   ├── service
│   │   │   │   │   │   └── Service.java     # Service layer with sql statement
│   │   │   │   │   └── util
│   │   │   │   │       └── JdbcUtils.java   # Utility class for JDBC operations
│   │   ├── resources
│   │   │   └── db.properties                # Properties file with database configuration
│   └── target                              # Compiled output directory with class files
└── bun.lockb                               # No direct equivalent in Maven projects, lock file for dependencies
```

## 📦 Installation

1. Clone the repository to your local machine:
   ```
   git clone https://github.com/unlckks/dbprog.git
   ```
2. Navigate to the project directory:
   ```
   cd https://github.com/unlckks/dbprog.git
   ```
3. Use Maven to compile the project:
   ```
   mvn compile
   ```
4. Package the application into a jar file:
   ```
   mvn package
   ```

## ▶️ Running the Application

To run the application, execute the following command, replacing `[csv_file_path]` with the path to your CSV file containing commands:

```
    run main
```

## 💼 Application Commands

The application supports the following commands read from the CSV file:

- e: Check if the required tables exist; if not, create them.
- r: Remove all tuples from all tables without removing the tables.
- p: Add a new player with details such as ID, name, birthdate, rating, and state.
- m: Enter information about a completed match.
- n: Enter information about a match to be played.
- c: Enter the results of a game that is already in the database.
- P: Return information about a player.
- A: List the win-loss record of a player against other players.
- D: List all matches within a specified date range in chronological order.
- M：return the matches by a certain player (regardless of whether it has been played), listed in chronological order. Subsequent fields contain.

### 🗂️ CSV File Format
The first field of each line in the CSV file specifies the command, followed by the necessary parameters for that command. The format of the parameters varies based on the command.



