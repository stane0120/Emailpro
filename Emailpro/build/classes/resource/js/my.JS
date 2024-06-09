let isNull = function(obj, msg) {
	if(obj.value == ''){
		alert(msg)
		obj.focus()
		return false
	}
	return true
}