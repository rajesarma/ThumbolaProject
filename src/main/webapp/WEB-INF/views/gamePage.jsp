<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Game</title>

	<script>
		function stop() {
			var form = document.getElementById('gameForm');
			form.action = "/game/stop";
			form.submit();
		}

		function newGame() {
			var form = document.getElementById('gameForm');
			form.action = "/game/newgame";
			form.submit();
		}

		function pause() {
			// clearInterval(myVar);
			// document.getElementById('next_num').focus();
			var form = document.getElementById('gameForm');
			form.action = "/game/pause";
			form.submit();
		}

		function start() {
			var form = document.getElementById('gameForm');
			form.action = "/game/start";
			form.submit();
		}


		if(typeof(EventSource) !== "undefined") {
			var source = new EventSource("http://192.168.34.45:8080/game/start");
			source.onmessage = function(event) {
				// document.getElementById("result").innerHTML += event.data + "<br>";
				document.getElementById("result").innerHTML = event.data;
			};
		} else {
			document.getElementById("result").innerHTML = "Sorry, your browser does not support server-sent events...";
		}

		/*function go() {

			var sse = new EventSource('http://localhost:8080/stream-sse-mvc');
			sse.onmessage = function (evt) {
				document.getElementById("result").innerHTML = evt.data;
			};
		}*/
	</script>

</head>
<body>

	<%--<%
		response.setIntHeader("Refresh", 3);
	%>--%>

	<%--<form:form action="/game" method="GET" id="gameForm">--%>
	<form:form action="/game" method="GET" id="gameForm">

	<table style="font-size: 10pt; -moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius: 6px;border: 1px solid blue;box-shadow: 10px 10px 5px #888888;">
		<tr>
			<td colspan="5" align="center" id="header">
				<font size="5" color="white"><b>BOARD </b></font>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" name="Reset" value="STOP" onClick="stop();"
					   style="height: 40px;width: 100px;"/>
			</td>
			<td align="center">
				<input type="button" name="Reset" value="NEW GAME" onClick="newGame();"
					   style="height: 40px;width: 100px;"/>
			</td>
			<td  align="center" >
				<input type="button" name="pause" value="PAUSE" onClick="pause();" style="height: 50px;width: 100px;" id="pause"/>
			</td>
			<td  align="center" >
				<input type="button" name="next" value="NEXT NUMBER" onClick="start();" style="height: 50px;"
					   id="next_num"/>
			</td>

			<%--<td  align="center" >
				<input type="button" name="pause" value="Get Data" onClick="go();" style="height: 50px;width: 100px;"
					   id="pause"/>
			</td>--%>
		</tr>
	</table>

	<div id = "result"></div>
	<div id = "sse"></div>

		<%--${data}--%>

	</form:form>



</body>
</html>