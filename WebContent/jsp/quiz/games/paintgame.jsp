<%@page import="model.MemberVO"%>
<%@page import="websocket.chatting.GameSystem"%>
<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	System.out.println("asokopsdkfosdf");
		MemberVO tmp = (MemberVO) session.getAttribute("user");
    	if(tmp != null)
			GameSystem.inputID(tmp.getId(), session.getId());
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <fieldset>
	    <select id="lstValue" style="width: 300px" multiple>
			
		</select>
    	<br>
        <textarea id="messageWindow" rows="10" cols="50" style="overflow:hidden;overflow-y:auto;" readonly="true"></textarea>
        <br/>
        <input id="inputMessage" type="text" onkeyup="enterkey()"/>
        <input type="submit" value="send" onclick="send()" />
        //!game 입력시 게임시작(최소 세명이상가능)
        <br>
        //!r 정답 입력시 판별가능
        <br>
    </fieldset>
</body>
    <script type="text/javascript">
    	var id= '<%= session.getId() %>';
        var textarea = document.getElementById("messageWindow");
        var webSocket = new WebSocket('ws://192.168.0.145:8080/miniproj1710/sockets/chatting?id=' + id);
        var inputMessage = document.getElementById('inputMessage');
    webSocket.onerror = function(event) {
      onError(event)
    };
    webSocket.onopen = function(event) {
      onOpen(event)
    };
    webSocket.onmessage = function(event) {
      onMessage(event)
    };
    function onMessage(event) {
    	var sc = chatscroll();
		var e = JSON.parse(event.data);
		console.log(e);
		if(e.type == 'msg'){
			textarea.value += e.name + " : " + e.msg + "\n";
		}
		else if(e.type == 'all'){
			textarea.value +=   e.msg + "\n";
		}
		else if(e.type == 'userinfos'){
			for (i = 0; i < e.list.length; i++) {
				addValue(e.list[i].name);
			}
		}
		else if(e.type == 'logout'){
			deleteValue(e.name);
			textarea.value += e.name + " : 로그아웃 하셨습니다.\n";
		}
		else if(e.type == 'login'){
			addValue(e.name);
			textarea.value += e.name + " : 로그인 하셨습니다.\n";
		}
		else if(e.type == 'kick'){
			deleteValue(e.name);
			textarea.value += e.name + " 님이 강퇴되셨습니다.\n";
		}
		else if(e.type == 'game'){
			//deleteValue(e.name);
			textarea.value += "게임에 참여에 대기리스트에 등록하였습니다.\n";
		}
		else if(e.type == 'gamestart'){
			//deleteValue(e.name);
			board();
		}
		else if(e.type == 'gameend'){
			//deleteValue(e.name);
			removeboard();
		}
		if(sc)
			textarea.scrollTop = textarea.scrollHeight;
    }
    function onOpen(event) {
        textarea.value += "연결 성공\n";
    }
    function onError(event) {
    	textarea.value += event.data;
    }
    
    function send() {
    	if(inputMessage.value == '')
    		return;
    	
	    textarea.value += "나 : " + inputMessage.value + "\n";
	    webSocket.send(inputMessage.value);
    	
        inputMessage.value = "";
		textarea.scrollTop = textarea.scrollHeight;
		
    }
    function chatscroll() {
    	return textarea.scrollTop + textarea.clientHeight == textarea.scrollHeight;
	}
    function enterkey() {
        if (window.event.keyCode == 13) {
            send();
        }
    }

    var i = 0;
    function deleteValue(name) {
        var x = document.getElementById("lstValue");
        for(i = 0 ; i < x.length; i++){
        	if(x.options[i].text == name){
        		x.remove(i);
        		break;
        	}
        }
        
        return true;
    }

    function addValue(name) {
        // get the TextBox Value and assign it into the variable
          var x = document.getElementById("lstValue");
          var option = document.createElement("option");
          option.text = name;
          
          x.add(option, x[0]);
          

        return true;
     }
    function removeboard() {
    	var tmp = document.getElementById("ba");
    	document.body.removeChild(tmp);
	}
    function board() {
    	var option = document.createElement("iframe");
    	option.id = 'ba';
        option.style.width = '100%';
        option.style.height = '700px';
		option.src= '${pageContext.request.contextPath}/jsp/quiz/games/broad.jsp';
		document.body.appendChild(option);
	}

  </script>
</html>