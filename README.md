# Customer
# REST services for Customer 

This project is to handle the below operstions of a customer using REST API and uses JSON for data transfer.
  1. Add a Customer
  2. Remove a Customer, given their ID
  3. List all Customers
Where a Customer has the following attributes:
  • Id
  • Firstname [mandatory]
  • Surname [mandatory]
  
  
  This project requires JDK8 and it uses spring boot and Guava Loading Cache to persist the data for some duration, instead of database.
  
Steps to works on this project:
  1. Clone the git repo in eclipe.
  2. Execute the CustomerApplication by selecting it and Run as Java Application.
  3. In windows, you can use the following command to execute the program.

# Add a Customer
curl -H "Content-Type: application/json" -X POST -d {\"firstName\":\"mkyong\",\"lastName\":\"abc\"} http://localhost:8080/api/customers -i

# Response:
HTTP/1.1 201
Content-Length: 0
Date: Wed, 19 Sep 2018 06:31:38 GMT

# Add a Customer with Invalid Data[Lastname is not provided]
curl -H "Content-Type: application/json" -X POST -d {\"firstName\":\"mkyong\"} http://localhost:8080/api/customers -i

# Response:
HTTP/1.1 409
Content-Length: 0
Date: Wed, 19 Sep 2018 06:31:59 GMT

# List all the Customers
curl http://localhost:8080/api/customers -i

# Response:
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 19 Sep 2018 06:33:16 GMT

[{"id":2127770536,"firstName":"mkyong","lastName":"abc"}]

# Delete a customer by a given id
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/customers/{id} -i
#In the url, the path param {id} should be replaced by the id generated from the GET REST API call


# Response:
HTTP/1.1 204
Date: Wed, 19 Sep 2018 06:33:53 GMT

# Delete a customer with a id, which does not exist
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/api/customers/{id} -i
#In the url, the path param {id} should be replaced by the id generated from the GET REST API call

# Response:
HTTP/1.1 404
Content-Length: 0
Date: Wed, 19 Sep 2018 06:34:03 GMT


