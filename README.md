# EGT Coding Challenge

Create an API in Java using this project as a base.

The project contains a Controller/Service/Repo structure, and “TODO” comments to serve as a guide. The Repository layer is set up to use a Map of objects rather than a database, this layer does not need any changes. “Person” and “Address” are used to serve as entities, one person may have one or many addresses and an address may have one person.

## Challenges:

1. Fill out the Person Controller and Service to accommodate the following endpoints:

   1. GET 	/person 
      1. This endpoint should return all Person records without their Address information and ordered by last name, first name.
   2. POST	/person 
      1. This endpoint should allow a new Person record to be created. 
      2. A Person is required to have a first name, last name, birthday, and main address. 
      3. The new Person record should use the next available id. 
   3. PUT	/person 
      1. This endpoint should allow a Person record to be updated. 
      2. A Person may not be update to have a null/empty main address. 
      3. A Person may not have their birthday updated. 
      4. Update the Address record with the matching id as specified by the payload. 
      5. Attempting to update an id that does not exist should return an error. 
   4. DELETE	/person	/{id} 
      1. This endpoint should allow a Person record to be deleted. 
      2. Deleting a Person should delete all of their Address information as well. 
      3. Attempting to delete an id that does not exist should return an error. 
   5. GET	/person/{id} 
      1. This endpoint should return a single Person record with all of their Address information. 
      2. Attempting to get an id that does not exist should return an error. 
   6. POST	/person/lastName 
      1. This endpoint should return all Person records with a matching last name, as specified in the payload.
2. Fill out the Address Controller and Service to accommodate the following endpoints:
   1. GET 	/address 
      1. This endpoint should return all Address records ordered by street1, state. 
   2. POST	/address
      1. This endpoint should allow a new Address record to be created.
      2. An Address is required to have a person Id, zip, and state.
      3. The new Address record should use the next available id. 
   3. PUT	/address
      1. This endpoint should allow an Address record to be updated. 
      2. An Address may not be update to have a null/empty person Id. 
      3. Update the Address record with the matching id as specified by the payload. 
      4. Attempting to update an id that does not exist should return an error. 
   4. DELETE	/address/{id} 
      1. This endpoint should allow an Address record to be deleted. 
      2. An Address used as a main address should not be able to be deleted. 
      3. Attempting to delete an id that does not exist should return an error. 
   5. GET	/address/{id} 
      1. This endpoint should return a single Address record with the corresponding Person information. 
      2. Attempting to get an id that does not exist should return an error.
3. When a piece of validation fails, then return an http status of 400 and a message stating what caused the error.
4. Add unit tests for all methods added into the Person Service and Address Service to ensure they return expected results/perform the correct actions when called.
