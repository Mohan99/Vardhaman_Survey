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
								<form:input class="form-control disabled" id="employeeId"
									path="employeeId" />
								<span id="idloc" style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empname"
									path="employeeName" />
								<span id="nameloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Designation</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empdesg"
									path="employeeDesg" />
								<span id="desgloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								Specialization</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="empspec"
									path="employeeSpecialization" />
								<span id="specloc" style="color: red"></span>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Employee
								MobileNumber</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="mobileno"
									path="employeeMobilenumber" />
								<span id="mobileloc" style="color: red"></span>


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
							<label for=" " class="col-sm-5 control-label">Join Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="joinDate"
									path="joinDate" />
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
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
					//$("#stateId").append(response.responseText);
	        	}
	        },
	        error: function(response) { 
	        	if(response.responseText!=null){
	        		var sEmpId=$("#stateId");
					sEmpId.empty().append('<option value="">Select State</option>');
					sEmpId.append(response.responseText);
	        	//$("#stateId").append(response.responseText);
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
				if (response.responseText != null) {
					var sEmpId=$("#cityid");
					sEmpId.empty().append('<option value="">Select City</option>');
					sEmpId.append(response.responseText);
					//$("#cityid").append(response.responseText);
				}
			},
			error : function(response) {
				if (response.responseText != null) {
					var sEmpId=$("#cityid");
					sEmpId.empty().append('<option value="">Select City</option>');
					sEmpId.append(response.responseText);
					//$("#cityid").append(response.responseText);
				}
			}
		});

	}
</script>
<script>
	$(document).ready(function() {
		$("#joinDate").datepicker({

			maxDate : new Date(),
			onSelect : function(date) {
				var date2 = $('#joinDate').datepicker('getDate');
				$('#joinDate').datepicker('option', 'minDate', date2);
				$('#joinDate').datepicker('option', 'maxDate', new Date());
			}
		});
	});
</script>
<script>
	function validateform() {
		var employeeId = document.myform.employeeId.value;
		var empname = document.myform.empname.value;
		var empdesg = document.myform.empdesg.value;
		var empspec = document.myform.empspec.value;
		var mobileno = document.myform.mobileno.value;

		if (employeeId == null || employeeId == "") {
			alert("Id can't be blank");
			document.getElementById("idloc").innerHTML = "EmployeeId is required";
			return false;
		} else if (empname == null || empname == "") {
			alert("Employee Name can't be blank.");
			document.getElementById("nameloc").innerHTML = "Employee Name is required";
			return false;
		} else if (empdesg == null || empdesg == "") {
			alert("Employee Designation can't be blank.");
			document.getElementById("desgloc").innerHTML = "Employee Designation is required";
			return false;
		} else if (empspec == null || empspec == "") {
			alert("Employee Specialization can't be blank.");
			document.getElementById("specloc").innerHTML = "Employee Specialization is required";
			return false;
		} else if (mobileno == null || mobileno == "") {
			alert("Mobile Number can't be blank.");
			document.getElementById("mobileloc").innerHTML = "Mobile Number is required";
			return false;
		}
	}
</script>

<jsp:include page="footer.jsp" />


