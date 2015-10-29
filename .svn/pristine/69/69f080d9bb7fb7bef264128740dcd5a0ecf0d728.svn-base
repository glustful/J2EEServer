<#include "/common/base.ftl">

<@addJS ["plupload.full.min"] />
<@base title="充值中心">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container">
    <div style="margin:10px 0 0 45px;">
      <div style="border-bottom:1px solid #e9e9d7;padding-bottom:5px;">
        <p>乖宝，欢迎进入 <span style="color:#ff4200;">充值中心</span></p>
        <p style="font-size:18px;">为了确保您的交易安全 <span style="color:#ff4200;">请先设置交易密码</span></p>
      </div>

      <div style="margin-top:20px;width:468px;">
        <form>
          <div class="form-group">
            <label for="" style="color:#999;">请输入交易密码</label>
            <input type="password" class="form-control" id="pass" style="border-color:#e9e9d7;height:40px;line-height:40px;">
            <p style="color:#666;margin-top:5px;">交易密码必须至少6位</p>
          </div>
          <div class="form-group">
            <label for="" style="color:#999;">请在此输入交易密码</label>
            <input type="password" class="form-control" id="pass2" style="border-color:#e9e9d7;height:40px;line-height:40px;">
          </div>
          </div>
          <a href="javascript:setPass();" class="btn btn-default" style="padding: 10px 20px;background-color:#ff4200;border:0;color:white;">设置密码</a>
        </form>
      </div>
    </div>
  </div>
</@base>

<script type="text/javascript">
     var setPass = function(){
          var pass = $.trim($("#pass").val());
          var pass2 = $.trim($("#pass2").val());
          if(pass == ""){
              alert("请输入密码");
              return;
          } else if(pass.length < 6){
              alert("请确保密码长度至少6位");
              return;
          } else if(pass != pass2){
              alert("两次输入密码不一致，请重新输入");
              return;
          }
          
          $.ajax({
                url: "${serverPath}/recharge/setpass",
                data: {pass:pass}
            }).done(function(res) {
                window.location = "${serverPath}/recharge/bankindex";
            });
     };
</script>