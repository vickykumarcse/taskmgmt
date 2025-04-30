// Switch to 'taskdb' (creates if it doesn't exist)
db = db.getSiblingDB('taskdb');  

// Create the 'tasktable' collection if it doesn't already exist
db.createCollection('tasktable');
