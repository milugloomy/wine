Vue.component('mymenu', {
  props: {
    menu1: {
      default: false
    },
    menu2: {
      default: false
    }
  },
  //v-bind:class 中 样式名带-的 需加单引号
  template: '<div>\
		<div class="navbar">\
			<div class="navbar-inner">\
				<ul class="nav pull-right">\
					<li id="fat-menu" class="dropdown">\
						<a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> \
							<i class="icon-user"></i>\
							{{username}}\
							<i class="icon-caret-down"></i>\
						</a>\
						<ul class="dropdown-menu">\
							<li><a href="/wineback/modifyPwd.html">密码修改</a></li>\
							<li><a href="/wineback/adminLog.html">日志查看</a></li>\
							<li class="divider"></li>\
							<li><a href="javascript:;" tabindex="-1" v-on:click="logout">登出</a></li>\
						</ul>\
					</li>\
				</ul>\
				<a class="brand" href="index.html"><span class="second">花道酒业管理后台</span></a>\
			</div>\
		</div>\
		<div class="copyrights">版权信息</div>\
		<div class="sidebar-nav">\
			<a href="#dashboard-menu" class="nav-header" data-toggle="collapse">\
				<i class="icon-dashboard"></i>花道酒业\
			</a>\
			<ul id="dashboard-menu" class="nav nav-list collapse" v-bind:class="{in:menu1}">\
				<li><a href="/wineback/index.html">主页</a></li>\
				<li><a href="/wineback/productList.html">商品管理</a></li>\
				<li><a href="/wineback/orderList.html">订单管理</a></li>\
				<li><a href="#">批量程序管理</a></li>\
			</ul>\
			<a href="#accounts-menu" class="nav-header" data-toggle="collapse">\
				<i class="icon-briefcase"></i>用户管理\
			</a>\
			<ul id="accounts-menu" class="nav nav-list collapse" v-bind:class="{in:menu2}">\
				<li><a href="/wineback/userList.html">关注用户</a></li>\
			</ul>\
		</div>\
	</div>\
	',
  mounted: function() {
    var that = this;
    post2SRV("/wineback/manager", function(data) {
      that.username = data.username;
    }, "json");
  },
  methods: {
    logout: function() {
      post2SRV("/wineback/logout", function(data) {
        window.location.href = "/wineback/index.html"
      }, "json");
    }
  },
  data: function() {
    return {
      username: ""
    }
  }
});
var nav = new Vue({
  el: "#menu"
});

Vue.component('mynav', {
  props: ["name"],
  template: '<div>\
			<div class="header">\
				<div class="stats">\
					<p class="stat">\
						<span class="number" >{{day}}</span>日\
					</p>\
					<p class="stat">\
						<span class="number" >{{month}}</span>月\
					</p>\
					<p class="stat">\
						<span class="number" >{{year}}</span>年\
					</p>\
				</div>\
				<h1 class="page-title">{{name}}</h1>\
			</div>\
			<ul class="breadcrumb">\
				<li><a href="/wineback/index.html">花道酒业</a> <span class="divider">/</span></li>\
				<li class="active">{{name}}</li>\
			</ul>\
		<div>',
  mounted: function() {
    var date = new Date();
    this.year = date.getFullYear();
    this.month = date.getMonth() + 1;
    this.day = date.getDate();
  },
  data: function() {
    return {
      year: "",
      month: "",
      day: ""
    }
  }
});

Vue.component('myfooter', {
  template: '<footer>\
			<hr>\
			<p class="pull-right">版权信息</p>\
			<p>\
				&copy; 2017 <a href="#" target="_blank">版权信息</a>\
			</p>\
		</footer>'
});