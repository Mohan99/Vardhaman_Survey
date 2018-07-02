<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Create New Employee</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						onsubmit="return validateform()" name="myform" method="post"
						action="${pageContext.request.contextPath}/Employee/createOrUpdateEmployee">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID</label>
							<div class="col-sm-7">

								<!-- <input type="text" id=""  placeholder="Employee Code" > -->
								<form:input class="form-control disabled" id="employeeId" path="employeeId" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empname"
									path="employeeName" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeDesg" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Specialization</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeMobilenumber" />


								<!--  <input type="text" class="form-control disabled" id="" placeholder="Employee  Name"> -->

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Category</label>
							<div class="col-sm-7">
								<form:select path="category.categoryId" id="categoryId"
									class="form-control" title="Select Category">
									<option value="">Select Category</option>
									<c:forEach var="category" items="${categoryList}">
										<option value="${category.categoryId}">${category.categoryName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Country</label>
							<div class="col-sm-7">
								<form:select path="countries.id" id="countryId" name="contryId"
									class="form-control" onchange="getState(this.value)"
									title="Select Country">
									<option value="">Select Country</option>
									<c:forEach var="country" items="${countryList}">
										<option value="${country.id}">${country.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">State</label>
							<div class="col-sm-7">
								<form:select path="states.id" id="stateId" name="state"
									class="form-control" onchange="getCity(this.value)"
									title="Select state">
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">City</label>
							<div class="col-sm-7">
								<form:select path="cities.id" id="cityid" name="city"
									class="form-control" title="Select City">

								</form:select>
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Location</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization" />
							</div>
						</div> --%>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Status</label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active"
									data-onstyle="success" data-offstyle="danger" path="status" />
								<!-- <i class="fa fa-toggle-on" aria-hidden="true"></i> -->
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
var userCountryId;
var userStateId;
var userCityId;
var country_id;
function getState(contryId){
	//alert(contryId);
		$.ajax({
	        url: '<%=application.getContextPath()%>/Employee/getStatesList',
	        dataType: "json",
	        type: "GET",
	        contentType: 'application/json',
	        mimeType: 'application/json',
	        data : {"country_Id" : contryId},
	        success: function(response){
	        	//alert("success")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	}
	        },
	        error: function(response) { 
	        	//alert("fail")
	        	if(response.responseText!=null){
	        	$("#stateId").append(response.responseText);
	        	//alert(response.responseText);
	        	}
	        }
	    });
		
	}
function getCity(stateId){
  // alert(stateId);
	$.ajax({
        url: '<%=application.getContextPath()%>/Employee/getCitiesList',
			dataType : "json",
			type : "GET",
			contentType : 'application/json',
			mimeType : 'application/json',
			data : {
				"state_Id" : stateId
			},
			success : function(response) {
				if(response.responseText!=null){
				$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if(response.responseText!=null){
				$("#cityid").append(response.responseText);
				//alert(response.responseText);
				}
			}
		});

	}
</script>
<script>  
function validateform(){  
var employeeId=document.myform.employeeId.value;  
var empname=document.myform.empname.value;  
  
if (employeeId==null || employeeId==""){  
  alert("Id can't be blank");  
  return false;  
}else if(password.length<6){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  
</script>  
<jsp:include page="footer.jsp" />
