# Chat App

This is a simple chat application developed for Android using Firebase Realtime Database for real-time messaging.

![Chat App Demo](/chat.gif)
## Features

- Users can sign up with an email and password or log in if they already have an account.
- **User Listing:** Upon logging in, users can see a list of other registered users to start a conversation.
- **Real-Time Messaging:** Users can send and receive messages in real-time.
- **Message History:** Chat history is maintained and loaded when users re-open a conversation.

## How to Use

1. **Registration/Login:** Users can register or log in using their email and password.
2. **User List:** After logging in, users can see a list of other registered users.
3. **Chatting:** Users can select a user from the list to start a chat. Messages are updated in real-time.

## Project Setup Instructions
### Clone the Project
### Firebase Setup:

1. Create a new project on Firebase Console.
2. Enable Firebase Authentication and Firebase Realtime Database.
3. Update the `google-services.json` file in the app directory with your Firebase configuration.

### Android Studio:

1. Open the project in Android Studio.
2. Ensure you have the necessary Firebase dependencies in your `build.gradle` file.
3. Build and run the application on an Android emulator or physical device.

## Dependencies

```gradle
implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
implementation("com.google.firebase:firebase-analytics")
implementation("com.google.firebase:firebase-database")
implementation("com.google.firebase:firebase-auth")
