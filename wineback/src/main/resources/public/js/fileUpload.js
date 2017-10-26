//jQuery扩展
(function($){
	var uploadDiv='<div id="progressNumber"></div>';
	var msgLayer;
	var obj;
	$.fn.upload = function(url,callback) {
		obj=this;
		var file=obj[0].files[0];
		if (file) {
			msgLayer=layer.msg(uploadDiv, {
				time: 0, //永远存在
			});
			var fd = new FormData();
			fd.append("file", file);
			var xhr = new XMLHttpRequest();
			xhr.upload.addEventListener("progress", uploadProgress, false);
			xhr.addEventListener("load", uploadComplete.bind(event,callback), false);
			xhr.addEventListener("error", uploadFailed, false);
//			xhr.addEventListener("abort", uploadCanceled, false);
			xhr.open("POST", url);
			xhr.send(fd);
		}
	}
	function uploadProgress(evt) {
		if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
			document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
		}
	}
	//关闭layer，清空input=file
	function uploadComplete(callback,evt) {
		if(msgLayer){ layer.close(msgLayer); }
		if(obj){ obj.val(""); }
		
		var data=JSON.parse(evt.target.responseText);
		if(data.retcode=="0000"){
			callback(data.body);
		}else{
			layer.alert(data.errMsg);
		}
	}
	function uploadFailed(evt) {
		if(msgLayer){ layer.close(msgLayer); }
		layer.alert("上传文件发生了错误,请重新尝试");
	}
}(jQuery));

