# Emergency notification system

>This project designed to effectively send notifications to people in case of emergency

## Features:
- `User Registration:` User can create an account to provide other people's contact information(email, phone number)  
- `Multiple Addition:` User can add large amount of people at once using .csv and .xlsx  
- `Different Templates:` The system allows to manage templates depending on the situation  
- `Scalability:` The system is able to work with multiple instances distributing the load among them using Kafka.

## Documentation:
>All endpoints starts with `http://localhost:8080/`
>
### Security-controller:
- `POST` **`auth/register`** Registers a new user
- `POST` **`auth/token`** Generates JWT token for user
- `GET` **`auth/validate`** Validates given JWT

### todo



