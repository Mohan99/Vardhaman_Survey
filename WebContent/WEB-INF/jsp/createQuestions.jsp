<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Create Questions</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="questionRequest"
						method="POST"
						action="${pageContext.request.contextPath}/Campaign/createOrupdateQuestion">

						<form:hidden class="form-control disabled " path="campaign.id"
							value='${campaignId }' />
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Question </label>
							<div class="col-sm-7">
								<form:textarea class="form-control disabled " path="question" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option1 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option1" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option2 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option2" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option3 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option3" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option4 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option4" />
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Remarks </label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled " path="remarks" />
							</div>
						</div>


						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Add More
									Questions</button>
								<button type="button" onclick="finishQuestions()"
									class="btn btn-primary">Finish</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>
<script type="text/javascript">
	function finishQuestions() {
		window.location = "${pageContext.request.contextPath}/Campaign/searchCampaignQuestions";
	}
</script>
<jsp:include page="footer.jsp" />
