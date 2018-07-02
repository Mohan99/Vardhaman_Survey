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
						<input type="hidden" value=${qsnId } name="qsnId" id="qsnId">
						<div class="form-group">
								<c:choose>
									<c:when test="${camQuestions eq null}">
										<h1 style="color: green">
											<b><i>Your Survey Questions Completed please Submit.</i></b>
										</h1>
									</c:when>
									<c:otherwise>
										<h1>${qsnId }.${camQuestions.question }</h1>
									</c:otherwise>
								</c:choose>
						</div>

						<div class="form-group">
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${camQuestions eq null}"></c:when>
									<c:otherwise>
										<h4>
											<input type="radio" value="A" name="ans">A.
											${camQuestions.option1 }
										</h4>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${camQuestions eq null}"></c:when>
									<c:otherwise>
										<h4>
											<input type="radio" value="B" name="ans">B.
											${camQuestions.option2 }
										</h4>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${camQuestions eq null}"></c:when>
									<c:otherwise>
										<h4>
											<input type="radio" value="C" name="ans">C.
											${camQuestions.option3 }
										</h4>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${camQuestions eq null}"></c:when>
									<c:otherwise>
										<h4>
											<input type="radio" value="D" name="ans">D.
											${camQuestions.option4 }
										</h4>
									</c:otherwise>
								</c:choose>

							</div>
						</div>

						<div class="form-group">
							<c:choose>
								<c:when test="${camQuestions.isRemarks() eq true}">
									<!-- Remarks::<textarea rows="4" cols="50" name="remarks"
											id="remarks"></textarea> -->
									<label for=" " class="col-sm-5 control-label"> Remarks
									</label>
									<div class="col-sm-7">
										<form:textarea class="form-control disabled " path="remarksStr" />
									</div>

								</c:when>

							</c:choose>

						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<c:choose>
									<c:when test="${camQuestions eq null}"></c:when>
									<c:otherwise>
										<input type=button class="btn btn-secondary" value="Back"
											onCLick="history.back()">
										<button type="submit" class="btn btn-primary"
											onclick="getSiNo()">Next</button>
									</c:otherwise>
								</c:choose>
								<button type="button" onclick="submitSurvey()"
									class="btn btn-success">Submit</button>
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
	function submitSurvey() {
		window.location.href = "${pageContext.request.contextPath}/Campaign/submitSurvey";
	}

	function getSiNo() {

	}
</script>

<jsp:include page="footer.jsp" />
