<div id="address-container" class="row" style="padding-bottom: 15px;border-bottom:1px solid #e3e3d9;">
  <div class="col col-md-1"><i class="icon-address"></i></div>
  <div class="col col-md-7 cart-address">
    <p class="name">收货人：${(defaultAddress.name)!} | 电话：${(defaultAddress.mobile)!}</p>
    <p class="address">送餐地址：${(defaultAddress.address)!}</p>
  </div>
  <div class="col col-md-3">
    <a class="change-address" href="javascript:void(0);">更改送货地址 <i style="margin-left:10px;top:2px;" class="glyphicon glyphicon-menu-right"></i></a>
  </div>
  <input type="hidden" id="addressId" value="${(defaultAddress.id)!}">
</div>