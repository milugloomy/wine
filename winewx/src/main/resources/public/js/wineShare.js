var Wine={};
Wine.wineShare=function(shareData){
	var debug=shareData.debug||false;
	$.post("wxSign",{url:shareData.link},function(data){
		wx.config({
			debug : debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		 	appId: data.appId, // 必填，公众号的唯一标识
			timestamp: data.timestamp, // 必填，生成签名的时间戳
			nonceStr: data.nonceStr, // 必填，生成签名的随机串
			signature: data.signature,// 必填，签名，见附录1
			jsApiList : [// 必填，需要使用的JS接口列表
				'onMenuShareTimeline', //朋友圈
				'onMenuShareAppMessage', //发给朋友
				'openAddress'//获取收货地址
			]
		});
		wx.ready(function(){
			//  分享接口
			wx.onMenuShareTimeline(shareData);	
			wx.onMenuShareAppMessage(shareData);
		});
	},"json");
}