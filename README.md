This repo contains simple mini test project with TestNG/Selenium/Java. 
The project is for demonstration purposes only and not for real tests.  

Application used:  https://www.booking.com/.   
Registering/account not needed to run tests

11/10/2022 I checked today and some tests arefailing because of some UI changes. Unfortunately I don't have a time now to fix this, but I will try to do it later.

11/15/2022 Updated some locators., but not sure it has a sense to continue. 2 different home pages are being launced from time to time, intermittently.Looks like some servers are not updated with the latest code (???). Without discussing this issue with devs, I think, there is no sense to dig deeper and try to create duplicated functionality for the home page methods.
Hence tests are expected to fail intermittently with NoSuchElementException.
