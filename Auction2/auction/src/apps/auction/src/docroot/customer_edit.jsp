<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<h3>Update Details</h3>
<waf:form name="customerEditForm" action="customerUpdate.do" method="POST">
  <table>
    <waf:input type="hidden" name="userId" size="10">
      <waf:value><c:out value="${customer.userId}"/></waf:value>
    </waf:input>
    <tr>
      <td align="right"><b>Password:</b></td>
      <td>
        <waf:input type="password" name="password" size="10">
          <waf:value><c:out value="${customer.password}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Name:</b></td>
      <td>
        <waf:input type="text" name="name" size="30">
          <waf:value><c:out value="${customer.name}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Email:</b></td>
      <td>
        <waf:input type="text" name="email" size="30">
          <waf:value><c:out value="${customer.email}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <a href="javascript:customerEditForm.submit()"><b>Update Details</b></a>
      </td>
    </tr>
  
</waf:form>
