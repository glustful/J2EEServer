<#include "/common/base.ftl">

<@base title="购物车">
  <div class="container bd-content cart-content main-container" style="margin-bottom:68px;">
    

    <#include "address.ftl">

    <#include "cart_content.ftl">

    <div style="text-align:right;margin-top: 10px;padding-right: 15px;">
        <span class="btn btn-primary btn-lg submit-order j-submit-order">提交订单</span>
    </div>
  </div>
</@base>