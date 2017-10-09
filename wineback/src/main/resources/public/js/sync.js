function post2SRV(callUrl,formData,callback,dataType){
	var loadLayer=layer.load(1,{shade: [0.5,'#fff']});
	if(typeof dataType=='undefined'){
		dataType=callback;
		callback=formData;
		formData=null;
	}
	$.ajax({
		url:callUrl,
		data:formData,    
		type:'post',
		async:true,
		dataType:dataType,    
		success:function(data, status, xhr){
			layer.close(loadLayer);
			//正常返回
			if(data.retcode=="0000"){
				data=data.body;
				callback(data);
			}else{
				//session超时，重定向
				if(data.retcode=="302"){
					window.location.href=data.body;
				//服务端报错
				}else{
					layer.alert(data.errMsg);
				}
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loadLayer);
			layer.alert("网络忙，请稍后再试！");
		} 
    });
}
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) 
		return unescape(r[2]); 
	return null; 
} 
function getCookie(name) { 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
}
function formatDate(date){
	//replace(/****/g,"-")等于replaceAll
	var dateStr=date.toLocaleDateString().replace(/\//g,"-");
	var arr=dateStr.split("-");
	if(arr[1].length<2){
		arr[1]="0"+arr[1]
	}
	if(arr[2].length<2){
		arr[2]="0"+arr[2]
	}
	return arr[0]+"-"+arr[1]+"-"+arr[2];
}