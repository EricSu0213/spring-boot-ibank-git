/**
 * 
 */
$(function(){
	console.log('hello2');
	
	$("#deleteAccountBtn").click(function() {
		if(confirm("確定要刪除帳號嗎?")){
			$.ajax({
				url : '/deleteAccount',// 跳轉到 action
				data : {},
				type : 'get',
				cache : false,
				dataType : 'json',
				success : function(reponse) {
					if (reponse) {
						alert("成功刪除！");
						window.location.replace("/logout");
					} else {
						view(reponse);
					}
				},
				error : function() {
					alert("異常！");
				}
			});
		}
	});
	
})