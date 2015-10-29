<#if dataList??>
  <#list dataList as comment>
    <div class="row comment-item">
        <div class="row">
          <div class="col col-md-8">
            <p class="comment">${(comment.summary)!}</p>
            <p class="time">${(comment.createTimeStr)!}</p>
          </div>

          <div class="col col-md-2" style="padding-left:20px;">
            <img class="avatar" src="/logo${(comment.user.logo)!}">
            <span class="nickname">${(comment.nickname)!}</span>
          </div>
        </div>
      </div>
  </#list>
  <#if haveMore?? && haveMore>
    <div class="row" id="j-comment-more" data-page="${(pageNo)!1}" data-set="${(setid)!}" style="text-align:center;font-size:16px;cursor:pointer;">
      加载更多
    </div>
  </#if>
</#if>