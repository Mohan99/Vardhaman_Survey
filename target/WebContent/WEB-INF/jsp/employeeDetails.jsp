<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Employee Details</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						onsubmit="return validateform()" name="myform" method="get"
						action="${pageContext.request.contextPath}/Employee/employeeHistory">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID</label>
							<div class="col-sm-7">

								<!-- <input type="text" id=""  placeholder="Employee Code" > -->
								<form:input class="form-control disabled" id="employeeId" path="employeeId" />
							</div>
						</div>
				
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>

<script>  
function validateform(){  
var employeeId=document.myform.employeeId.value;  
var empname=document.myform.empname.value;  
  
if (employeeId==null || employeeId==""){  
  alert("Id can't be blank");  
  return false;  
}
}  
</script>  
<jsp:include page="footer.jsp" />
