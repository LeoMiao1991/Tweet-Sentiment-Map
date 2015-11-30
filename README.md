# Tweet-Sentiment-Map

Tweet Sentiment Map is a web application showing tweets stream on a Google Map in near real time. Users may use time and category filters to see the tweets on some specific topic and time period. We also provide a heatmap showing where is people tweeting the most.

Sentiment Map : http://cloud-computing-hw2-env.elasticbeanstalk.com/SentimentMap.jsp
Heat Map      : http://cloud-computing-hw2-env.elasticbeanstalk.com/HeatMap.jsp

This web application consists three parts: a tweet collector, a tweet processor and a backend servlet. 
The tweet collector uses twitter API to collect the random tweets on 4 topics: music, sports, technology and food. It saves all data in a RDS database on AWS. Also, it sends SQS messages to inform tweet processor.

The tweet processor receive the message from SQS and invoke a Alchemy Sentiment API to get the sentiment for each tweet. Then the processor will send update message to the backend servlet by SNS publish.

The backend server handles the HTTP request from client. It subscribes topic on SNS and keep receiving sentiment update information.

# Team member

Zhilei Miao (zm2221)
Junkai Yan  (jy2654)

# Backend

1. Java web servlet on Tomcat server.
2. Implemented with RESTful API POST and GET.
3. Using a MySQL database on AWS RDS to store and manage tweet information.
4. Using JDBC to access to the MySQL database.
5. Subscribed to a SNS topic and get updated information in near real-time.

# Frontend

Our web-page provides TWO selection dropdown lists for users: (1) Category: There are five items in the category dropdown list: All, sports, music, tech and food. You could select any one of them and the map would show corresponding markers.

1. Period: Similar to category, you could select markers in the past 1/5/30/60 min period. What’s more, our map will automatically add/remove points which are in/out of current period every 3 seconds.

2. In addition, you could refer to # Records to know how many markers are currently showed in our TwittMap. When starting the program, it shows “Initializing” instead.

A heat map is provided to show where is people tweeting the most.

# Deployment

1. We created an Ubuntu 64-bit ec2 instance.
2. We created an elastic beanstalk application.
3. With load balancing, we upload the war file of the backend servlet and deployed it using elastic beanstalk.
4. Tweet Collector and Tweet Processor are also deployed to AWS EC2 instance.
