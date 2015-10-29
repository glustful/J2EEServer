<#if addressList??>
	<#list addressList as ad>
	<div class="row address-list <#if (ad_index==0)>active</#if>">
    <div class="col col-md-1" style="width: 58px;padding-right: 15px;">
      <input type="radio" data-id="${(ad.id)!}" class="address-select pull-right" name="address_check" <#if (ad_index==0)>checked</#if>>
    </div>
    <div class="col col-md-9">
      <p class="name-mobile">
        <span>收货人：${ad.name}</span>
        <span>电话：${ad.mobile}</span>
      </p>
      <p class="address">
        <span>送餐地址：${ad.address}</span>
        <#if ad.default?? && ad.default><span class="default-flag">【默认】</span></#if>
      </p>
    </div>
    <div class="col col-md-2 action">
      <#if ad.default?? && ad.default == false>
      <a href="javascript:void(0);" class="btn btn-default set-default-address j-address-setdefault" data-id="${(ad.id)!}">设置为默认</a>
      </#if>
      <a href="javascript:void(0);" class="btn delete-address j-address-remove" data-id="${(ad.id)!}">删除</a>
    </div>
  </div>
  </#list>
</#if>