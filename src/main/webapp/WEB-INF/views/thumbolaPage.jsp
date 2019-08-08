<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Game</title>

	<script>
		var game;

		function loadData () {
			this.source = null;
			this.start = function () {
				this.source = new EventSource("/thumbola/start_game");
				this.source.addEventListener("message", function (event) {
					document.getElementById("result").innerHTML = event.data;
				});

				this.source.onerror = function () {
					this.close();
				};
			};

			this.stop = function() {
				this.source.close();
			}
		}

		game = new loadData();

		window.onload = function() {
			game.start();
		};

		window.onbeforeunload = function() {
			game.stop();
		}

		/*function pauseData() {
			game.stop();
		}

		function continueData() {
			game.start();
		}*/

		/*function newGame() {
			game.start();
		}*/

		function newGame() {
			var form = document.getElementById('gameForm');
			form.action = "/thumbola/new_game";
			form.submit();
		}

		function pauseGame() {
			var form = document.getElementById('gameForm');
			form.action = "/thumbola/pause_game";
			form.submit();
		}

		function continueGame() {
			var form = document.getElementById('gameForm');
			form.action = "/thumbola/continue_game";
			form.submit();
		}

	</script>

</head>
<body>

	<form:form action="/thumbola" method="GET" id="gameForm">

	<table style="font-size: 10pt; -moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius: 6px;border: 1px solid blue;box-shadow: 10px 10px 5px #888888;">
		<tr>
			<td colspan="5" align="center" id="header">
				<font size="5" color="white"><b>BOARD </b></font>
			</td>
		</tr>
		<tr>

			<td align="center">
				<input type="button" name="Reset" value="New Game" onClick="newGame();"
					   style="height: 20px;width: 100px;"/>
			</td>

			<td  align="center" >
				<input type="button" name="pause" value="Pause" onClick="pauseGame();"
					   style="height: 20px;width: 100px;" id="pause"/>
			</td>
			<td  align="center" >
				<input type="button" name="next" value="Continue" onClick="continueGame();" style="height: 20px;width: 100px;"
					   id="next_num"/>
			</td>

			<td align="center">
				<input type="button" name="Reset" value="Stop" onClick="stopGame();"
					   style="height: 20px;width: 100px;"/>
			</td>



			<%--<td  align="center" >
				<input type="button" name="pause" value="Get Data" onClick="go();" style="height: 50px;width: 100px;"
					   id="pause"/>
			</td>--%>
		</tr>
	</table>

	<div id = "result">${gameData}</div>
	<div id = "sse"></div>

	</form:form>



</body>
</html>