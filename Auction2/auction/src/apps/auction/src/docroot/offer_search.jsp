<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<h3>Search Results</h3>

<table>
  <tr>
    <td>Name</td>
    <td>Description</td>
    <td>Ask Price</td>
    <td/>
  </tr>
  <c:forEach items="${offerList}" var="offer" varStatus="status">
    <tr valign="middle">
      <td><c:out value="${offer.name}"/></td>
      <td><c:out value="${offer.description}"/></td>
      <td><c:out value="${offer.askPrice}"/></td>
      <c:if test="${j_signon == true}">
        <td>
          <form name="bidForm<c:out value="${status.count}"/>" action="offerBid.do" method="POST">
            <input type="hidden" value="<c:out value="${offer.offerId}"/>" name="offerId"/>
            <input type="text" name="bidPrice"/>
            <a href="javascript:bidForm<c:out value="${status.count}"/>.submit()"><b>Bid</b></a>
          </form>
        </td>
      </c:if>
    </tr>
  </c:forEach>
</table>