var pageSize=10;
Vue.component('mypage', {
	props:['url'],
	template: 
	'<div class="pagination">\
		<ul>\
		<li><a href="javascript:;" v-on:click="prev">上一页</a></li>\
			<li>\
				<span id="pageSpan">当前第{{pageNo}}/{{totalPage}}页</span>\
			</li>\
			<li><span id="pageSpan">共{{totalSize}}条记录</span></li>\
			<li><a href="javascript:;" v-on:click="next">下一页</a></li>\
			<li>\
				<span>\
					<input type="number" maxlength="3" v-model="inputPage"\
						style="width: 50px;height:30px;margin: 0;" /> 页\
					<a href="javascript:;" v-on:click="goto()">跳转</a>\
				</span>\
			</li>\
		</ul>\
	</div>',
	mounted:function(){
		var that=this;
		//url+"Size"查询总数交易
		post2SRV(this.url+"Size",function(data){
			that.totalSize=data;
			if(that.totalSize%pageSize==0){
				that.totalPage=that.totalSize/pageSize;
			}else{
				that.totalPage=parseInt(that.totalSize/pageSize)+1;
			}
		},"json");
	},
	methods:{
		prev:function(){
			if(parseInt(this.pageNo)==1){
				alert("当前已是第一页");
				return;
			}
			this.goto(parseInt(this.pageNo)-1);
		},
		next:function(){
			if(parseInt(this.pageNo)==this.totalPage){
				alert("当前已是最后一页");
				return;
			}
			this.goto(parseInt(this.pageNo)+1);
		},
		goto:function(input){
			var that=this;
			if(input==undefined)
				input=this.inputPage;
			if(!/^[0-9]*$/.test(input) || input>this.totalPage || input<1){
				alert("请输入范围内的页码");
				return;
			}
			post2SRV(this.url,{pageNo:input},function(data){
				db.list=data;
				that.pageNo=input;
			},"json");
		}
	},
	data:function(){
		return{
			totalSize:0,//总条数
			totalPage:0,//总页数
			pageNo:1,//当前页码
			inputPage:1//输入要跳转的页码
		}
	}
});