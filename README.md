# IF4031_Jgroup
A Replicated Stack or Set Program Based on Jgroups

## Requirements
 - JDK >= 1.8
 - [Maven](https://maven.apache.org/download.cgi)
 - [JGroups 3.6.6.Final](http://sourceforge.net/projects/javagroups/files/JGroups/)

## How to Build
1. Resolve maven dependency

	 ```
	 $ mvn dependency:copy-dependencies
	 ```
2. Build `jar` using maven `mvn`

	 ```
	 $ mvn package
	 ```

## How to Run

1. Run `ReplStack` from the generated `jar` in `target` folder

	 ```
	 $ java -cp target/dependency/*:target/kafka-chat-1.0.jar ReplStack.java
	 ```
2. Run `ReplSet` from the generated `jar` in `target` folder

	 ```
	 $ java -cp target/dependency/*:target/kafka-chat-1.0.jar ReplSet.java
	 ```

## Stack Commands
- `push <value_object>` : push element to replicated stack
- `pop` : pop top element from replicated stack
- `top` : see top replicated stack's element
- `exit` : stop program
- `quit` : stop program

## Set Commands
- `add <value_object>` : add one new element to replicated set
- `remove <value_object>` : remove one replicated set's element from replicated set
- `contains <value_object>` : check whether some element already in replicated set
- `exit` : stop program
- `quit` : stop program

## Testing
#### Scenario Testing:
*  

#### Testing Screenshoot:
![alt text](https://github.com/edmundophie/kafka-chat/blob/master/blob/testing_screenshot_prak_5.png "Testing Result")

## Team Member
- Edmund Ophie 13512095
- Kevin 13512097

## [Github Link](https://github.com/gejedah/IF4031_Jgroup.git)