# Matrix360

## Overview
Matrix360 is an IoT-based weighing system that uses Arduino Uno, ESP32, and HX711 to measure weight and transmit real-time data to Firebase. The data is then displayed on a mobile application for easy monitoring.

## Features
- **Real-time Weight Monitoring**: Captures weight data using HX711 and sends it to Firebase.
- **ESP32 & Arduino Integration**: Seamless communication between ESP32 and Arduino Uno.
- **Cloud Storage**: Data is stored and managed using Firebase.
- **Mobile Application**: Displays real-time weight data on a user-friendly interface.

## Hardware Components
- **Arduino Uno**
- **ESP32**
- **HX711 Load Cell**
- **Weighing Scale Sensor**
- **LCD Display (I2C)**
- **Jumper Wires & Power Supply**

## Software & Technologies Used
- **Embedded C / Arduino IDE** (for ESP32 and Arduino Uno programming)
- **Firebase Realtime Database** (for data storage)
- **Mobile App Development** (Java, Android Studio for app UI)

## Installation & Setup
### 1. Clone the Repository
```bash
git clone https://github.com/bharatnaikar/Matrix360.git
cd Matrix360
```

### 2. Set Up Arduino & ESP32
- Install Arduino IDE and necessary board libraries.
- Upload the `ESP32.ino` and `Arduino.ino` files to respective boards.

### 3. Firebase Configuration
- Create a Firebase project.
- Get the Firebase Realtime Database URL and API key.
- Update the Firebase credentials in the ESP32 code.

### 4. Run the Mobile App
- Open the Android project in **Android Studio**.
- Ensure Firebase dependencies are added in `build.gradle`.
- Connect an Android device or emulator and run:
  ```bash
  ./gradlew assembleDebug
  ```

## Collecting Data from HX711 (Arduino Uno)
The Arduino Uno is responsible for reading weight data from the HX711 load cell and converting it into a meaningful petrol volume reading. The process involves the following steps:
1. Initialize the HX711 sensor and calibrate the scale.
2. Read the raw weight data from the HX711 sensor.
3. Convert the weight into petrol volume using a predefined density value.
4. Format the data into a JSON-like structure.
5. Display the petrol volume on an LCD screen.
6. Send the formatted data to ESP32 over a serial connection.

## Receiving Data from Arduino Uno (ESP32)
The ESP32 receives weight data from Arduino Uno via serial communication. The process involves the following steps:
1. Establish a serial connection between ESP32 and Arduino Uno.
2. Configure the ESP32 to read incoming serial data from Arduino.
3. Continuously check for available data from the Arduino.
4. Read the data from the serial buffer until a newline character is encountered.
5. Print the received data for debugging purposes.
6. Send the received data to Firebase for real-time storage.

## Sending Data to Firebase
The ESP32 sends weight data to Firebase using HTTP requests. The process involves the following steps:
1. Construct the Firebase URL where the data will be stored.
2. Generate a unique identifier for each data entry.
3. Append the unique identifier to the Firebase URL.
4. Send an HTTP request with the weight data in JSON format.
5. Handle the server response and check for errors.
6. Close the HTTP connection after sending the data.

## Usage
1. Power on the system.
2. The Arduino Uno reads weight data from HX711 and calculates the petrol volume.
3. The ESP32 collects weight data from Arduino Uno via serial communication.
4. Data is sent to Firebase in real-time using HTTP requests.
5. The mobile app fetches and displays the weight data dynamically.

## Troubleshooting
- **ESP32 Not Connecting to Wi-Fi?** Double-check SSID and password.
- **No Data in Firebase?** Ensure correct database rules and API key setup.
- **No Data Received from Arduino?** Verify serial connections and matching baud rates.
- **LCD Display Not Working?** Check I2C address and connections.
- **App Not Updating?** Refresh the Firebase listener in the app.





