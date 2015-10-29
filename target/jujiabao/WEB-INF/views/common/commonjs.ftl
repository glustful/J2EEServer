<script type="text/javascript" charset="utf-8">
  (function(Config) {
    Config.server = '${serverPath}';
  }(window.Config = window.Config || {}));
</script>

<@outJS "jquery-1.11.2.min" />
<@outJS "bootstrap.min" />
<@outJS "bootstrap-notify.min" />
<@outJS "moment.min" />
<@outJS "jquery.validate.min" />
<@outJS "store.min" />
<!--[if IE 8]><@outJS "respond4ie8" /> <![endif]-->

<#if js??>
  <#list js as item>
    <@outJS "${item}" />
  </#list>
</#if>

<@outJS "index" />
