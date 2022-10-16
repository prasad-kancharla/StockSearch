# StockSearch
 
 Tech Stack Used: Java 11, Spring Boot, Elastic Search, Spring Data JPA
 
 #### STEP 1: Install Elastic Search using the below link
 https://www.elastic.co/downloads/past-releases/elasticsearch-6-5-1
 
 #### STEP 2: Navigate to Config folder and open elasticsearch.yml. Uncomment 'cluster.name:' property & update it as below
 <code> cluster.name: stocksearch </code>
 
 #### STEP 3: Navigate to bin folder and open terminal at this path. Type the below command and press enter
 <code> elasticsearch.bat </code>
 
 #### STEP 4: Open browser and visit the below url to see the details of elastic search.
 <code> http://localhost:9200/ </code>
 
 Now Run the *Application.java* from IDE
 
 #### (Optional) STEP 5: Open Postman and import postman collection by following the below steps
 <code> File -> Import -> Upload Files -> select stock_search .postman_collection </code>
 
 Please use the TestStocks.xlsx as a sample excel sheet for your reference
 
 


 

