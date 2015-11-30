# Tweet-Sentiment-Map

Tweet Sentiment Map is a web application showing tweets stream on a Google Map in near real time. Users may use time and category filters to see the tweets on some specific topic and time period. We also provide a heatmap showing where is people tweeting the most.

Sentiment Map : http://cloud-computing-hw2-env.elasticbeanstalk.com/SentimentMap.jsp
Heat Map      : http://cloud-computing-hw2-env.elasticbeanstalk.com/HeatMap.jsp

This web application consists three parts: a tweet collector, a tweet processor and a backend servlet. 
The tweet collector uses twitter API to collect the random tweets on 4 topics: music, sports, technology and food. It saves all data in a RDS database on AWS. Also, it sends SQS messages to inform tweet processor.

The tweet processor receive the message from SQS and invoke a Alchemy Sentiment API to get the sentiment for each tweet. Then the processor will send update message to the backend servlet by SNS publish.

The backend server handles the HTTP request from client. It subscribes topic on SNS and keep receiving sentiment update information.

# Backend

# Frontend

# Deployment
