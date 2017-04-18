# echomarket_java
Update April 18, 2017:  In seeking employment to prove -- yet again and again -- my web development skills, I re-wrote EchoMaket in ROR 5.  Rewrite went well,  didn't rewrite all views as that effort is very time-consuming.  Structure is very different as Active Record is superior to Hibernate  and SessionHelper with current_user excellent. If you are interested in seeing that code, please contact me.

EchoMarket is a goodwill resource for lending, borrowing and even trading or donating some of life's necessities, or items that you need just once and you want to avoid a one-time purchase.     
Echo Market is offered as a facilitating service to introduce borrowers seeking to lenders offering EchoMarket approved items.    
Once a borrower or lender has been introduced, EchoMarket will play no mediating role in that event, except in the case of anonymous item donations.  
EchoMarket was designed to protect participant privacy and assure success in participation.    
    
Echomarket was built using Java, IDE Netbeans, Faces, Hibernate with MySQL 
Fully functional as a (CDI) Contexts and Dependency Injection application.  Look at AbstracBean as a starting point. 
Runs in GlassFish and Tomee.  Tomcat apparently does not support CDI.  Tomcat will load the application but not recognize request variables.

Back in 2000 I was working with Java and Java Servlets.  In the meanwhile I have worked with ColdFusion, PhP. ASP.Net and Ruby on Rails.  I decided to return to Java and re-write an application that I previously wrote in Ruby on Rails.  These are my results...  all work in progress... 

What's going on with the application.
Dropped @ManagedBean and converted to @Named, @...Scope with @Inject

The basic bones exist...

Search function, need to incorporate Zip Code radius API, jst waiting until I deploy.
I need to complete Hibernate attributes.  
I need to code for the event of a user deleted, or drops a user type.  
I need to test and understand various browsers.  I know that IE does not find my Javascript.    
I need feedback in how I am Hibernate coding.   


Future ambitions:  
Registration via FaceBook or LinkedIn...  
Provide GIS Map of Items Sought and Items Offered.  
Make the GUI more attractive.  
Code for cell phone to capture item, bar code, as desired or offered.  
Convert code to Andriod.   
Learn and implement best-practice in deployment.  

Otherwise, I hope you see some value in not only my application but its code.  I am seeking comments on my work, employment in web development... Please contact me...  

Thanks,  

Liz
