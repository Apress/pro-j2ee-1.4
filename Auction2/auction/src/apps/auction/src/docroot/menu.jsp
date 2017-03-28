<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<c:choose>
  <c:when test="${j_signon == true}">
    <a href="offerList.do"><b>My Offers</b></a>
    <br/>
    <a href="customerEdit.do"><b>My Account</b></a>
    <br/>
  </c:when>
  <c:otherwise>
    <waf:form name="logonForm" action="j_signon_check" method="POST">
      <waf:input type="text" name="j_username" size="10"/>
      <br/>
      <waf:input type="password" name="j_password" size="10"/>
      <br/>
      <a href="javascript:logonForm.submit()"><b>Login</b></a>
    </waf:form>
    <a href="customer_create.screen"><b>Register</b></a>
    <br/>
  </c:otherwise>
  <waf:form name="searchForm" action="offerSearch.do" method="POST">
    <waf:input type="text" name="searchText"/>
    <br/>
    <a href="javascript:searchForm.submit()"><b>Search</b></a>
  </waf:form>
  <br/>
</c:choose>

