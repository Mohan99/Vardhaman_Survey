<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" /> 


<div id="page-wrapper">
    <div class="container-fluid">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header">Edit User</h3>
          <div class="col-md-6">
             <form:form class="form-horizontal" modelAttribute="UsersRequest" method="GET" action="${pageContext.request.contextPath}/Users/createOrUpdateUsers">
   <%--  <form:hidden class="form-control disabled"  path="SNo" value="${GcsEmpManager.getSNo()}" readonly="true" /> --%>
 <%-- <div class="form-group">
    <label for=" " class="col-sm-5 control-label">User ID</label>
    <div class="col-sm-7">
   
      <!-- <input type="text" id=""  placeholder="Employee Code" > -->
    <form:input class="form-control disabled"  path="userId" value="${Users.getUserId()}" readonly="true" />
    </div> 
  </div>--%>
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">User Name</label>
    <div class="col-sm-7">
    <form:input class="form-control disabled"  path="userName" value="${Users.getUserName()}" />  
    </div>
  </div> 
  <%-- <div class="form-group">
    <label for=" " class="col-sm-5 control-label">User Name</label>
    <div class="col-sm-7">
       <form:select path="userId" id="userId" class="form-control" title="Select User">
		 <option value="">Select User</option>
		 <c:forEach var="users" items="${Users}">              
                  <c:choose>
											<c:when
											test="${users.userId eq Users.getUserId()}">
												<option value="${users.userId}" selected>${users.UserName}</option>
											</c:when>
											<c:otherwise>
												<option value="${users.userId}">${users.UserName}</option>
											</c:otherwise>
										</c:choose>
                  </c:forEach> 
                    </form:select>     
    </div>
  </div> --%>
  
    
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Login Name</label>
    <div class="col-sm-7">
    <form:input class="form-control disabled"  path="loginName" value="${Users.getLoginName()}" />  
    </div>
  </div>  
  <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Login Password</label>
    <div class="col-sm-7">
    <form:input class="form-control disabled"  path="loginPassword" value="${Users.getLoginPassword()}" />  
    </div>
  </div>  
  
   <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Is Active</label>
    <div class="col-sm-7">  
     <%--  <form:checkbox class="form-control disabled" checked="true" path="isActive" /> --%>
      <input type="checkbox" checked data-toggle="toggle" data-on="Active" data-off="In Active" data-onstyle="success" data-offstyle="danger">
    </div>
  </div> 
   <div class="form-group">
    <label for=" " class="col-sm-5 control-label">Email ID</label>
    <div class="col-sm-7">
    <form:input class="form-control disabled"  path="email" value="${Users.getEmail()}" />     
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-4 col-sm-8" style="margin-top:10px;">
      <button type="submit" class="btn btn-primary">Submit</button>
    </div>
  </div>
</form:form>
          </div>
        </div>
      </div>
      
      <!-- ... Your content goes here ... --> 
      
    </div>
  </div>
<jsp:include page="footer.jsp" />