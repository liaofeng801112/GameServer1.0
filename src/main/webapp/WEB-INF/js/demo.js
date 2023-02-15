
onLoad();

function onLoad(){
	$.ajax({
	url:"demo",
	type:"POST",
	success : function(json){
		alert("success");
	}
});
}
