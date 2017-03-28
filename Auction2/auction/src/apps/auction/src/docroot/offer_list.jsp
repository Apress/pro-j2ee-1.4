<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<%@ taglib prefix="c" uri="/WEB-INF/c-rt.tld" %>
<h3>My Offers</h3>

<table>
  <tr>
    <td>Name</td>
    <td>Description</td>
    <td>Ask Price</td>
    <td/>
    <td/>
  </tr>
  <c:forEach items="${offerList}" var="offer" varStatus="status">
    <tr>
      <form name="offerEditForm<c:out value="${status.count}"/>" action="offerUpdate.do" method="POST">
        <input type="hidden" value="<c:out value="${offer.offerId}"/>" name="offerId"/>
        <td><input type="text" value="<c:out value="${offer.name}"/>" name="name"/></td>
        <td><input type="text" value="<c:out value="${offer.description}"/>" name="description"/></td>
        <td><input type="text" value="<c:out value="${offer.askPrice}"/>" name="askPrice"/></td>
        <td><a href="javascript:offerEditForm<c:out value="${status.count}"/>.submit()"><b>Update</b></a></td>
        <td><a href="offerRemove.do?offerId=<c:out value="${offer.offerId}"/>"><b>Remove</b></a></td>
      </form>
    </tr>
  </c:forEach>
</table>
<a href="offer_create.screen"><b>Add Offer</b></a>
