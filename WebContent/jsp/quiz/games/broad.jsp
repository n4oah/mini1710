<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>그림공유</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#canvas1 , #canvas2{
	border: solid 1px greenyellow;
	cursor: crosshair;
	float: left;
	position: absolute;
	
}

.center {
	
}

#colors {
	margin: 0;
	padding: 0;
	margin-left: 1000px;
	display: inline;
	visibility: hidden;
}

#colors li {
	height: 20px;
	width: 20px;
	display: block;
	cursor: pointer;
	margin-left: 1000px;
}

#colors li.selected {
	height: 18px;
	width: 18px;
	display: block;
	border: solid 1px #eee;
	margin-left: 1000px;
}

label {
	float: left;
}
</style>
<!--[if IE]>
<script src="excanvas.js"></script>
<![endif]-->
<script src="http://reali.kr/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	//Including a white background
	var Scolor, cntxt;
	var canvas;
	var start;
	function canvasInit() {
		context = document.getElementById("canvas1").getContext("2d");
		context.canvas.width = 1000;
		context.canvas.height = 500;
		context.lineCap = "round";
		//Fill it with white background
		context.save();
		context.fillStyle = '#fff';
		context.fillRect(0, 0, context.canvas.width, context.canvas.height);
		context.restore();
		context = document.getElementById("canvas2").getContext("2d");
		context.canvas.width = 1000;
		context.canvas.height = 500;
		context.lineCap = "round";
		//Fill it with white background
		context.save();
		context.fillStyle = '#fff';
		context.fillRect(0, 0, context.canvas.width, context.canvas.height);
		context.restore();

		if (start == 1)
			send(0, 0, 0, 'c', 0);
	}
	$(function() {
		var draw, draw = 0, top, left;
		//Get the canvas element
		//var canvas = $("#canvas1");
		start = 0;
		canvas = document.getElementById("canvas1");
		cntxt = canvas.getContext("2d");
		top = $('#canvas1').offset().top;
		left = $('#canvas1').offset().left;
		canvasInit();
		//Drawing Code
		$('#canvas1').mousedown(
				function(e) {
					if (e.button == 0) {
						draw = 1;
						cntxt.beginPath();
						cntxt.moveTo(e.pageX - left, e.pageY - top);
						send(e.pageX - left + 1, e.pageY - top + 1, Scolor,
								'd', cntxt.lineWidth);
					} else {
						draw = 0;
					}
				}).mouseup(
				function(e) {
					if (e.button != 0) {
						draw = 1;
					} else {
						draw = 0;
						cntxt.lineTo(e.pageX - left + 1, e.pageY - top + 1);
						cntxt.stroke();
						cntxt.closePath();
						send(e.pageX - left + 1, e.pageY - top + 1, Scolor,
								'u', cntxt.lineWidth);
					}
				}).mousemove(
				function(e) {
					if (draw == 1) {
						cntxt.lineTo(e.pageX - left + 1, e.pageY - top + 1);
						cntxt.stroke();
						console.log(e.pageX - left + 1);
						console.log(e.pageY - top + 1);
						send(e.pageX - left + 1, e.pageY - top + 1, Scolor,
								'v', cntxt.lineWidth);
					}
				});
		$('#clear').click(function(e) {
			e.preventDefault();
			canvas.width = canvas.width;
			canvas.height = canvas.height;
			canvasInit();
			$('#colors li:first').click();
			$('#brush_size').change();
		});
		$('#brush_size').change(function(e) {
			cntxt.lineWidth = $(this).val();
		});
		$('#colors li').click(function(e) {
			e.preventDefault();
			$('#colors li').removeClass('selected');
			$(this).addClass('selected');
			cntxt.strokeStyle = $(this).css('background-color');
			Scolor = $(this).css('background-color');
		});
		//Init the brush and color
		$('#colors li:first').click();
		$('#brush_size').change();
		start = 1;
	});
</script>
<script type="text/javascript">
	//ws://localhost:3500/AjaxTest/broadcasting
	//var webSocket = new WebSocket('ws://dndyd1256.cafe24.com/AjaxTest/broadcasting');
    var id= '<%= session.getId() %>';
	var webSocket = new WebSocket(
			'ws://192.168.0.145:8080/miniproj1710/sockets/Paint?id=' + id);
	webSocket.onerror = function(event) {
		onError(event)
	};
	webSocket.onopen = function(event) {
		canvas = document.getElementById("canvas2");
		cntxt = canvas.getContext("2d");
		onOpen(event)
	};
	webSocket.onmessage = function(event) {
		onMessage(event)
	};
	function onMessage(event) {
		var e = JSON.parse(event.data);
		console.log(e);
		cntxt.strokeStyle = e.color;
		if(e.state == 'master'){
			var canvas2 = document.getElementById("canvas2");
			canvas2.style.width = '0px';
			canvas2.style.height = '0px';
			canvas = document.getElementById("canvas1");
			cntxt = canvas.getContext("2d");
		}
		if (e.state == 'v') {
			cntxt.lineWidth = e.size;
			cntxt.lineTo(e.pageX, e.pageY);
			cntxt.stroke();
		} else if (e.state == 'u') {
			cntxt.lineWidth = e.size;
			draw = 0;
			cntxt.lineTo(e.pageX, e.pageY);
			cntxt.stroke();
			cntxt.closePath();

		} else if (e.state == 'd') {
			cntxt.lineWidth = e.size;
			cntxt.beginPath();
			cntxt.moveTo(e.pageX, e.pageY);
		} else if (e.state == 'c') {
			cntxt.lineCap = "round";
			//Fil it with white background
			cntxt.save();
			cntxt.fillStyle = '#fff';
			cntxt.fillRect(0, 0, context.canvas.width, context.canvas.height);
			cntxt.restore();
		}
	}
	function onOpen(event) {
		document.getElementById('state').innerText += " 연결 성공";
	}
	function onError(event) {
		document.getElementById('state').innerText += event.data;
	}

	function send(x, y, Scolor, state, size) {
		var arr = {};
		arr.pageX = x;
		arr.pageY = y;
		arr.color = Scolor;
		arr.state = state;
		arr.size = size;
		var str = JSON.stringify(arr);

		if (webSocket != null)
			webSocket.send(str);
	}
</script>
</head>
<body>
	<div class="center">
		<h1 id="state">연결 상태 :</h1>
		<label for="brush">Brush Size:</label> <input name="brush"
			id="brush_size" type="range" value="5" min="0" max="100" /> <br
			style="clear: both;" />
		<button id="clear" href="#">Reset</button>
		<div>
			<canvas id="canvas1"
				style="width: 1000px; height: 500px; "></canvas>
			<canvas id="canvas2"
				style="width: 1000px; height: 500px; "></canvas>
		</div>

		<div style="display: inline;">
			<div style="display: inline;">
				<ul id="colors">
					<li style="background-color: black;"></li>
					<li style="background-color: red;"></li>
					<li style="background-color: green;"></li>
					<li style="background-color: orange;"></li>
					<li style="background-color: brown;"></li>
					<li style="background-color: #d2232a;"></li>
					<li style="background-color: #fcb017;"></li>
					<li style="background-color: #fff460;"></li>
					<li style="background-color: #9ecc3b;"></li>
					<li style="background-color: #fcb017;"></li>
					<li style="background-color: #fff460;"></li>
					<li style="background-color: #F43059;"></li>
					<li style="background-color: #82B82C;"></li>
					<li style="background-color: #0099FF;"></li>
					<li style="background-color: #ff00ff;"></li>
				</ul>
			</div>
		</div>
		<br />
	</div>
</body>
</html>