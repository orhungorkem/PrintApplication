# PrintApplication

To run the program, first run ApplicationServer.java and then run Client.java.

Default users, passwords:  

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

Default printer names:  

* Printer1
* Printer2
* Printer3

# ACL:

* need to think of start, stop
* need to add hashes for alice bob new chars

Format for acl:

<username> binary string

binary string consists of 9 characters where each of them shows a job
print 1
queue 2
topqueue 3
start 4
stop 5
restart 6
status 7
see config 8
set config 9

so let b the binary string for user j.
then if b[0]=='1' then j is authorized to print, else not.
