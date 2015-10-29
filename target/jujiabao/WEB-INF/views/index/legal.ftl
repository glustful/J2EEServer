<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <title>用户注册协议</title>
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <link rel="stylesheet" type="text/css" href="/jjbstatic/css/bootstrap.min.css">
</head>
<body>
  <div class="container">
    <div>
      <h2 style="margin-bottom:20px;">居家宝用户注册协议</h2>
    </div>

    <div class="agreement"></div>
  </div>
  <script type="text/javascript" src="/jjbstatic/js/jquery-1.11.2.min.js"></script>
  <script type="text/javascript">
    var json = ${json};
    $(function() {
      var html = '';
      $.each(json, function(i,v) {
        html += '<div style="font-size:18px;margin-bottom:10px;">'+v.txt+'</div>';

        $.each(v.child, function(a, b) {
          html += '<div style="padding-left:20px;margin-bottom:5px;">'+b.txt+'</div>';
        });
      });

      $('.agreement').html(html);
    });
  </script>
</body>
</html>