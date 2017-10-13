var pageSize=10;
Vue.component('mypage', {
	props:['url'],
	template: 
	'<div class="pagination">\
		<ul>\
		<li><a href="javascript:;" v-on:click="prev">上一页</a></li>\
			<li>\
				<span >当前第{{pageNo}}/{{totalPage}}页</span>\
			</li>\
			<li><span >共{{totalSize}}条记录</span></li>\
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
	},
	methods:{
		queryTotalAndList:function(param){
			param=param||{};
			var that=this;
			//服务端定义一个url+"Size"查询总数交易
			post2SRV(this.url+"Size",param,function(data){
				that.totalSize=data;
				if(that.totalSize%pageSize==0){
					that.totalPage=that.totalSize/pageSize;
				}else{
					that.totalPage=parseInt(that.totalSize/pageSize)+1;
				}
			},"json");
			this.queryList(param);
		},
		queryList:function(param,input){
			var that=this;
			//新增页码，默认第一页
			input=input||1;
			param.pageNo=input;
			post2SRV(this.url,param,function(data){
				//调用父组件的list对象
				that.$parent.list=data;
				that.pageNo=input;
			},"json");
		},
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
			var param={};
			//调用父组件的getparam方法
			if(this.$parent.getParam!=null){
				param=this.$parent.getParam();
			}
			this.queryList(param,input);
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