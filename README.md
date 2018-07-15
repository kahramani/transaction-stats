## About
This is an API which accepts transactions with amount and timestamp. The second part of the API is that it gives you the statistics of transactions which have timestamp within the range of last 60 secs. Calculations have achieved real time which means that statistics endpoint respond requests with O(1) complexity.

## Specs
```POST /transactions```

```GET /statistics```  

Both endpoints have localization support for error messages with request parameter of ```?locale=``` 

## Tech Stack
Tech|Version
---|---
Maven|3.5.0
JDK|1.8
Spring Boot|2.0.0.RELEASE