<div id="address-container" class="address-container row" style="padding-bottom: 15px;">
  <div>
    <p style="font-size:26px;margin-bottom:5px;">收货信息
      <span class="j-save-address" style="font-size:14px;cursor:pointer;margin-left:15px;color:#ff4200;">保存信息并返回</span>
    </p>
  </div>

  <div>
    <#include "address_item.ftl">

    <div class="row address-list new-address-list">
      <div class="col col-md-1" style="width: 58px;padding-right: 15px;">
        <input type="radio" data-id="-1" class="new-address pull-right" name="address_check">
      </div>
      <div class="col col-md-10">
        <p style="margin-bottom:0;">使用新地址</p>
        <div class="create-address" style="display:none;">
          <div class="form-horizontal">
            <div class="form-group">
              <label for="" class="col-sm-2 control-label">选择地区</label>
              <div class="col-sm-2">
                <select class="form-control region-select region-select-1" data-index="1">
                  <option value="">请选择</option>
                  <#list regionList as region>
                    <option data-type="20" value="${region.id}">${region.name}</option>
                  </#list>
                </select>
              </div>

              <div class="col-sm-2">
                <select class="form-control region-select region-select-2" data-index="2">
                  <option value="">请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select class="form-control region-select region-select-3" data-index="3">
                  <option value="">请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select class="form-control region-select region-select-4" data-index="4">
                  <option value="">请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select class="form-control region-select region-select-5" data-index="5">
                  <option value="">请选择</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="" class="col-sm-2 control-label">所在楼层</label>
              <div class="col-sm-2">
                <input type="text" class="form-control" name="floor" id="">
              </div>

              <label for="" class="col-sm-2 control-label">详细地址</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="manualAddress" id="">
              </div>
            </div>

            <div class="form-group">
              <label for="" class="col-sm-2 control-label">收货人姓名</label>
              <div class="col-sm-2">
                <input type="text" class="form-control" name="name" id="">
              </div>

              <label for="" class="col-sm-2 control-label">手机号码</label>
              <div class="col-sm-6">
                <input type="text" class="form-control" name="mobile" id="">
              </div>
            </div>

            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                  <label>
                    <input type="checkbox" class="j-status"> 设置为默认收货地址
                  </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-10">
        <button type="submit" class="btn btn-default j-save-address">保存信息</button>
      </div>
    </div>
  </div>
</div>
