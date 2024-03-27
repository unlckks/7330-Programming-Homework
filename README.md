# 7330 Programming Homework

## Overview

This application is designed to manage a database of players and their match records through a variety of data manipulation and query commands. The program reads commands from a CSV file and performs operations such as creating tables, adding new players, recording match results, and querying player information and match histories.

## Technology Stack

-Language: Java
- Build Tool: Maven
- Database: Mysql

## Getting Started

### Prerequisites

- Java JDK 21
- Maven 4.0.0
- [Any other prerequisites]

dbprog
├── .gitignore
├── pom.xml                              # Maven project file, containing dependencies and build configuration
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── smu
│   │   │   │   │   ├── controller
│   │   │   │   │   │   └── Commands.java      # Controller class, handle HTTP requests
│   │   │   │   │   ├── domain
│   │   │   │   │   │   ├── Matches.java       # Domain class for matches
│   │   │   │   │   │   └── Player.java        # Domain class for player
│   │   │   │   │   ├── factory
│   │   │   │   │   │   └── impl
│   │   │   │   │   │       ├── ACommand.java  # Factory implementation classes for commands
│   │   │   │   │   │       ├── CCommand.java
│   │   │   │   │   │       ├── ...            # Other command classes
│   │   │   │   │   │       └── Factory.java   # Factory class to create instances
│   │   │   │   │   ├── service
│   │   │   │   │   │   └── Service.java       # Service layer, business logic
│   │   │   │   │   └── util
│   │   │   │   │       └── JdbcUtils.java     # Utility class for JDBC operations
│   │   ├── resources
│   │   │   └── db.properties                  # Database configuration properties
│   └── target                                # Compiled output directory, contains the compiled class files
└── bun.lockb                                 # No equivalent, possibly a lock file for Maven dependencies

### Installation

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

### Running the Application

To run the application, execute the following command, replacing `[csv_file_path]` with the path to your CSV file containing commands:

```
    run main
```

## Application Commands

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
### CSV File Format

The first field of each line in the CSV file specifies the command, followed by the necessary parameters for that command. The format of the parameters varies based on the command.



