<#import "parts/common.ftlh" as c>


<@c.page>
    <div class="form-row">
       <div class="form-group col-md-6">
           <form method="get" action="/main" class="form-inline">
               <input type="text" name="filter" class="form-inline" value="${filter?if_exists}" placeholder="Search by tag">
               <button type="submit" class="btn btn-primary">Search</button>
           </form>
       </div>
    </div>
    <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
  <div class="collapse" id="collapseExample">
      <div class="form-group mt-3">
          <form method="post" enctype="multipart/form-data">
              <div class="form-group">
              <input type="text" class="form-control" name="text" placeholder="Input text" />
              </div>
              <div class="form-group">
              <input type="text" class="form-control" name="tag" placeholder="Tag">
              </div>
              <div class="custom-file">
                  <input type="file" class="form-control" name="file" id="customFile">
                  <label class="custom-file-label" for="customFile"> Choose file</label>
              </div>
              <input type="hidden" name="_csrf" value="${_csrf.token}" />
              <div class="form-group">
              <button type="subbit" class="btn btn-primary ml-2">Add</button>
              </div>
          </form>
      </div>
  </div>
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <#list  messages as message>
            <div class="card my-3">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>