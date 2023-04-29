<#macro login path isRegistrForm>
    <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> User Name :</label>
            <div class="col-sm-6">
            <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Password: </label>
            <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <#if isRegistrForm>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label"> Email: </label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="Email"/>
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token }" />
        <#if !isRegistrForm><a href="/registration"> Add new user</a></#if>
        <button type="submit" class="btn btn-primary"><#if isRegistrForm> Create <#else> Sign in</#if></button>
    </form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit"  class="btn btn-primary" > Sign Out </button>
    </form>
</#macro>