#include <Keypad.h>
#include <EEPROM.h>

#define SIZE_BUFFER_DATA       50
boolean stringComplete = false;
String inputString = "";
char bufferData [SIZE_BUFFER_DATA];


//Specified password
const String KEY = "1234";
const String KEY2 = "5678";
const String KEY3 = "9012";

//Formato de mensaje "prioridad/idconjunto/#residencia/dispositivoid"

//Id del dispositivo
const String ID = "c1";

//Número de residencia (casa, apartamento)
const String RESIDENCIA = "1";

//Identificador del conjunto residencial
const String CONJUNTO = "conjunto1";


boolean bateria=false;
long currTimeBat;

//Minimum voltage required for an alert
const double MIN_VOLTAGE = 1.2;

//Battery measure pin
const int BATTERY_PIN = A0;

//Battery indicator
const int BATTERY_LED = 15;

//Current battery charge
double batteryCharge;


//Time in milliseconds which the system is locked
const int LOCK_TIME = 30000;
unsigned long CURRENT_TIME=0;

//Keypad rows
const byte ROWS = 4; 

//Keypad columns
const byte COLS = 3;

//Maximum number of attempts allowed
const byte maxAttempts = 3;

//Keypad mapping matrix
char hexaKeys[ROWS][COLS] = {
  {
    '1', '2', '3'
  }
  ,
  {
    '4', '5', '6'
  }
  ,
  {
    '7', '8', '9'
  }
  ,
  {
    '*', '0', '#'
  }
};

//Keypad row pins definition
byte rowPins[ROWS] = {
  9, 8, 7, 6
}; 

//Keypad column pins definition
byte colPins[COLS] = {
  5, 4, 3
};

//Keypad library initialization
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 

//Current key variable
String currentKey;
//Door state
boolean open;
//Number of current attempts
byte attempts;
//If the number of current attempts exceeds the maximum allowed
boolean block;

//RGB led pin values.
int redPin= 13;
int greenPin = 12;
int bluePin = 10;


/////PARTE 2 VARIABLES/////

//Button pin
const int CONTACT_PIN = 11;

//Attribute that defines the button state
boolean buttonState;


/////PARTE 3 VARIABLES/////

int ledPin = 14;                // choose the pin for the LED
int inputPin = 2;               // choose the input pin (for PIR sensor)
int pirState = LOW;             // we start, assuming no motion detected
int val = 0;                    // variable for reading the pin status

int unavez; //Este unavez es para que la alerta de puerta abierta mucho tiempo solo se ejecute una vez en lugar de hacerlo cada vez que entra al loop

void setup() {
  //////PARTE 3 SETUP//////
  pinMode(ledPin, OUTPUT);      // declare LED as output
  pinMode(inputPin, INPUT);     // declare sensor as input

  //////PARTE 1 SETUP//////
  Serial.begin(9600);

  currentKey = "";
  open = false;
  attempts = 0;
  block = false;

  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  setColor(0, 0, 255); // Blue Color

  /////PARTE 2 SETUP/////

  buttonState = false;

  pinMode(CONTACT_PIN,INPUT);

  setColor(0, 0, 255);
  currTimeBat=0;

  /////PARTE 4 BATERIA SETUP/////
  // Ouput pin definition for BATTERY_LED
  pinMode(BATTERY_LED,OUTPUT);

  //Input pin definition for battery measure
  pinMode(BATTERY_PIN,INPUT);
}

//////////////////////////////////////////////////Loop Header//////////////////////////////////////////////////
void loop() {


  ///////////////////////////////////////Battery Header////////////////////////////////////////////////////////

  batteryCharge = (analogRead(BATTERY_PIN)*5.4)/1024;
  digitalWrite(BATTERY_LED,HIGH);    
  //Measured value comparison with min voltage required
  if(batteryCharge<=MIN_VOLTAGE) {
    digitalWrite(BATTERY_LED,HIGH);
    bateria=true;
  }
  else {
    digitalWrite(BATTERY_LED,LOW);
    bateria=false;
  }
  if(bateria)
  {

    if(millis()-currTimeBat>=30000)
    {
      Serial.println("Low battery");
      Serial.println("baja/"+CONJUNTO+"/"+RESIDENCIA+"/"+ID);
      currTimeBat=millis();
      digitalWrite(BATTERY_LED,HIGH);
      setColor(255,0,0);
      delay(2000);
      digitalWrite(BATTERY_LED,LOW);
      setColor(0,0,255);
    }
  }
  ///////////////////////////////////////Battery Footer////////////////////////////////////////////////////////


  char customKey;

  if(!block) {
    //Selected key parsed;
    customKey = customKeypad.getKey();
  }
  else {
    Serial.println("Number of attempts exceeded");
    while(true);
  }

  //Verification of input and appended value
  if (customKey) {  
    currentKey+=String(customKey);
    Serial.println(currentKey);
  }

  //If the current key contains '*' and door is open
  if(open && currentKey.endsWith("*")) {
    open = false;
    Serial.println("Door closed");
    setColor(0, 0, 255); // Blue Color
    currentKey = "";
  }
  //If the current key contains '#' reset attempt
  if(currentKey.endsWith("#")&&currentKey.length()<=KEY.length()) {
    currentKey = "";
    Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length()== KEY.length()) {
    if(compareKey(currentKey)) {
      if(!open) {
        setColor(0, 255, 0); // Green Color
        open = true;
        Serial.println("Door opened!!");
        attempts = 0;
        CURRENT_TIME = millis();
      } 
      else {
        //If door is open more than 30 sec xxx

        if((millis()-CURRENT_TIME)>=5000) {
          if(unavez==0) {
            Serial.println("media/"+CONJUNTO+"/"+RESIDENCIA+"/"+ID); 
            unavez=1;
          }
          Serial.println("Door opened too much time!!");
          setColor(255, 0, 0); // Red Color
        } 
        else { 
          Serial.println("Door opened!!");
          unavez=0;
        }
      }
    }
    else {
      attempts++;
      if(attempts < 3) {
        setColor(255, 0, 0); //Red Color
        delay(1000);
        setColor(0, 0, 255); //Blue Color
      }
      currentKey = "";
      Serial.println("Number of attempts: "+String(attempts));
    }
  }
  else if(currentKey.length()> KEY.length()) {
    Serial.println("Door opened!!");
  }
  if(attempts>=maxAttempts) {
    currentKey = "";
    attempts = 0;
    Serial.println("alta/"+CONJUNTO+"/"+RESIDENCIA+"/"+ID);
    Serial.println("System locked");
    setColor(255, 0, 0); // Red Color
    delay(LOCK_TIME);
    Serial.println("System unlocked");
    setColor(0, 0, 255); // Blue Color
  }

  /////PARTE 2 LOOP/////

  //Button input read and processing 
  if(!buttonState) {
    if(digitalRead(CONTACT_PIN)) {
      CURRENT_TIME = millis();
      buttonState = true;
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      Serial.println("Door opened!!");
    }
  }
  else {
    if(digitalRead(CONTACT_PIN)) {
      if((millis()-CURRENT_TIME)>=5000) {
        setColor(255, 0, 0);
        Serial.println("media/"+CONJUNTO+"/"+RESIDENCIA+"/"+ID);
        Serial.println("Door opened too much time!!");
      }
    }
    else {
      setColor(0, 0, 255);
      open = false;
      buttonState = false;
      Serial.println("Door closed!!");
    }
  }
  delay(100);

  ////////PARTE 3 LOOP////////
  val = digitalRead(inputPin);  // read input value
  if (val == HIGH) {            // check if the input is HIGH        
    digitalWrite(ledPin, HIGH);  // turn LED ON
    if (pirState == LOW) {
      // we have just turned on
      Serial.println("Motion detected!");
      // We only want to print on the output change, not state
      pirState = HIGH;
    }
  } 
  else {
    digitalWrite(ledPin, LOW); // turn LED OFF
    if (pirState == HIGH) {
      // we have just turned of
      Serial.println("Motion ended!");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
  }
  receiveData();
  processData();
}
//////////////////////////////////////////Loop Footer/////////////////////////////////////////////////////

//Sets RGB led color.
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}
/////////////////////////////////Metodos de Modulo Wifi Header////////////////////////////////////////////
void receiveData() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    if (inChar == '\n') {
      inputString.toCharArray(bufferData, SIZE_BUFFER_DATA);
      stringComplete = true;
    }
  }
}

void processData() {
  if (stringComplete) {
    // Implementación...
    String arreglo [3];
    int index, val;

    val = arreglo[1].toInt();
    index = arreglo[2].toInt();

    processCommand(arreglo, inputString);

    if (arreglo[0] == "addPassword")
    {
      addPassword(val, index);
    }
    else if ( arreglo[0] == "updatePassword")
    {
      updatePassword(val, index);
    }
    else if ( arreglo[0] == "deletePassword")
    {
      deletePassword(index);
    }
    else if ( arreglo[0] == "deleteAllPasswords")
    {
      deleteAllPasswords();
    }

    inputString = "";
    stringComplete = false;
  }
}
/////////////////////////////////Metodos de Modulo Wifi Footer////////////////////////////////////////////

///////////////////////////////////////Gestion de claves Header////////////////////////////////////////////////////////
// Method that compares a key with stored keys
boolean compareKey(String key) {
  int acc = 3;
  int codif, arg0, arg1; 
  for(int i=0; i<3; i++) {
    codif = EEPROM.read(i);
    while(codif!=0) {
      if(codif%2==1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc+1)*256;
        arg1+= arg0;
        if(String(arg1)==key) {
          return true;
        }
      }
      acc+=2;
      codif>>=1;
    }
    acc=(i+1)*16+3;
  }
  return false;
}

// Methods that divides the command by parameters
void processCommand(String* result, String command) {
  char buf[sizeof(command)];
  String vars = "";
  vars.toCharArray(buf, sizeof(buf));
  char *p = buf;
  char *str;
  int i = 0;
  while ((str = strtok_r(p, ";", &p)) != NULL) {
    // delimiter is the semicolon
    result[i++] = str;
  }
}

//Method that adds a password in the specified index
void addPassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j |= i;
  EEPROM.write(location,j);
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  byte arg0 = val%256;
  byte arg1 = val/256;
  EEPROM.write((index*2)+3,arg0);
  EEPROM.write((index*2)+4,arg1);
}

//Method that deletes a password in the specified index
void deletePassword(int index) {
  byte i = 1;
  byte location = index/8;
  byte position = index%8;
  i<<=position;
  byte j = EEPROM.read(location);
  j ^= i;
  EEPROM.write(location,j);
}

//Method that deletes all passwords
void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0,0);
  EEPROM.write(1,0);
  EEPROM.write(2,0);
}
///////////////////////////////////////Gestion de claves Footer////////////////////////////////////////////////////////
