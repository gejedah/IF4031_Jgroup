# IF4031_Jgroup
A Replicated Stack or Set Program Based on Jgroups

## Requirements
 - JDK >= 1.8
 - [Maven](https://maven.apache.org/download.cgi)

## How to Build

1. Resolve maven dependency

	 ```
	 $ mvn dependency:copy-dependencies
	 ```
2. Build `jar` using maven `mvn`

	 ```
	 $ mvn package
	 ```
## Some Note

1. 	Ketika pengetesan dilakukan secara otomatis via Junit, proses pengetesan berlangsung sangat cepat, hal ini bisa menyebabkan
	hasil tes terkadang failure terkadang success karena proses sinkronisasi pada Jgroups yang belum selesai dilakukan atau 
	kurang cepat dibandingkan jalannya thread Junit. Namun hal serupa tidak terjadi ketika pengetesan dilakukan secara manual.


## How to Run

1. Sebelum menjalankan aplikasi ini, pastikan bahwa node jgroups di dalam cluster bisa saling mendeteksi satu sama lain.
   Jika node di dalam cluster tidak bisa saling mendeteksi satu sama lain, maka tambahkan kode berikut melalui terminal
   
   opsi tambahan pada command 2 - 3 di bawah
   ```
   $ -Djgroups.bind_addr=127.0.0.1 -Djava.net.preferIPv4Stack=true
   ```
   
   kemudian
   ```
   $ route add -net 224.0.0.0 netmask 240.0.0.0 dev lo
   ```

2. Run `ReplStack` from the generated `jar` in `target` folder

	 ```
	 $ java -cp target/dependency/*:target/jgroupStack-1.0-SNAPSHOT.jar ReplStack
	 ```
3. Run `ReplSet` from the generated `jar` in `target` folder

	 ```
	 $ java -cp target/dependency/*:target/jgroupStack-1.0-SNAPSHOT.jar ReplSet
	 ```
	 
4. Run `ReplTest` method `TestStack`

	 ```
	 $ mvn -Dtest=ReplTest#TestStack test
	 ```

5. Run `ReplTest` method `TestSet`

	 ```
	 $ mvn -Dtest=ReplTest#TestSet test
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
#### Stack Scenario
1. Create First Stack
2. Test top
3. Test pop
4. push "nabe"
5. test top
6. Create Second Stack
7. test Second Stack top
8. push "urumi"
9. Test first stack top
10. Test second stack pop
11. Test second stack top
12. Test first stack pop

#### Set Scenario
1. Create First Set
2. Test First Set Add "albedo"
3. Test First Set Add "albedo"
4. Create Second Set
5. Test Second Set Contains "albedo"
6. Test Second Set Contains "albedos"
7. Test Second Set add "shalltear"
8. Test Second Set remove "albedoes"
9. Test Second Set remove "albedo"
10. Test First Set remove "shalltear"
11. Test First Set remove "shalltear"

#### Testing Screenshoot:
![alt text](https://github.com/gejedah/IF4031_Jgroup/blob/master/blob/prak_6_test_set.png "Testing Set Result")
![alt text](https://github.com/gejedah/IF4031_Jgroup/blob/master/blob/prak_6_test_stack.png "Testing Stack Result")

## Team Member
- Edmund Ophie 13512095
- Kevin 13512097

## [Github Link](https://github.com/gejedah/IF4031_Jgroup.git)
