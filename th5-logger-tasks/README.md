***Logger***

You are given two interfaces `ua.hillel.java.elementary1.logger.Logger` and 
```ua.hillel.java.elementary1.logger.LoggerFactory```.
Your task is to write file logger. 
File logger will append all message happens in the system into the logging file.

When no file with such name exists it must be added into filesystem.
We must assume that number of the messages will be high and we should minimize latency
of IO flow (read-write flow) as much as possible. (use buffers, caches, etc.)

Each log message must be logger on separate line.
Each log message should start with date and time when this message is logged.

**Rotation**

Logger can log huge number of messages and file can increase very quickly.
Therefore two possible file limitation mechanisms must be implemented:

1. Date rotation. 
2. Size rotation.

*Date rotation*

When calendar date passed and there exists messages in the log file your logger should
rename existed file and add date at the end. Also it must zip this file. 
New file with same filename used at the beginning must be created.

Example:
  1. I created logger which must wrote logs into file `server.log`. Today is 10.03
  2. When next day came (11.03) and `server.log` contains any messages 
  (you can check just if this file was created).
  3. `server.log` must be renamed into `server.log-2019-10-03` and zip (gzip) into file `server.log-2019-10-03.gz`.
  4. Further logs must be written into new `server.log` file.
  
*Size rotation*

Same as upper but with file size. When size of the file reaches limit you must rename file 
in same way as declined above and (zip) gzip. There must be more the one file per day so add counter 
in the file.

Example:
  1. I created logger which must wrote logs into file `server.log`. Today is 10.03 limit 5MB.
  2. When file size became more then 5 MB 
      `server.log` must be renamed into `server.log.1-2019-10-03` and
       zip (gzip) into file `server.log.1-2019-10-03.gz`.
  3. When on the same day `server.log` again reaches limit it 
       must be renamed into `server.log.2-2019-10-03` and
        zip (gzip) into file `server.log.2-2019-10-03.gz`.