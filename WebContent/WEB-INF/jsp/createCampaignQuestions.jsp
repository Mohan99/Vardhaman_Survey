<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Campaign</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Campaigns List</h5>
					</div>
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/Campaign/createCampaign">
							<i class="fa fa-user-plus"> </i> Add Campaign
						</a>
					</div>
					<!-- /.panel-heading -->
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
												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">Campaign</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Vendor Type</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Create Questions</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach var="campaign" items="${campaignList}">


												<tr class="gradeA odd" role="row">
													<td class="sorting_1">${campaign.name}</td>
													<td>${campaign.vendorType.name}</td>
													<td class="center"><a
														href="<%=request.getContextPath()%>/Campaign/createQuestion/${campaign.id}">
															<i class="btn btn-success btn-sm">Create Survey Questions </i>
													</a></td>
												</tr>
											</c:forEach>
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
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>
<jsp:include page="footer.jsp" />
