<#import "parts/common.ftlh" as c>

<@c.page>

    List of User
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
          <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role> ${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}"></a> </td>
           </tr>
            <#else>
            No users
        </#list>
        </tbody>
    </table>
</@c.page>