<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<script>
function deleteManager(mID) {
	var x= confirm("Are you sure you want to delete this Manager = "+mID);	  
	  if(x)
		location.href=$('#deleteManagerUrl').val()+mID;
}
$(function() {
	$(".datepicker").datepicker();
	
	/* if($('#startDate').val()=="" || $('#endDate').val()=="")
	setLastWeekDates(); */
	
	
	
});
</script>

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Monthly Report</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal"
						modelAttribute="EmpReportDataRequest" id="form" method="post"
						action="${pageContext.request.contextPath}/Reports/getMonthlyReport">


						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">StartDate</label>
							<div class="col-sm-7">
								<form:input id="startDate"  class="form-control datepicker disabled"  value="${EmpReportDataRequest.getStartDate()}" path="startDate" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">EndDate</label>
							<div class="col-sm-7">
								<form:input id="endDate" class="form-control datepicker disabled"  value="${EmpReportDataRequest.getEndDate()}" path="endDate" />
							</div>
						</div>
						
						<div class="form-group">
						<input type="hidden" name="searchHidden" id="searchHidden" value="${EmpReportDataRequest.getSearchedBy()}"/>
							<label for=" " class="col-sm-5 control-label">Search By</label>
							<div class="col-sm-7">

								<form:select path="searchedBy" id="searchBy"
									class="form-control" title="Select" onchange="searchByChange();">														            
                                 <option value="All">All Employees</option>
                                  <option value="Manager">Manager</option>
                                   <option value="Employee">Employee</option>
                
								</form:select>
							</div>
						</div>
						<div class="form-group" id="managerDiv" style="display:none">
							<label for=" " class="col-sm-5 control-label">Manager
								Name</label>
							<div class="col-sm-7">

								<form:select path="ManagerId" id="managerId"
									class="form-control" title="Select Manager">
									<option value="">Select Manager</option>
									<c:forEach var="manager" items="${GcsManagerList}">              
                                 <c:choose>
											<c:when
											test="${manager.managerId eq EmpReportDataRequest.getManagerId()}">
												<option value="${manager.managerId}" selected>${manager.managerName}</option>
											</c:when>
											<c:otherwise>
												<option value="${manager.managerId}">${manager.managerName}</option>
											</c:otherwise>
										</c:choose>
                  </c:forEach> 
								</form:select>
							</div>
						</div>

						<div class="form-group" id="empDiv" style="display:none">
							<label for=" " class="col-sm-5 control-label">Employee Name</label>
							<div class="col-sm-7">
								<form:select path="empId" id="empId" class="form-control"
									title="Select Employee">
									<option value="">Select Employee</option>
									<c:forEach var="emp" items="${GcsEmpManager}">								
										 <c:choose>
											<c:when
											test="${emp.empId eq EmpReportDataRequest.getEmpId()}">
												<option value="${emp.empId}" selected>${emp.employeeName}</option>
											</c:when>
											<c:otherwise>
												<option value="${emp.empId}">${emp.employeeName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-8 col-sm-8" style="margin-top: 8px;">
								<button type="button" class="btn btn-primary" onclick="validateReportSearch()">Search</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

<div class="form-group" style="${EmployeeResponseReport!=null?'':'display:none'}" >
							<div class="col-sm-offset-8 col-sm-8" style="margin-top: 8px;">
								<button type="button" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/Employee/email'">sendEmail</button>
							</div>
						</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Monthly Report</h5>
					</div>

					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<table width="100%"
										class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
										id="dataTables-example" role="grid"
										aria-describedby="dataTables-example_info"
										style="width: 100%;">
										<thead>
											<tr role="row" class="bg-warning">
											<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1">From Date</th>
														<th>To Date</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1">Employee
													Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1">Emp
													ID</th>										
												<th>Total In Hours</th>										
												<th>Total Hours</th>											
											</tr>
										</thead>
										<tbody>



					  <c:forEach var="reportObj" items="${EmployeeResponseReport}">


                        <tr class="gradeA odd" role="row">
                         <td>${reportObj.fromDate}</td>
                          <td>${reportObj.toDate}</td>
                         <td class="sorting_1">${reportObj.employeeName}</td>                   
                          <td>${reportObj.empID}</td>                                                                 
                          <td>${reportObj.totalInHours}</td>
                           <td>${reportObj.totalHours}</td>                                             
                         
                             </tr>
                  </c:forEach>  
											<!-- <tr class="gradeA even" role="row">
                          <td class="sorting_1">Employee Name</td>
                          <td>GI1214</td>
                          <td>UI/UX Designer</td>
                          <td class="center">M-Vite</td>
                          <td class="center"><a class="btn-info btn-sm" href="Form-HR-Edit.html"> <i class="fa fa-edit"> </i> Edit</a></td>
                        </tr> -->

										</tbody>
									</table>
								</div>
							</div>

						</div>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>



	</div>
	<!-- ... Your content goes here ... -->

</div>
<script>
  
 
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    
    </script>
<jsp:include page="footer.jsp" />
