<#include "/common/base.ftl">

<@base title="付款成功">
  <div class="container bd-content cart-content main-container" style="margin-bottom:68px;">

    <div class="row order-msg">
      <div class="col col-md-2 tip">
        <img src="/jjbstatic/img/pay-ok.png">
      </div>

      <div class="col-md-10">
        <p class="title">恭喜，您已订餐成功</p>
        <p class="num">我们将在今天12:00后为您配送餐点，请耐心等待，预计10分钟送到。</p>
      </div>
    </div>

    <#include "static_address.ftl">
    <div style="margin-top:8px;">
      <div class="cart-item row j-cart-item" id="cart_${(cart.id)!}" data-id="${(cart.id)!}">
        <div class="col col-md-6">
          <div class="row">
            <div class="col-md-4" style="width: 130px;">
              <img class="cover" src="/jjbstatic/img/demo.jpg">
            </div>
            <div class="col-md-8">
              <p class="name">A套餐</p>
              <p class="foods">fsdfsdfsdfsd</p>
              <p class="price">单价：9.00 元</p>
            </div>
          </div>
        </div>

        <div class="col col-md-3 action">
          <p class="lb">数量</p>
          <div>
            <span class="count j-count">${(cart.count)!}</span>
          </div>
        </div>

        <div class="col col-md-3 price-block">
          <p class="lb">价格</p>
          <p class="item-price">
            <span class="ip">${(cart.cartPrice?string(",###.00"))!}</span>
            <span>元</span>
          </p>
        </div>
      </div>
    </div>
    <div class="row" style="font-size:16px;margin-top:20px;">
      <div class="col-md-8" style="text-align:right;"></div>

      <div class="col-md-4" style="text-align:right;color:#ff4000;">
        <p>共 <span class="total-count">${cartCount!}</span> 份 / 合计：￥ <span class="total-price">${sum?string(",##0.00")!}</span> 元</p>
      </div>
    </div>
  </div>
</@base>