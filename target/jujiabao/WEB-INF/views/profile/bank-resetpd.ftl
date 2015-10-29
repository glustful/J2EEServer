<#include "/common/base.ftl">

<@addJS ["plupload.full.min"] />
<@base title="修改密码">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container">
    <#include "bank-head.ftl">

    <div style="width:448px;margin-left:38px;margin-top:30px;">
      <form>
        <div class="form-group" style="border-bottom:1px solid #e6e6dd;padding-bottom:20px;margin-bottom:20px;">
          <label for="" style="color:#999;">请输入原始交易密码</label>
          <input type="password" class="form-control" id="pass" style="border-color:#e9e9d7;height:45px;line-height:45px;">
        </div>
        <div class="form-group">
          <label for="" style="color:#999;">请输入新的交易密码</label>
          <input type="password" class="form-control" id="pass2" style="border-color:#e9e9d7;height:45px;line-height:45px;">
          <p style="color:#666;margin-top:5px;">交易密码必须至少6位</p>
        </div>
        <div class="form-group" style="margin-bottom:20px;">
          <label for="" style="color:#999;">请再次输入新的交易密码</label>
          <input type="password" class="form-control" id="pass3" style="border-color:#e9e9d7;height:45px;line-height:45px;">
        </div>
        <a href="javascript:setPass();" class="btn btn-default" style="padding: 10px 20px;background-color:#ff4200;border:0;color:white;">修改密码</a>
      </form>
    </div>
  </div>
</@base>

<script type="text/javascript">
     var setPass = function(){
          var pass = $.trim($("#pass").val());
          var pass2 = $.trim($("#pass2").val());
          var pass3 = $.trim($("#pass3").val());
          if(pass == ""){
              alert("请输入密码");
              return;
          } else if(pass.length < 6 || pass2.length < 6 || pass3.length < 6){
              alert("请确保密码长度至少6位");
              return;
          } else if(pass2 != pass3){
              alert("两次输入密码不一致，请重新输入");
              return;
          }
          
          $.ajax({
                url: "${serverPath}/recharge/resetpwd",
                data: {oldPwd:pass, newPwd:pass2}
            }).done(function(res) {
                if(res.success) {
                	alert("修改成功！");
                	window.location = "${serverPath}/recharge/bankindex";
                } else {
                	alert(res.msg);
                }
            });
     };
</script>