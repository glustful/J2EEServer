<#include "/common/base.ftl">


<@base title="已取消订单">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container">
    <div style="border-bottom:1px solid #e0e0d7;">
      <div class="sidebar-nav">
        <a href="/user/order/unpaid">未付款 <span>1</span></a>
        <a href="/user/order/paided">已付款</a>
        <a class="active" href="/user/order/cancel">已取消</a>
      </div>
    </div>

    <div class="order-list">
      <div class="order-item">
        <p class="num">订单号：965350347131938</p>
        <div class="cart-item row">
          <div class="col col-md-12 item-list">
            <div class="row">
              <div class="col col-md-6">
                <div class="row">
                  <div class="col-md-4" style="width: 130px;">
                    <img class="cover" src="/jjbstatic/img/demo.jpg">
                  </div>
                  <div class="col-md-8">
                    <p class="name">A套餐</p>
                    <p class="foods">红烧鱼 + 荷塘小炒 + 香烤小排</p>
                    <p class="price">单价：9.00 元</p>
                  </div>
                </div>
              </div>

              <div class="col col-md-3 action">
                <p class="lb">数量</p>
                <p class="cnt">x2</p>
              </div>

              <div class="col col-md-3 price-block">
                <p class="lb">价格</p>
                <p class="item-price">9.00 元</p>
              </div>
            </div>


            <div class="row">
              <div class="col col-md-6">
                <div class="row">
                  <div class="col-md-4" style="width: 130px;">
                    <img class="cover" src="/jjbstatic/img/demo.jpg">
                  </div>
                  <div class="col-md-8">
                    <p class="name">A套餐</p>
                    <p class="foods">红烧鱼 + 荷塘小炒 + 香烤小排</p>
                    <p class="price">单价：9.00 元</p>
                  </div>
                </div>
              </div>

              <div class="col col-md-3 action">
                <p class="lb">数量</p>
                <p class="cnt">x2</p>
              </div>

              <div class="col col-md-3 price-block">
                <p class="lb">价格</p>
                <p class="item-price">9.00 元</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</@base>