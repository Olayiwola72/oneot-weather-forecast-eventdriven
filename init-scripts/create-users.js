// init-scripts/create-users.js

// Print a message indicating the script has started
print("MongoDB user creation script started.");

// Get environment variables for username, password, database, and roles
var username = process.env.MONGO_INITDB_ROOT_USERNAME;
var password = process.env.MONGO_INITDB_ROOT_PASSWORD;
var database = process.env.MONGO_INITDB_DATABASE;
var roles = process.env.MONGO_DB_ROLES;

// Log the environment variables being used
print(`Using the following configuration:
  Username: ${username}
  Database: ${database}
  Roles: ${roles}`);

// Use the specified database
print(`Switching to database: ${database}`);
db = db.getSiblingDB(database);

// Check if the user already exists
print(`Checking if user '${username}' already exists...`);
var weatherUser = db.getUser(username);

if (!weatherUser) {
  print(`User '${username}' does not exist. Creating user...`);
  try {
    db.createUser({
      user: username,
      pwd: password,
      roles: [
        { role: roles, db: database }  // Assign roles to the user
      ]
    });
    print(`User '${username}' created successfully.`);
  } catch (e) {
    print(`Error creating user '${username}': ${e}`);
  }
} else {
  print(`User '${username}' already exists. No action needed.`);
}

// Indicate the script has finished
print("MongoDB user creation script completed.");
