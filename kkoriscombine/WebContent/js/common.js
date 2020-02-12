
// input 타입1
$('.input-container > input').focus(function(){
	$(this).parent().addClass('focus on');
}).focusout(function(){
	if($(this).val() == ""){
		$(this).parent().removeClass('focus');
	} else{
		$(this).parent().removeClass('on');
	}
});

// input radio
$('label.radioLabel').click(function(){
	$(this).prev().prop('checked', true);
});