<#include "/common/base.ftl">
<#if order??>
	        <#if order.status == '10'>
	            <#assign titleName="下单成功">
	        </#if>
	        <#if order.status == '20'>
	            <#assign titleName="支付成功">
	        </#if>
</#if>
<@base title=titleName>
  <div class="container bd-content cart-content main-container" style="margin-bottom:68px;">

    <div class="row order-msg">
      <div class="col col-md-2 tip">
      	<#if order??>
	    	<#if order.status == '20'>
	    	   <img src="/jjbstatic/img/pay-ok.png">
	    	<#else>
	    	   <#if order.status == '10'>
	    	   		<img src="/jjbstatic/img/order-ok.png">
	    	   </#if>
        	</#if>
       	</#if>
      </div>

      <div class="col col-md-6">
        <#if order??>
	        <#if order.status == '10' || order.status == '20'>
	        	<#if order.status == '10'>
	        		<p class="title">${title!}</p>
	            <#else>
	                <p class="title">订单已成功支付</p>
	            </#if>
	            <#if order.status == '10'>
	                <p class="num">订单编号：${(order.orderNo)!}</p>
	            <#else>
	                <p style="font-size:16px;" class="num">订单编号：${(order.orderNo)!}</p>
	                <p style="color:#666666;font-size:14px;">${(order.orderTip)!}</p>
	            </#if>
	        <#else>
	        </#if>
	    <#else>
	        <p class="title">订单不存在!</p>
	    </#if>
      </div>

      <#if order?? && order.status == '10'>
	      <div class="col col-md-4" style="line-height:86px;">
	           <a id="j-pay-now" target="_blank" href="${serverPath}/pay/walletdo?orderNo=${order.orderNo}"><span class="btn btn-primary btn-lg submit-order">立即付款</span></a>
	      </div>
      </#if>
    </div>

    <#include "static_address.ftl">
    <div style="margin-top:8px;">
      <#include "order_detail.ftl">
    </div>
    <div class="row" style="font-size:16px;margin-top:20px;">
      <div class="col-md-8" style="text-align:right;"></div>

      <div class="col-md-4" style="text-align:right;color:#ff4000;">
        <p>共 <span class="total-count">${order.count!}</span> 份 / 合计：￥ <span class="total-price">${order.amount?string(",##0.00")!}</span> 元</p>
      </div>
    </div>
  </div>
</@base>


<div class="modal fade ls-modal" id="payModal" tabindex="-1" role="dialog" aria-labelledby="payModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-dialog" style="width:328px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">订单支付</h4>
      </div>
      <div class="modal-body">
        <div class="row" style="text-align:center;">
          <div class="col-md-6">
            <button type="button" class="btn btn-default btn-danger" onclick="javascript:window.location=window.location.href">完成支付</a>
          </div>
          <div class="col-md-6">
            <a target="_blank" href="https://cshall.alipay.com/lab/help_detail.htm?help_id=238671&keyword=%25CA%25D5%25D2%25F8%25CC%25A8" class="btn btn-faq">遇到问题</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>