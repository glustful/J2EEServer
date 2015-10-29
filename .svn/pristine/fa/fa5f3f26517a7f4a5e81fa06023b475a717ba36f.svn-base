<#macro addCSS path>
    <#if css??>
        <#assign css = css + path>
    <#else>
        <#assign css = path>
    </#if>
</#macro>

<#macro addJS path>
    <#if js??>
        <#assign js = js + path>
    <#else>
        <#assign js = path>
    </#if>
</#macro>

<#macro addScript>
    <#assign tempScript>
        <#nested>
    </#assign>
    <#if javascript??>
        <#assign javascript = javascript + tempScript>
    <#else>
        <#assign javascript = tempScript>
    </#if>
</#macro>

<#macro outCss file>
    <link rel="stylesheet" type="text/css" href="${staticServerPath}/css/${file}.css?v=0.0.1" media="all" />
</#macro>

<#macro outJS file dir="">
    <script type="text/javascript" charset="utf-8" src="${staticServerPath}/js/${dir}${file}.js?v=0.0.1"></script>
</#macro>
