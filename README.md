## PROJECT LIST + DESCRIPTION
----------------------------------------------------------------------------------------------------------------------------------------------------

### Project #4 - Clothing Website (Full Stack, In Progress)
Summary: This is an in progress website. My goal is to turn this into a clothing website. I am using this project to learn
many new skills (see thechnology stack below for things I have already learned/used).

Technology Stack  
-Frontend: JavaScript, HTML, CSS. I will be using React in the future.  
-Backend: Node.js, MongoDB (running on docker network).

Description: This is currently a simple website with a navigation bar and a body asking people to sign up to be notified when the website goes
online. When the form is filled with name and email, the info gets saved on MongoDB database running in a docker container. In the navigation 
bar, there is a link that takes you to the list of people that have signed up. This was done to practice connecting and reading/writing to 
a databse running on docker container/network. I am using express and EJS modules to inject dynamic content.

----------------------------------------------------------------------------------------------------------------------------------------------------

### Project #3 - Predicting Flight Delays (Big Data and Machine Learning)
Summary: Performed machine learning on airline datasets to predict how much delay to expect for any given flight. The models predict if 
a flight will have no delay, minor delay, moderate delay, or major delay.

Technology Stack  
-PySpark  
-ML models used: Random Forest, Logistic Regression

Description: This was a group project. I was responsible for the machine learning part where I used a couple of models to train/predict. Others were
responsible for the exploratory data analysis and report drafting. I learned how to use PySpark to handle big data. I set up a typical machine
learning project where I used pre processing pipeline to scale, encode, and handle null values if any. Since PySpark does not offer 
GridSearchCV, I used cross validator for hyperparameter tuning. Ideally I would have used TimeSeriesSplit to preserve the temporal aspect
of this data, however this is not available in PySpark. Initially, there was no correlation between the features and the labels. After
some feature engineering, I was able to get decent predictions. I created a new column where I labelled the data based on the delay observed.
For example if flight was up to an hour late, I classified that as minor delay. 

----------------------------------------------------------------------------------------------------------------------------------------------------

### Project #2 - Airline Ticketing (Full Stack Desktop Project using Java/MySQL)
Summary: This is a airline ticket booking system.  

Technology Stack  
-Frontend: Java  
-Backend: Java & MySQL  

Description: There are different types of users that can log into the app and they have different priviliges and access. 
For example, a registered user can collect points while the admin user can make modifications to the flights and fleet of aircraft. 
Guests can also use the app and can retrieve thei booking via a booking ID. Booking confirmation or cancellation are communicated via email.  
This was a group project. I completed the entire backend by myself and I also helped with the UI. I wrote most of the classes and coded all
of the logic performed when any button is pressed.

----------------------------------------------------------------------------------------------------------------------------------------------------

### Project #1 - TicTacToe (Learning Docker)
Summary: This is terminal a tic tac toe game.

Technology Stack  
-Deployed using docker
-Coded using Java

Description: The game can be played vs another player or agains the computer.

