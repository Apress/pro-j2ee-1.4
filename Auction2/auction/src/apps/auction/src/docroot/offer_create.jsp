<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<h3>New Offer</h3>
<waf:form name="offerCreateForm" action="offerCreate.do" method="POST">
  <table>
    <tr>
      <td align="right"><b>Name:</b></td>
      <td>
        <waf:input type="text" name="name" size="20">
          <waf:value><c:out value="${param[name]}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Description:</b></td>
      <td>
        <waf:input type="text" name="description" size="10">
          <waf:value><c:out value="${param[description]}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td align="right"><b>Ask Price:</b></td>
      <td>
        <waf:input type="text" name="askPrice" size="30">
          <waf:value><c:out value="${param[askPrice]}"/></waf:value>
        </waf:input>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <a href="javascript:offerCreateForm.submit()"><b>Create Offer</b></a>
      </td>
    </tr>
  
</waf:form>
