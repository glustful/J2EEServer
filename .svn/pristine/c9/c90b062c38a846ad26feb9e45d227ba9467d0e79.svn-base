<#include "/common/base.ftl">

<@addJS ["plupload.full.min"] />
<@base title="个人资料">
  <#include "sidebar.ftl">
  <div class="sidebar-container main-container">
    <div class="profile-container">
      <div style="border-bottom:1px solid #dbdcd3;padding-bottom:30px;">
        <p style="font-size:24px;margin-bottom:30px;">个人资料</p>
        <div class="row">
          <div class="col col-md-3" style="width:200px;">
            <div>
              <img class="user-avatar" src="/blogo${(currentUser.logo)!}">
            </div>
            <div>
              <span id="upload-avatar">修改头像</span>
              <span id="progress"></span>
            </div>
          </div>

          <div class="col col-md-2">
            <div style="margin-top:22px;">
              <p>昵称</p>
              <div>
                <input id="user-nickname" class="nickname form-control" value="${(currentUser.nickname)!}" type="text" placeholder="不超过15个字符">
              </div>
              <div style="margin-top:36px;"><a  class="btn btn-default save-nickname" data-user>保存昵称</a></div>
            </div>
          </div>
        </div>
      </div>

      <div style="margin-top:38px;">
        <p style="font-size:24px;">修改密码</p>
        <div class="form-horizontal" style="width:387px;margin-top:20px;">
          <div class="form-group">
            <label for="oldPassword" class="col-sm-4 control-label">原始密码</label>
            <div class="col-sm-8">
              <input type="password" class="form-control" id="oldPassword" placeholder="请输入原始密码">
            </div>
          </div>
          <div class="form-group">
            <label for="newPassword1" class="col-sm-4 control-label">新的密码</label>
            <div class="col-sm-8">
              <input type="password" class="form-control" id="newPassword1" placeholder="请输入新密码">
            </div>
          </div>
          <div class="form-group">
            <label for="newPassword2" class="col-sm-4 control-label">再次输入新密码</label>
            <div class="col-sm-8">
              <input type="password" class="form-control" id="newPassword2" placeholder="请再次输入新密码">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-4 col-sm-10">
              <button type="submit" class="btn btn-default save-pass">保存密码</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</@base>

<script type="text/javascript">
var uploader = new plupload.Uploader({
    runtimes: 'html5,flash,html4',
    browse_button: 'upload-avatar',
    url: '/user/head',
    multi_selection: false,
    filters: {
      max_file_size: '5mb',
      mime_types: [
        {title: "Image files", extensions: "jpg,gif,png"}
      ]
    },

    flash_swf_url: '/jjbstatic/swf/Moxie.swf',
    init: {
        PostInit: function() {
          $('#progress').empty();
        },

        FilesAdded: function(up, files) {
          uploader.start();
        },

        UploadProgress: function(up, file) {
          $('#progress').text(file.percent+'%');
        },

        FileUploaded: function(up, file, info) {
          var res = JSON.parse(info.response);

          $('.user-avatar').attr('src', '/blogo'+res.file.path);
          $('.navbar-right .avatar img').attr('src', '/blogo'+res.file.path);
        },

        Error: function(up, err) {
          alert(err.message);
        }
    }
});

uploader.init();
</script>