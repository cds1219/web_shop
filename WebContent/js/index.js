$(document).ready(function() {

	console.log("index.js ready....ok");

	$(document).on('click', '#memberDeleteBtn', function() {
		const r = confirm("정말 탈퇴하실 건가요?");
		if (r == true) {
			$.post('main',
				{ sign: "memberDelete"},
				function(data) {
					alert(data);
					location.reload();
				 }
			);
		}
	});
	
});