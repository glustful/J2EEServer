<div class="footer">
  <div class="container bd-content">
    <div class="row">
      <div class="col col-md-8">
        <div class="row">
          <div class="col col-md-3" style="width:120px;line-height:144px;">
            <img class="logo" src="/jjbstatic/img/footer-logo.png">
          </div>
          <div class="col col-md-9">
            <p style="font-weight:bold;margin-top:40px;">改变传统生活方式，迎接明天便利生活</p>
            <p>自建了上千平方米的中央厨房，配备一流的厨师团队，采用高标准的质检要求，</p>
            <p>严格把控原材料的同时，每一套环节都有严格的质检标准。</p>
          </div>
        </div>
      </div>
      <div class="col col-md-4">
        <div class="row" style="margin-top: 30px;">
          <div class="col pull-right" style="margin-left:22px;text-align:right;">
            <p style="color:#ff4200;font-size:16px;margin-top:16px;margin-bottom:1px;">移动端订餐</p>
            <p style="color:#ff4200;font-size:16px;">更便捷</p>
            <p style="margin-bottom:0;">扫描下载客户端</p>
          </div>
          <div class="col pull-right" style="margin-left:22px;padding-top:25px;">
            <img class="qr" src="/jjbstatic/img/android-qr-mark.png" style="width:42px;height:42px;cursor:pointer;">
            <div class="qr-box android">
              <img src="/jjbstatic/img/android.png">
            </div>
          </div>
          <div class="col pull-right" style="margin-left:22px;padding-top:25px;">
            <img class="qr" src="/jjbstatic/img/apple-qr-mark.png" style="width:42px;height:42px;cursor:pointer;">
            <div class="qr-box ios">
              <img src="/jjbstatic/img/ios.png">
            </div>
          </div>
          <div class="col pull-right" style="padding-top:25px;">
            <img src="/jjbstatic/img/footer-qr-tip.png" style="width:66px;">
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade ls-modal" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">用户登录</h4>
      </div>
      <div class="modal-body">
        <div id="login-section">
          <form id="login-form" class="form-horizontal">
            <div class="form-group input-group">
              <label for="l-mobile" class="col-sm-2 control-label">手机号</label>
              <div class="col-sm-10">
                <input type="text" name="user" class="form-control" id="l-mobile">
              </div>
            </div>
            <div class="form-group input-group">
              <label for="l-password" class="col-sm-2 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
              <div class="col-sm-10">
                <input type="password" name="pass" class="form-control" id="l-password">
              </div>
            </div>
            <div class="form-group" style="margin-top: 20px;"><a href="javascript:void(0);" class="forget-password">忘记密码</a></div>
            <div class="form-group">
              <button type="button" class="dologin btn btn-default btn-lg btn-block">登&nbsp;&nbsp;录</button>
            </div>
          </form>
        </div>

        <div id="forget-section" style="display:none;">
          <form class="form-horizontal">
            <div class="form-group input-group">
              <label class="col-sm-5 control-label" style="width:152px;">注册时使用的手机号</label>
              <div class="col-sm-7">
                <input type="text" name="user-forget" class="form-control">
              </div>
            </div>
            <div class="form-group input-group">
              <div class="col-sm-5">
                <input type="text" name="find-pwd-verify" class="form-control" style="height:40px;" placeholder="请输入短信验证码">
              </div>
              <div class="col-sm-7">
                <button type="button" class="send-forget-sms btn btn-default btn-block" style="margin-top:3px;margin-bottom:3px;"><i class="cd" style="margin-right:5px;font-style:normal;"></i>发送短信验证码</button>
              </div>
            </div>
            <div class="form-group input-group">
              <label class="col-sm-2 control-label">新密码</label>
              <div class="col-sm-10">
                <input type="password" name="new-pass" class="form-control">
              </div>
            </div>
            <div class="form-group" style="margin-top: 20px;"><a href="javascript:void(0);" class="back-login">回到登录</a></div>
            <div class="form-group">
              <button type="button" class="find-forget-pwd btn btn-default btn-lg btn-block">找回密码</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade ls-modal" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="signupModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">用户注册</h4>
      </div>
      <div class="modal-body">
        <div id="reg-one">
          <form id="register-form" class="form-horizontal">
            <div class="form-group input-group">
              <label for="s-mobile" class="col-sm-2 control-label">手机号</label>
              <div class="col-sm-10">
                <input required type="text" name="userName" class="form-control" id="s-mobile">
              </div>
            </div>
            <div class="form-group input-group">
              <div class="col-sm-5">
                <input required type="text" name="verifyCode" class="form-control" id="s-sms" style="height:40px;" placeholder="请输入短信验证码">
              </div>
              <div class="col-sm-7">
                <button type="button" class="sendverify btn btn-default btn-block" style="margin-top:3px;margin-bottom:3px;"><i class="cd" style="margin-right:5px;font-style:normal;"></i>发送短信验证码</button>
              </div>
            </div>
            <div class="form-group input-group">
              <label for="s-password" class="col-sm-2 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
              <div class="col-sm-10">
                <input required type="password" name="password" class="form-control" id="s-password">
              </div>
            </div>
            <div class="form-group input-group">
              <label for="s-nickname" class="col-sm-2 control-label">昵&nbsp;&nbsp;&nbsp;&nbsp;称</label>
              <div class="col-sm-10">
                <input required type="text" name="nickname" class="form-control" id="s-nickname">
              </div>
            </div>
            <div class="form-group checkbox">
              <label>
                <input class="agreement" type="checkbox" checked style="height:auto;margin-top:4px;"><a href="/register/legalprovisions" target="_blank">我已阅读并同意《居家宝用户注册协议》</a>
              </label>
            </div>
            <div class="form-group">
              <a href="javascript:void(0);" class="btn btn-default btn-lg btn-block go-reg-tow">下一步（填写配送信息）</a>
            </div>
          </form>
        </div>

        <div id="reg-two" style="display:none;">
          <form id="address-form" class="form-horizontal">
            <div class="form-group">
              <label for="" class="col-sm-2 control-label">选择地区</label>
              <div class="col-sm-2">
                <select required class="form-control region-select region-select-1" data-index="1">
                  <option>请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select required class="form-control region-select region-select-2" data-index="2">
                  <option>请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select required class="form-control region-select region-select-3" data-index="3">
                  <option>请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select required class="form-control region-select region-select-4" data-index="4">
                  <option>请选择</option>
                </select>
              </div>

              <div class="col-sm-2">
                <select required class="form-control region-select region-select-5" data-index="5">
                  <option>请选择</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="" class="col-sm-2 control-label">所在楼层</label>
              <div class="col-sm-2">
                <input required type="text" class="form-control" name="floor" id="">
              </div>

              <label for="" class="col-sm-2 control-label">详细地址</label>
              <div class="col-sm-6">
                <input required type="text" class="form-control" name="manualAddress" id="">
              </div>
            </div>

            <div class="form-group">
              <label for="" class="col-sm-2 control-label">收货人姓名</label>
              <div class="col-sm-2">
                <input required type="text" class="form-control" name="name" id="">
              </div>

              <label for="" class="col-sm-2 control-label">手机号码</label>
              <div class="col-sm-6">
                <input required type="text" class="form-control" name="mobile" id="">
              </div>
            </div>
          </form>
          <button type="button" class="doregister btn btn-default btn-lg btn-block">完成注册</button>
        </div>
      </div>
    </div>
  </div>
</div>
