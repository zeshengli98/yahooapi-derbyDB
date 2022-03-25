# yahooapi-derbyDB

first use YahooFinanceAPI package to build basic extraction function.
In the YahooStockAPI class, I built a getStock function to identify the stock information
For getHistory method, it can extract the n-year daily, monthly or weekly historical data for any stocks.
The parameter is symbol, years of data we want to fetch, and data type.

The second step is to build a derby database to store the historical data.

Steps as follows:

 - Create a libs directory to add derby.jar, derbytools.jar, and derbyshared.jar files
 - Add the lib directory in the project structure-->dependencies-->add Jars or directories to set the environment
 - run initDB to initialize the derby database. In this case, We name it as bigbucks.
 - fetch the stock data by using getHistory method and insert in the database.