<#include "/common/base.ftl">

<@base title="首页">
  <div class="container bd-content main-container" style="padding-bottom:28px;">
    <div class="ad-detail">
      <#if (detail.contentDetails)??>
      		<#list detail.contentDetails as detail>
      			<#if detail.type == "text">
      				 <div class="txt">
				        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(detail.content)!}</p>
				      </div>
      	 		<#else>
      	 		 	 <div>
				        <img src="${uploaddir}${(detail.content)!}">
				     </div>
      	 		</#if>
      		</#list>
      </#if>
      <div class="action">
	        <a href="${serverPath}/"><span class="btn btn-lg btn-danger">返回</span></a>
	  </div>
      <!-- <#if (detail.action)??>
      	 <div class="action">
	        <span class="btn btn-lg btn-danger">${(detail.action.name)!}</span>
	     </div>
      </#if>
      -->
    </div>
  </div>
</@base>
