***Command processing*** 

This project is small, basic command processor with commands presented in the system.
Idea is to take command type, arguments from different sources and return result of the execution.

Commands can be nested : each command can get result of other command execution.
Command (s) should be provided via command line arguments in form:

1. Command name of the execution command
2. Any arguments needed to execute command
3. Commands can be few and they should be separated by ';'
4. In case result of the execution must be passed to other command '|' should be used.
   Example : 'add 1 2 | print' -> should print 3 to console

List of supported commands should be :

1. Arithmetical commands (add, subtract, divide, multiply): example 'add 1 2' -> should return '3'
2. Print to console : example 'print x'
3. Sort array : 'sort 3 2 1 4' return array of {1, 2, 3, 4}. Optional flag '-d' should be provided 
   when array should be sorted in descending order.
4. Stats operations: min, max, avg
5. Arrays operation: first, last, count
6. Echo operation which convert list on arguments into array and return it.
7. Operation applied for each element in the array: square, sqrt, negate. 
   Example 'square -1 1 2 ' -> return array of {1, 1, 4}   

  