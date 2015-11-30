# Tweet-Sentiment-Map

Tweet-Sentiment-Map is a web application to show the random recent tweets on a Google Map in near real time. Users may use time and category filters to see the tweets on some specific topic and or time period. We also provide a heatmap showing where is people tweeting the most.

1. Sentiment Map : http://cloud-computing-hw2-env.elasticbeanstalk.com/SentimentMap.jsp
2. Heat Map      : http://cloud-computing-hw2-env.elasticbeanstalk.com/HeatMap.jsp

This web application consists three parts: a tweet collector, a tweet processor and a backend servlet.

The tweet collector uses twitter oAuth streaming API to collect random tweets on 4 topics: music, sports, technology and food. The collector saves all data in a MySQL database on AWS RDS. Also, it sends a message to SQS to inform tweet processor that new tweets are available.

The tweet processor retrieve messages from SQS and invoke a Alchemy Sentiment Analysis API to compute the sentiment evaluation for each tweet. The processor will send update messages to the backend servlet by SNS publishment and subscription.

The backend server handles the HTTP request from client browser. It subscribes a topic on SNS and keep receiving sentiment update information from tweet processor.

# Team member

1. Zhilei Miao (zm2221)
2. Junkai Yan  (jy2654)

# Backend

1. A Java web servlet on Tomcat 8 server.
2. Implemented with RESTful APIs including POST for SNS and GET for client browser.
3. Using a MySQL database on AWS RDS to store and manage tweets information.
4. Using JDBC to access to the MySQL database.
5. Subscribed to a SNS topic and get updated information in near real-time.

# Frontend

Our web-page provides TWO selection dropdown lists for users: (1) Category: There are five items in the category dropdown list: All, sports, music, tech and food. You could select any one of them and the map would show corresponding markers.

1. Period: Similar to category, you could select markers in the past 1/5/30/60 min period. What’s more, our map will automatically add/remove points which are in/out of current period every 3 seconds.

2. In addition, you could refer to # Records to know how many markers are currently showed in our TwittMap. When starting the program, it shows “Initializing” instead.

A heat map is also provided on a different URL to show where is people tweeting the most.

# Deployment

1. We created an Ubuntu 64-bit EC2 instance.
2. We created an elastic beanstalk application with Tomcat 8 server environment.
3. With load balancing, we upload the war file of the backend servlet and deployed it using elastic beanstalk.
4. Tweet Collector and Tweet Processor are also deployed to AWS EC2 instance.
