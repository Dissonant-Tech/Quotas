
Android Activities are central to an application but because of androids highly
decoupled API they do not (in my opinion) naturally form either MVC or MVP 
programming styles. 

Lukily the highly decoupled API allows shaping them into either style relatively
simple.


Methods
=======

    * init(): Initializes public fields. __Only__ initialize, do nothing else.

    * setupView(): Connects android.View objects to their respective fields.

    * createListeners(): Creates onClick and other listeners for initialized fields. 
        _ This method may call to other methods or may be split in the future as it 
            is prone to being very long _

NOTES:
======

1. __DO NOT__ use android:onClick in xml. Set onClickListeners inside activities.
    Seperate view from logic.

2. Do not create a new .java file for every listener as many will only call
    one liner statements.
