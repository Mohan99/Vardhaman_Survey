<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header"> Create Campaign </h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="campaignRequest"
						method="GET"
						action="${pageContext.request.contextPath}/Campaign/createOrupdateCampaign">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Campaign Name </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="name" />
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Vendor Type </label>
							<div class="col-sm-7">
								<form:select path="vendorType.id" id="vendorTypeId" name="vendorTypeId"
									class="form-control" 
									title="Select Country">
									<option value="">Select Vendor Type</option>
									<c:forEach var="type" items="${vendorTypeList}">
										<option value="${type.id}">${type.name}</option>
									</c:forEach>
								</form:select>
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
<jsp:include page="footer.jsp" />
