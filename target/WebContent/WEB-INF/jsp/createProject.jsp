<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Project</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="projectRequest"
						method="post"
						action="${pageContext.request.contextPath}/Project/createOrupdateProject">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project
								Name</label>
							<div class="col-sm-7">
								<form:input type="text" class="form-control disabled"
									path="projectName" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Start Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="startdate"
									path="startDate" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">End Date</label>
							<div class="col-sm-7">
								<form:input type="text"
									class="form-control disabled date-picker" id="enddate"
									path="endDate" />
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label">Status</label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" checked="true"
									path="status" />
							</div>
						</div> --%>

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
   $(document).ready(function () {
       $("#startdate").datepicker({
    	// dateFormat: "yy-M-dd",
    	dateFormat: "dd-M-yy",
           maxDate: new Date(),
           onSelect: function (date) {
               var date2 = $('#startdate').datepicker('getDate');
               $('#enddate').datepicker('option', 'minDate', date2);
               $('#enddate').datepicker('option', 'maxDate', new Date());
           }
       });
       $('#enddate').datepicker({
           dateFormat: "dd-M-yy",
            //dateFormat: "yy-M-dd",
           maxDate: new Date(),
           minDate: $('#startdate').datepicker('getDate'),
           onClose: function () {
               var dt1 = $('#startdate').datepicker('getDate');
               var dt2 = $('#enddate').datepicker('getDate');
               //check to prevent a user from entering a date below date of dt1
               if (dt2 < dt1) {
                   $('#enddate').datepicker('setDate', $('#startdate').datepicker('option', 'minDate'));
               }
           }
       });
   });
</script>
<jsp:include page="footer.jsp" />
