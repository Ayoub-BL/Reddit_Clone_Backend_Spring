# Reddit Clone
 
| Backend project with J2EE (Spring Boot) |
| ------ |
| Auto-formation |
| Projet `Reddit_Clone_Backend_Spring` (v.0.0.1) |
***********************************************************************
## Features
REST API project for Spring Boot and Angular application, creating a clone of the famous reddit website using JWT and a fake SMTP test server to sign up.

## Tech
- Spring Boot - REST API ( backend )
- Java Mail with Mailtrap ( [Dummy SMTP email] )
- Asynchronous send mails
- Bcrypt Hashing Algorithm ( BCryptPasswordEncoder )
- JWT ( [JSON Web Tokens] )
- Spring Security
- Spring JPA (Hibernate)
- MySQL Database
- Angular - HTML & ✨Bootstrap✨ ( frontend ) - coming soon...
 
## Installation
- In this commit, I made some major changes. The application now uses JWT and it responds with the KeyStore `reddit_clone_backend_spring.jks` but this file in `.gitignore` has been ignored and, in consequence, you must add your own KeyStore to your project. So it is highly recommended that you take a look at how to create a KeyStore on [link-1] and [link-2].
- When it is done, you can re-configure your KeyStore Alias, Password and file path in the class JwtProvider.
- After that, you need to run `phpMyAdmin` (your MySQL database manager system), then run the application with Elipse IDE.
💡 `You don't need to create the database manually, it will be created automatically`.
- Enjoy 😉

## Tests - Screenshots
#### Sign up
<img align="center" src="screenshots/postman_signup.jpg" alt="Postman test - Sign Up">

#### Received email validation
<img align="center" src="screenshots/postman_account-verification_email.jpg" alt="Postman test - Account Verification Email">

#### Account verification with token
<img align="center" src="screenshots/postman_account-verification.jpg" alt="Postman test - Account Verification">

#### Login and get response token and username
<img align="center" src="screenshots/postman_login_response_token.jpg" alt="Postman test - Login">

## Contact
> `boulahya.ayoub@gmail.com`

> `ayoub.boulahya@etu.univ-amu.fr`

## License
**Free Software, Hell Yeah!**

[Dummy SMTP email]: <https://mailtrap.io/>

[JSON Web Tokens]: <https://jwt.io/>

[link-1]: <https://www.codeproject.com/Articles/1253786/Java-JWT-Token-Tutorial-using-JJWT-Library/>

[link-2]: <https://knowledge.digicert.com/quovadis/ssl-certificates/ssl-general-topics/common-java-keytool-commands.html>