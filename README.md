# Insert_script


# Insert to database script in Java

One Paragraph of project description goes here

## Getting Started

The following java program will read data from an csv files and do a HTTP request to insert the data into the database. 

### Prerequisites

1. Csv file name must be [DB_TableName]_[Time in Epoch]
2. Csv file first row of data must be the able to map as database column name

```

### Settings  

In order to use the program, you will have to set configuration in config..properties file. 

```
dir=[Directory to read the csv file],[2nd Directory is there is]
#1 Hour
Interval=[hours in days] e.g. 0.041667 -> 1 hour
Day=86400
API=[API_Endpoint]
```

