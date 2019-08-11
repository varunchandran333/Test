# Test
The language used is Kotlin with structural architecture MVVM. 

Design pattern used is Fatory pattern for Viewmodel provider and Observer for data observing.

The application is lifecycle aware using Viewmodel architecture.

Repository pattern is used to add as a mediator between data and viewmodel.

Adapter pattern is used to list data and listen data from firebase.

Used worker threads for doing DB operations.

Utilised jetpack components like viewmodel, livedata, databinding and room.

The project consist of 3 modules Login, OrderListing and Add new Order.



#In Login 
Username is validated as Email or Not empty, password as minimum 5 characters

Login credentials can be remembered using remember button and saves credential to shared preferences.

Unitest case are written using espresso for Login page. 



#In OrderListing

The data is listed from Firestore and saved locally using Room persistance storage.

The CRED operations are enabled, 

where NEW order is INSERTED using Floating Action Button

UPDATE order can be listened using LONG Press

To DELETE order just swipe the order.



#Add new Order

In add new order page, data can be provided using Edittext view , Datepicker and Button

Location is enabled on succesful RUNTIME permission using FUSED API on get Location Button.

No fields are set as mandatory, every field is free to leave blank.

The page is used for data update also,its determined using the parcelised data provided as Bundle.



