
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
    
    1. Create quotas
    2. Edit quotas
    3. Delete quotas
    4. List all quotas
    5. Organize quotas


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

    * Quota Class

    * Database API

    * UI Classes:
        * Quota UI
        * DB Management UI
        * Daily/Weekly/Monthly Quota Overviews

        * PieChart/DoughnutChart:
            * Uses Database API to update chart on homepage.
            * update method.
            * on Selected/deselected methods.

        * QuotaList:
            * Uses Database API to update quota list on homepage.
            * update method.
            * on Selected/deselected methods.

