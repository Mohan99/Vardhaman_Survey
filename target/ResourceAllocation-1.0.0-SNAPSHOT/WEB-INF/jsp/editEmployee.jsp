<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Edit Employee</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="EmployeeRequest"
						method="post"
						action="${pageContext.request.contextPath}/Employee/createOrUpdateEmployee">
						<form:hidden class="form-control disabled" path="empId"
							value="${empObj.getEmpId()}" readonly="true" />
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee ID</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeId"
									value="${empObj.getEmployeeId()}" readonly="true" />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeName"
									value="${empObj.getEmployeeName()}" />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Category</label>
							<div class="col-sm-7">
								<form:select path="category.categoryId" id="categoryId"
									class="form-control" title="Select Category">
									<option value="">Select Category</option>
									<c:forEach var="category" items="${categoryList}">
										<c:choose>
											<c:when
												test="${category.categoryId eq empObj.getEmployeeCategory().getCategoryId()}">
												<option value="${category.categoryId}" selected>${category.categoryName}</option>
											</c:when>
											<c:otherwise>
												<option value="${category.categoryId}">${category.categoryName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" path="employeeDesg"
									value="${empObj.getEmployeeDesg()}" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Specialization</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeSpecialization"
									value="${empObj.getEmployeeSpecialization()}" />

							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Country </label>
							<div class="col-sm-7">
								<form:select path="countries.id" id="countryId"
									class="form-control" onchange="getState(this.value)"
									title="Select Country">
									<option value="">Select Coutry</option>
									<c:forEach var="country" items="${countryList}">
										<c:choose>
											<c:when
												test="${country.id eq empObj.getEmployeeCountry().getId()}">
												<option value="${country.id}" selected>${country.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${country.id}">${country.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">State</label>
							<div class="col-sm-7">
								<form:select path="states.id" id="stateId" class="form-control"
									onchange="getCity(this.value)" title="Select State">
									<option value="">Select State</option>
									<c:forEach var="state" items="${stateList}">
										<c:choose>
											<c:when
												test="${state.id eq empObj.getEmployeeState().getId()}">
												<option value="${state.id}" selected>${state.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${state.id}">${state.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">City</label>
							<div class="col-sm-7">
								<form:select path="cities.id" id="cityid" class="form-control"
									title="Select City">
									<option value="">Select City</option>
									<c:forEach var="city" items="${cityList}">
										<c:choose>
											<c:when test="${city.id eq empObj.getEmployeeCity().getId()}">
												<option value="${city.id}" selected>${city.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${city.id}">${city.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="employeeMobilenumber"
									value="${empObj.getEmployeeMobilenumber()}" />

							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Status</label>
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${empObj.isStatus()}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="status" size="1" path="status"
											value="${empObj.isStatus()==true}" />
									</c:when>

									<c:otherwise>
										<form:checkbox class="form-control disabled" id="status"
											size="1" path="status" value="${empObj.isStatus()==true}" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Join Date</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled"
									path="joinDate" id="joinDate"
									value="${empObj.getJoinDateStr()}" />

							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
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
        url: '<%=application.getContextPath()%>
	/Employee/getCitiesList',
			dataType : "json",
			type : "GET",
			contentType : 'application/json',
			mimeType : 'application/json',
			data : {
				"state_Id" : stateId
			},
			success : function(response) {
				if (response.responseText != null) {
					$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if (response.responseText != null) {
					$("#cityid").append(response.responseText);
					//alert(response.responseText);
				}
			}
		});

	}
</script>
<script>
   $(document).ready(function () {
      
       $('#joinDate').datepicker({
           //dateFormat: "dd-M-yy",
            //dateFormat: "yy-M-dd",
           maxDate: new Date(),
           minDate: $('#joinDate').datepicker('getDate'),
           onClose: function () {
               var dt1 = $('#joinDate').datepicker('getDate');
               var dt2 = $('#joinDate').datepicker('getDate');
               //check to prevent a user from entering a date below date of dt1
               if (dt2 < dt1) {
                   $('#joinDate').datepicker('setDate', $('#joinDate').datepicker('option', 'minDate'));
               }
           }
       });
   });
</script>
<jsp:include page="footer.jsp" />
