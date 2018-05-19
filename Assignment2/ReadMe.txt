These jars are generated with java 1.8
If you have a different version you can specify it in command line this way:

> <path_to_jre>/bin/java.exe -cp Executable.jar

--------------
Run Server jar:
--------------
> java -cp G16_Prototype_Server.jar server.AEServer <port>

e.g.
> "C:\Program Files\Java\jre1.8.0_151\bin\javaw.exe" -cp "G16_Prototype_Server.jar;mysql-connector-java-5.1.46-bin.jar" server.AEServer


--------------
Run Client jar:
--------------
> java -cp G16_Prototype_Client.jar application.AESystem <ip>

e.g.
> "C:\Program Files\Java\jre1.8.0_151\bin\java.exe" -cp G16_Prototype_Client.jar application.AESystem 127.0.0.1


----------------
Troubleshooting:
----------------

1) SQLException: No suitable driver found for jdbc:mysql
   
Need to include the mysql connector jar in java classpath:
java -cp "G16_Prototype_Server.jar;mysql-connector-java-5.1.46-bin.jar"

2) WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.

Need to add the following to your sql connection string: ?autoReconnect=true&useSSL=false
e.g.
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aes?autoReconnect=true&useSSL=false","root","root");

Need 

