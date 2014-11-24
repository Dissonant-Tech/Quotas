
Problem Definition
==================


Normally time management is done by setting time slots where specific tasks
will be completed. This apporach leaves little to no room for unexpected events
nor any room for flexability. 

Solution:
Create a program that manages time using tasks rather than time slots. Using, 
like the name suggests, quotas for specific tasks and activities we can create
a variable, and flexible system of time management.


Software Requirements
=====================


**Inputs:**
    
    1. Android application
    2. Web app?
    3. Linux app?


**Outputs:**
    
    1. Android notifications/alerts
    2. Email notifications?


**Basic Tasks:**
    
    1. Manage Quotas
    2. Notifications on Quota Completion/Overdue


System Architecture
===================


**Data Organization:**
    
    * Program data will be stored on the Android device in an SQL table.
        * If subsequent applications are created (web/linux/etc...) database 
        will be kept in sync using wifi networks. _A paid online solution will
        be considered_.
    
    * Database will be accessed through a uniform API across devices. 
        * Sync: Sync DB between systems
        * Add/Remove/Edit

**Main Classes and interfaces:**

QuotaModel Class
  * Fields:
      * Id
      * Title
      * Description/Notes
      * Start Time
      * End Time
      * Duration/Length Time
      * Repeats (i.e. Daily, Monthlu, Mon-Thur, etc...)

Database API

UI Classes:
  * Quota UI
  * DB Management UI
  * Daily/Weekly/Monthly Quota Overviews

  * DoughnutChart:
      * Recieves data in constructor or through public method
      * update method.
      * on Selected/deselected methods.

  * QuotaList:
      * Recieves data in constructor or through public method
      * update method.
      * on Selected/deselected methods.


General Overview
================

```
SQLHelper ----> MainActivity -----------> QuotasChart
      \        /            \           \
       \      /              \           \
      EditActivity    SettingsActivity  QuotasList
                                |
                                |
                                V
                            Preferences

```

Using the MVC pattern
=====================

QuotaModel classes will make up with Model.

XML resources, and when more complicated functions are needed, java classes
will make up the View.

Activity classes and separate listeners will make up the controllers.
