<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Time sheet Monthly Report</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Resource Time sheet Report</h5>
					</div>

					<div class="panel-body">
						<form:form class="form-inline" method="post"
							action="${pageContext.request.contextPath}/Reports/getTimeSheetMonthlyDetails">
							<div class="form-group">
								<label for="startDate"> Select Month </label>
								<div class="input-group">
									<input type="text" class="form-control" id="month" name="month"
										required="required" name="startdate" placeholder="Select Month">
								</div>
							</div>

							<div class="input-group">
								<input type="submit" class="btn btn-primary" value="Fetch" class="form-control">

							</div>

						</form:form>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
 <link href="<%=application.getContextPath() %>/resources/css/MonthPicker.min.css" rel="stylesheet" type="text/css" />
<script src="<%=application.getContextPath() %>/resources/js/MonthPicker.js"></script>
<script src="<%=application.getContextPath() %>/resources/js/MonthPicker.min.js"></script>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
		$('#month').MonthPicker({ Button: false });
	});
</script>
<jsp:include page="footer.jsp" />
