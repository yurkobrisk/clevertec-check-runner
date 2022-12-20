### Receipt Create CLI App

***

It`s just test project 🛒 from the Clevertec 

***

### Used:

> No special libraries. It`s just Java 17 & Gradle 7.5

***

### How to Run this:

>* check version jdk 17 is present

>* pull this project
 
>* in CLI go to the folder where project is
>
>>1. type: **gradlew** *[downloading will start]*
>
>>2. type: **gradlew build** *[building the project will start]*
>
>>3.1 start with params in line type: **gradlew run --args="2-4 1-5 6-9 3-1 4-2 5-2 7-9 card-1234"** 
>>
>> ###### ** args: productId-quantity & card-cardNumber
>
>>3.2 start with args in the filename type: **gradlew run --args="filename.txt"** 
>>
>> ###### ** use pattern - filename.txt - args inside file
> 
>> 3.3 start with data in the filenames type: **gradlew run --args="products.txt cards.txt input.txt"** 
>>
>> ###### ** products.txt - any file products, but check record format [productId,name,description,price;]
>> 
>> ###### ** cards.txt - any file cards, but check record format [cardNumber,name,lastName,discount;]
>> 
>> ###### ** input.txt - args: productId-quantity & card-cardNumber

***

###### ... two day were thinking, three days were coding ... and it hasn`t at all yet.
