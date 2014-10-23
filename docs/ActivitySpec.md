
Android Activities are central to an application but because of androids highly
decoupled API they do not (in my opinion) naturally form either MVC or MVP 
programming styles. 

Lukily the highly decoupled API allows shaping them into either style relatively
simple.

Fields
======

1. Field names should be descriptive. lower-case word followed by upper-case words.
    Example: `private ImageView fabButton` . ImageView is not descriptive and as such
    `fabButton` is used for the field name.

2. Member class names should start with `m` to indicate they are class members.
    
3. If member class names are too long they should be shortend by using lowercase 
    letters in place of the first few (or more, as neccessary) words, followed by 
    the full upppercase word(s) such as in the case of 
    `private ColorSpinnerAdapter m_csAdapter`


Methods
=======

1. init(): Initializes public fields. __Only__ initialize, do nothing else.

2. setupView(): Connects android.View objects to their respective fields.

3. createListeners(): Creates onClick and other listeners for initialized fields. 
    _This method may call to other methods or may be split in the future as it 
        is prone to being very long_

NOTES:
======

1. __DO NOT__ use android:onClick in xml. Set onClickListeners inside activities.
    Seperate view from logic.

2. Do not create a new .java file for every listener as many will only call
    one liner statements.
