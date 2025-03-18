#include <WiFi.h>
#include <HTTPClient.h>

#define WIFI_SSID "Bharat"
#define WIFI_PASSWORD "12345678"

#define RXp2 16
#define TXp2 17

String incomingData;

void setup() {
  Serial.begin(9600);
  Serial2.begin(9600, SERIAL_8N1, RXp2, TXp2); // Match Arduino baud rate

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi");
}

void loop() {
  if (WiFi.status() == WL_CONNECTED && Serial2.available()) {
    incomingData = Serial2.readStringUntil('\n'); // Read data from Arduino until newline
    Serial.println("Received data from Arduino: " + incomingData);

    sendToFirebase(incomingData);
  }
}

void sendToFirebase(String jsonData) {
  HTTPClient http;
  String url = "https://projectloginnaati-default-rtdb.firebaseio.com/sensorData.json";

  // Generate a unique ID based on your project name or any other identifier
  String uniqueID = "your_project_name_" + String(millis());

  // Append the unique ID to the Firebase URL
  url += "?identifier=" + uniqueID;

  http.begin(url);

  http.addHeader("Content-Type", "application/json");

  int httpResponseCode = http.PUT(jsonData); // Use PUT to update data

  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
  } else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }

  http.end();
}
