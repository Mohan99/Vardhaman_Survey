<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Survey Questions</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="questionRequest"
						method="GET"
						action="${pageContext.request.contextPath}/Campaign/upadateAnswer">

						<form:hidden class="form-control disabled " path="id"
							value='${camQuestions.id }' />
						<form:hidden class="form-control disabled " path="campaign.id"
							value='${camQuestions.campaign.id }' />
							<form:hidden class="form-control disabled " path="prefix"
							value='${camQuestions.prefix }' />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Question </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="question"
									value="${camQuestions.question}" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option1 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option1"
									value="${camQuestions.option1}" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option2 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option2"
									value="${camQuestions.option2}" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option3 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option3"
									value="${camQuestions.option3}" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Option4 </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="option4"
									value="${camQuestions.option4}" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Answer </label>
							<div class="col-sm-7">
								<input type="text" id="ans" name="ans">
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Next</button>
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
