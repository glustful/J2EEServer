<#include "/common/base.ftl">


<@base title="未付款订单">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container">
    <div style="border-bottom:1px solid #e0e0d7;">
      <div class="sidebar-nav">
        <a <#if status=='10'>class="active"</#if> href="/order/orderlist/10">未付款 <span>${(unpaid)!}</span></a>
        <a <#if status=='70'>class="active"</#if> href="/order/orderlist/70">已付款</a>
        <a <#if status=='50'>class="active"</#if> href="/order/orderlist/50">已取消</a>
      </div>
    </div>

    <div class="order-list">
      <#include "order-item.ftl">
    </div>
  </div>
</@base>