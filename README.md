# Quiz App (Java Servlets + JSP + JDBC)
## Features
- Admin can add and approve questions.
- Users can take quizzes (only approved questions).
- Scores are stored in the database.

## How to run
1. Create MySQL database and import `sql/quiz_app.sql`.
2. Edit DB credentials in `src/main/java/utils/DBConnection.java`.
3. Build with Maven: `mvn clean package`
4. Deploy the generated `target/quiz-app.war` to Tomcat (or run via `mvn tomcat7:run` if configured).

## Notes
- This project keeps authentication simple (plain-text) for learning/demo purposes. Do not use in production without proper password hashing and protections.
