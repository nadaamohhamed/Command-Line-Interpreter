# Command Line Interpreter
Java Implementation of a Command Line Interpreter program - Linux Commands. The CLI will keep accepting different commands from the user until the user writes
**_exit_**, then the CLI terminates. The CLI will support the following 16 commands:
- **echo**: prints the given argument.
- **pwd**: prints the current working directory.
- **cd**: changes the current working directory to the given directory, depending on the argument given.
- **ls**: lists the contents of the current working directory in a sorted order.
- **ls -r**: lists the contents of the current working directory in a reverse order.
- **mkdir**: creates a new directory in the current working directory.
- **rmdir**: removes an empty directory/directories from the current working directory, depending on the argument given.
- **touch**: creates a new file in the current working directory.
- **cp**: copies a file from a given path to another path.
- **cp -r**: copies a directory from a given path to another path.
- **rm**: removes a file if it exists from the current working directory.
- **cat**: concatenates the content of two files and prints the result.
- **wc**: prints the number of lines, words, characters in a file and its name.
- **>**: redirects the output of a command to a file, overwriting previous content.
- **>>**: redirects the output of a command to a file, appending to previous content.
- **history**: prints an enumerated list with the commands you’ve used in the past.