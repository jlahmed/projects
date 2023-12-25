# Temperature report
Author: Jubayer Ahmed

## UML class diagram
![Temperature_Class_UML](Temperature_Class_UML.png)

Code on plantuml:
@startuml
class Temperature{
~degrees : double

~Temperature(d: double)
~getFahrenheit() : double
~getCelsius() : double
+main(String[]) : void
}
@enduml


## Specification

Class Name: Temperature

The class Temperature represents a temperature measurement in fahrenheit 
and allows its reporting in fahrenheit or celsius.
When the program runs, two temperature objects are created.
The first object (thermometer1 is assigned 20F and the second (thermometer2 is assigned 98.6F.
These are converted to celsius and the results are displayed in both fahrenheit and celsius.


## Execution and Testing

Execution:
![Temperature_Execution](Temperature_Execution.png)

Testing:
![Temperature_Testing](Temperature_Testing.png)


