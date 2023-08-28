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
### Security controller:
- `POST` **`auth/register`** Registers a new user
- `POST` **`auth/token`** Generates JWT token for user
- `GET` **`auth/validate`** Validates given JWT

### Recipient controller:
- `GET` **`/recipient/get`** Returns all recipients you have been created
- `GET` **`/recipient/get/{id}`** Returns single recipient by ID only if you created them
- `POST` **`/recipient/create`** Creates new recipient
- `DELETE` **`/recipient/{id}`** Deletes recipient by ID only if you created them
- `PATCH` **`/recipient/{id}`** Updates recipient by ID only if you created them

### Batch controller:
- `POST` **`/batch/save/csv`** Saves all your recipients with providen .csv file
- `POST` **`/batch/save/xlsx`** Saves all your recipients with providen .xlsx file
- `GET` **`/batch/get/csv`** Returns all your recipients in .csv file
- `GET` **`/batch/get/xlsx`** Returns all your recipients in .xlsx file

### Template controller:
- `GET` **`/template/get`** Returns all templates
- `GET` **`/template/get/{id}`** Returns single template by ID
- `POST` **`/template/create`** Creates new template
- `DELETE` **`/template/{id}`** Deletes template by ID
- `PATCH` **`/template/{id}`** Updates template by ID

### Notification controller:
 - `GET` **`/send/all/{template_id}`** sends notifications to your recipients by phone number and email using given template ID (WIP)
 - `GET` **`/send/email/{template_id}`** sends notifications to your recipients by email using given template ID
 - `GET` **`/send/phone/{template_id}`** sends notifications to your recipients by phone number using given template ID (WIP)

