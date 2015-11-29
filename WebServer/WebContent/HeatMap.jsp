<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>tweetHeatMap</title>
    <style>
      html, body {
        height: 100%;
        margin: 0px;
        padding: 0px
      }

      #panel {
        position: absolute;
        top: 5px;
        left: 50%;
        margin-left: -180px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
      
      #nav {
		    line-height:60px;
		    background-color:#eeeeee;
		    height:600px;
		    width:150px;
		    float:left;
		    padding:8px;	      
	  }
	  
	  #map-canvas {
			height: 600px;
			float: right;
			width: 1110px;
	  }
      
      #header {
		    background-color:black;
		    color:white;
		    text-align:center;
		    padding:5px;
	  }
	  
	  #section {
		    width:350px;
		    float:left;
		    padding:10px;	 	 
		}
    </style>

    <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen">
    <link rel="stylesheet" href="styles/bootstrap.min.css" type="text/css" media="screen">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=visualization"></script>
	<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/js/bootstrap.min.js"></script>
    <script src="js/HeatMap.js"></script>
</head>
<body>

 		<div id="header">
			<h1>TwittHeat Map</h1>
		</div>
		
		<div id="nav">
			
			<div>
				<h4>Period</h4>
			</div>
			<form>
				<select id="Period" onchange="periodChange()">
				  <option value="Select...">Time Range</option>
				  <option value="1">1min</option>
				  <option value="5">5min</option>
				  <option value="30">30min</option>
				  <option value="60">60min</option>
				</select>
			</form>
			
			<div>
				<h4># Records:</h4>
				<p id="status">0</p>
			</div>
		</div>
	 	<div id="map-canvas"></div>
</body>
</html>