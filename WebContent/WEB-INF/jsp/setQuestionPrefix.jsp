<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Set Prefix</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal"
						modelAttribute="campQuestionRequest" method="POST"
						action="${pageContext.request.contextPath}/Campaign/createOrupdatePrefix">
						<form:hidden class="form-control disabled" path="id"
							value="${questions.getId()}" readonly="true" />
						<form:hidden class="form-control disabled" path="campaign.id"
							value="${questions.getCampaign()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Question</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projectName"
									name="question" value="${questions.getQuestion()}" readonly="true"
									path="question" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Option1</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="option1"
									name="option1" value="${questions.getOption1()}" path="option1" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Option2</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="option2"
									name="option2" value="${questions.getOption2()}" path="option2" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Option3</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="option3"
									name="option3" value="${questions.getOption3()}" path="option3" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Option4</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="option4"
									name="option4" value="${questions.getOption4()}" path="option4" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Prefix</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="prefix"
									name="prefix" path="prefix" />
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

<jsp:include page="footer.jsp" />







