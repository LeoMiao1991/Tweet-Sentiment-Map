//$(document).ready(function() {
//	(function() {
var map;
var category = "all";
var period = 10;
var server = "http://cloud-computing-hw2-env.elasticbeanstalk.com/BackendServlet";
var count = 0;

var oldMap = {};
var newMap = {};
var iconMap = {
	"positive" : 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
	"negative" : 'http://maps.google.com/mapfiles/ms/icons/red-dot.png',
	"neutral" : 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png',
	"error" : 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',
	"empty" : 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
};

function sendMsg() {
	var data = {
		"message" : period + " " + category
	};
	$.get(server, data, function(resp) {
		// console.log(resp);
		newMap = {};
		if (resp == "no matching record") {
			clearMarkers();
			document.getElementById("status").innerHTML = "No Points";
		} else {
			update(resp);
		}
	}).fail(function() {
		console.log("fail");
	});
}

function clearMarkers() {
	for ( var key in oldMap) {
		oldMap[key].setMap(null);
	}
	oldMap = {};
}

function update(resp) {

	var points = resp.split("\n");
	count = points.length;
	// Add new markers
	for (var i = 0; i < points.length; i++) {
		var point = JSON.parse(points[i]);
		// console.log(point);
		var id = point.id;
		var icon = iconMap[point.sentiment];
		if (id in oldMap) {
			newMap[id] = oldMap[id];
		} else {
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(point.lat, point.lng),
				icon : icon,
				map : map
			});
			newMap[id] = marker;
		}
	}
	document.getElementById("status").innerHTML = count;
	var key;
	for (key in oldMap) {
		if (!(key in newMap)) {
			oldMap[key].setMap(null);
		}
	}
	oldMap = newMap;
}

function initialize() {
	document.getElementById("status").innerHTML = "Initializing...";
	var myLatLng = {
		lat : 0,
		lng : 0
	};
	var mapOptions = {
		zoom : 2,
		center : myLatLng
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	// sendMsg();
}

function categoryChange() {
	category = document.getElementById("Category").value;
	// console.log(category);
	sendMsg();
}

function periodChange() {
	period = document.getElementById("Period").value;
	// console.log(period);
	if (period != "Select...") {
		sendMsg();
	}
}

google.maps.event.addDomListener(window, 'load', initialize);

setInterval(function() {
	sendMsg();
}, 1000 * 3);
// })();
// });

