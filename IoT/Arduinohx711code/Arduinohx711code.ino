#include <Arduino.h>
#include <HX711.h>
#include <LiquidCrystal_I2C.h>

const int LOADCELL_DOUT_PIN = 2;
const int LOADCELL_SCK_PIN = 3;

HX711 scale;

const float PETROL_DENSITY = 0.75;

// Define LCD address and dimensions
const int lcdColumns = 16;
const int lcdRows = 2;
const int lcdAddress = 0x27; // Adjust this according to your LCD module

// Initialize the LCD with the I2C address
LiquidCrystal_I2C lcd(lcdAddress, lcdColumns, lcdRows);

void setup() {
  Serial.begin(9600);
  
  scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
  scale.set_scale(459.558);
  scale.tare();

  // Initialize the LCD
  lcd.init();
  lcd.backlight();
  lcd.clear();
}

void loop() {
  // Read weight from load cell
  float weight = scale.get_units();

  // Convert weight to liters of petrol
  float petrolVolume = weight / PETROL_DENSITY;

  // Convert float to string
  String petrolVolumeStr = String(petrolVolume, 2);

  // Create JSON payload in a String variable
  String jsonPayload = "{\"liters\": " + petrolVolumeStr + "}";

  // Display JSON data on LCD
  lcd.setCursor(0, 0);
  lcd.print("Petrol: ");
  lcd.print(petrolVolumeStr);

  // Clear the LCD display
  lcd.setCursor(0, 1);
  lcd.print("                "); // Clear the entire line

  // Print JSON payload to serial monitor
  Serial.println(jsonPayload);

  delay(5000); // Adjust delay as needed
}
