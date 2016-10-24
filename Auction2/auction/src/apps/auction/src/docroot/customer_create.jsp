<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<h3>Register</h3>
<waf:form name="customerCreateForm" action="customerCreate.do" method="POST">
  <table>
    <tr>
      <td align="right"><b>User ID:</b></td>
      <td>
        <waf:input type="text" name="userId" size="10">
          <waf:value><c:out value="${param['userId']}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Password:</b></td>
      <td>
        <waf:input type="password" name="password" size="10">
          <waf:value><c:out value="${param[password]}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Name:</b></td>
      <td>
        <waf:input type="text" name="name" size="30">
          <waf:value><c:out value="${param['name']}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Email:</b></td>
      <td>
        <waf:input type="text" name="email" size="30">
          <waf:value><c:out value="${param[email]}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <a href="javascript:customerCreateForm.submit()"><b>Register</b></a>
      </td>
    </tr>
  
</waf:form>