# PrintApplication

To run the program, first run ApplicationServer.java and then run Client.java.

# Default users, passwords:  

* user-0,password-0
* user-1,password-1
* user-2,password-2
* user-3,password-3
* user-4,password-4
* user-5,password-5
* user-6,password-6
* user-7,password-7
* user-8,password-8
* user-9,password-9
* Alice,password-Alice
* Bob,password-Bob
* Cecilia,password-Cecilia
* David,password-David
* Erica,password-Erica
* Fred,password-Fred
* George,password-George
* Henry,password-Henry
* Ida,password-Ida

# Default printer names:  

* Printer1
* Printer2
* Printer3

# Default printer server configuration:

  color | scale 
  --- | --- 
  black | fit 

# ACR
In the table bellow we can see the system users and their roles

Username | Role 
--- | --- 
Alice | role-1 
Cecilia | role-3
David | role-0
Erica | role-0
Fred | role-0
user-0 -> user-8 | role-0
user-9 | role-none
Bob | role-none
George | role-2
Henry | role-0
Ida | role-3
<br>

In the table bellow we can see the permission binary for each action

setConfig | readConfig | status | stop |start |  restart | topQueue | seeQueue | print | Decimal
--- | --- | --- | --- |--- |--- |--- |--- |--- | ---
0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 1
0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 2
0 | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 4
0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 8
0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 16
0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 32
0 | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 64
0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 128
1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 256
<br>
In the table bellow we can see all system roles

Role Title | Role ID 
--- | --- |
role-0 | ordinary users 
role-1 | server manager 
role-2 | service technician
role-3 | power user 
<br>
In the table bellow we can see all permission for each role

setConfig | readConfig | status | stop | start | restart | topQueue | seeQueue | print | Roles | Decimal
--- | --- | --- | --- |--- |--- |--- |--- |--- | --- | ---
0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | 1 | role-0 | 3
1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | role-1 | 511
1 | 1 | 1 | 1 | 1 | 1 | 0 | 0 | 0 | role-2 | 504
0 | 0 | 0 | 0 | 0 | 1 | 1 | 1 | 1 | role-3 | 15
<br>

Example of role-permission check:
user Cecilia is a power user. This means that her role has assigned permission of scope 15. So when Cecilia is
requesting a restart which requires scope 8 then we need check whether the role of the user has the requested operation.

Check: <b>role-permission AND operation-permission = operation-permission</b>

Operation | setConfig | readConfig | status | stop | start | restart | topQueue | seeQueue | print 
--- |--- | --- | --- | --- |--- |--- |--- |--- |--- | 
restart | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 
role-3 | 0 | 0 | 0 | 0 | 0 | 1 | 1 | 1 | 1
AND | 0 | 0 | 0 | 0 | 0 | 1 | 0 | 0 | 0 

Not permitted to readConfiguration:

Operation | setConfig | readConfig | status | stop | start | restart | topQueue | seeQueue | print
--- |--- | --- | --- | --- |--- |--- |--- |--- |--- | 
restart | 0 | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0
role-3 | 0 | 0 | 0 | 0 | 0 | 1 | 1 | 1 | 1
AND | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0

In order to allow power-user to restart the printer we need the <b>AND result</b> to be <b>equal</b> to the <b>restart-operation-permission</b>.