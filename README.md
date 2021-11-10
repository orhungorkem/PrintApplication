# Print Application

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
* Alice,password-Alice
* Bob,password-Bob
* Cecilia,password-Cecilia
* David,password-David
* Erica,password-Erica
* Fred,password-Fred
* George,password-George

Default printer names:  

* Printer1
* Printer2
* Printer3

## Access Control List Implementation:

Format for the list: 

&lt;username&gt; &lt;binary permission string&gt;

Binary string consists of 9 binary characters where each of them determines the permission for a job.  

### Job Enumeration

* print 1  
* queue 2  
* topqueue 3  
* start 4  
* stop 5  
* restart 6  
* status 7  
* see config 8  
* set config 9  

So let **b** the binary string for user **j**.  
Then there is a row "j b" in access control file.   
If **b[i-1]=='1'** then j is authorized for job i, else there is no authorization.  

#### Example:

ACL:
<pre><code>Alice 111111111
Bob 000111111
Cecilia 111001000
David 110000000
Erica 110000000
Fred 110000000
George 110000000</code></pre> 

To check if Bob is allowed to print, we should check the enumeration for **print** job. See **i = 1**. The permission string **b = 000111111**. Since **b[0]=0**, Bob is not allowed to print.
