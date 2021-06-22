$(document).ready(function() {
	const username=$.cookie('username');
	if(username){
		document.getElementById("welcomeMsg").innerHTML = username + "님 환영합니다. <button id='logoutBtn'>로그아웃</button>	<button id='memberDeleteBtn'>회원탈퇴</button>";
	}

	//탈퇴
	$(document).on('click', '#memberDeleteBtn', function() {
		const r = confirm("정말 탈퇴하실 건가요?");
		if (r == true) {
			$.post('main',
				{ sign: "memberDelete" },
				function(data) {
					alert(data);
					location.reload();
				}
			);
		}
	});

	//로그아웃
	$(document).on('click', '#logoutBtn', function() {
		$.post('main',
			{ sign: "logout" },
			function(data) {
				alert(data);
				$.removeCookie('username',{path:'/'});
				location.reload();
			}
		);
	});

});