<#include "macro.ftl">

<#macro base title>
    <!DOCTYPE html>
    <html lang="zh-CN">
    <head>
        <#include "meta.ftl">
        <title>${title}</title>
        <@outCss "style"/>

        <#if css??>
            <#list css as item>
                <@outCss "${item}" />
            </#list>
        </#if>
        <!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
		<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
		<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
		<!--[if IE 9 ]>    <html class="ie9"> <![endif]-->
		<!--[if (gt IE 9)|!(IE)]><!--> <html class=""> <!--<![endif]-->
    </head>
    <body>
        <#include "/header/header.ftl">
        <#nested/>

        <#include "/footer/footer.ftl">
        <#include "commonjs.ftl">

        <#if javascript??>
            ${javascript}
        </#if>
    </body>
    </html>
</#macro>