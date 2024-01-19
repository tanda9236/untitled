let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ //function(){},()=>{} this를 바인딩하기 위해서
			this.save();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
	},
	
	save: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// ajax호출 : default 비동기 호출
		// ajax통신 -> 3개의 데이터 json으로 변경 insert요청 -> 통신성공 -> 서버가 json을 리턴 -> 자동 자바오브젝트 변환
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body data
			contentType: "application/json; charset=utf-8",
			dataType: "json" // 자동으로 자바오브젝트로 변환됨 (생략가능)
		}).done(function(resp){
			if(resp.status === 500){
				alert('회원가입이 실패하였습니다.');
			}else{
				alert('회원가입이 완료되었습니다.');
				location.href = "/";
			}
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	update: function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(), // 세션변경하기위해
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert('회원수정이 완료되었습니다.');
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init(); //btn-save버튼을 클릭 -> this.save호출