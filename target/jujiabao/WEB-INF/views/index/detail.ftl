<div class="item-dc detail-container">
  <div>
    <#if data?? && data.foodDetailList??>
      <#list data.foodDetailList as foodDetail>
        <div class="row">
          <div class="col col-md-6">
            <img class="detail-fc" src="/detail${(foodDetail.logo)!}">
          </div>

          <div class="col col-md-6" style="padding-left:40px;">
            <p class="detail-fn">菜品${foodDetail_index+1}/ ${(foodDetail.name)!}</p>
            <p class="detail-ft">口味：${(foodDetail.taste)!}</p>
            <p class="detail-fr">用料：${(foodDetail.materials)!}</p>
          </div>
        </div>
      </#list>
    </#if>
  </div>
</div>