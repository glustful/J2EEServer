<div class="header navbar navbar-static-top" role="banner">
  <div class="container bd-content">
    <div class="navbar-header" style="margin-left:15px;">
      <a href="/" class="navbar-brand"></a>
    </div>

    <div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
      <ul class="nav navbar-nav" style="margin-left:25px;">
        <li><a <#if currentMenu?? && currentMenu == "home">class="active"</#if> href="/">首页</a></li>
        <li><a <#if currentMenu?? && currentMenu == "client">class="active"</#if> href="/site/client">客户端下载</a></li>
        <li><a <#if currentMenu?? && currentMenu == "about">class="active"</#if> href="/site/about">关于我们</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right" style="margin-right:0;">
      	<#if currentUser??>
      		<li class="user">
	          <a href="/order/orderlist/10" class="avatar <#if currentMenu?? && currentMenu == "user">active</#if>">
	            <img src="/logo${(currentUser.logo)!}"> ${(currentUser.nickname)!}
	          </a>
	        </li>
	        <li class="user"><span class="split"></span></li>
	        <li class="user">
	          <a class="cart-box <#if currentMenu?? && currentMenu == "cart">active<#else><#if cartCount?? && cartCount gt 0>active</#if></#if>" href="/cart" style="padding-top: 28px;"><i class="glyphicon glyphicon-shopping-cart" style="font-size: 25px;vertical-align: middle;"></i><span class="cart-count"><#if cartCount?? && cartCount gt 0>${cartCount!}</#if></span></a>
	        </li>
	        <li class="user"><span class="split"></span></li>
	        <li class="user">
	          <a href="${serverPath}/login/logoff">注销</a>
	        </li>
      	<#else>
      		<li class="login"><a href="#loginModal" data-toggle="modal">登录</a></li>
       		<li class="login"><span class="split">/</span></li>
        	<li class="login"><a href="#signupModal" data-toggle="modal">注册</a></li>
      	</#if>
      </ul>
    </div>
  </div>
</div>