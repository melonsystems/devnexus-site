<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="jumbotron call" style="margin-bottom:50px">
	<div class="container">
		<div id="banner">
			<h1><strong>Edit CFP</strong></h1>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-8 col-md-offset-2">

	<spring:bind path="cfpSubmission.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="alert alert-danger fade in"
				><a href="#" data-dismiss="alert" class="close">&times;</a>
				<c:forEach var="error" items="${status.errorMessages}"
					><c:out value="${error}" escapeXml="false"/><br/>
				</c:forEach>
			</div>
		</c:if>
	</spring:bind>

	<form:form id="cfpForm" class="form-horizontal" role="form" method="post"
		modelAttribute="cfpSubmission" enctype="multipart/form-data">

		<form:hidden path="event.id"/>
		<form:hidden path="id"/>
		<h3>Presentation</h3>

		<spring:bind path="cfpSubmission.title">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="title" class="col-lg-2 control-label">Presentation Title*</label>
			<div class="col-lg-10">
				<form:input cssClass="form-control" path="title" id="title"/>
				<form:errors path="title" cssClass="fieldError"/>
			</div>
		</div>

		<spring:bind path="cfpSubmission.description">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="description" class="col-lg-2 control-label">Abstract*</label>
			<div class="col-lg-10">
				<form:textarea cssClass="form-control" path="description" id="description" rows="10" maxLength="10000"/>
				<form:errors path="description" cssClass="fieldError"/>
				<span class="help-block"><a href="http://daringfireball.net/projects/markdown/" target="_blank">
				Markdown</a> is supported for the abstract.</span>
			</div>
		</div>

		<spring:bind path="cfpSubmission.topic">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="topic" class="col-lg-2 control-label">Topic*</label>
			<div class="col-lg-10">
				<form:input cssClass="form-control" path="topic" id="topic" />
				<form:errors path="topic" cssClass="fieldError"/>
				<span class="help-block">For example: Java/JavaEE/Spring, Data, HTML5, Agile, Mobile, Cloud ...</span>
			</div>
		</div>
		<p style="clear: left;">Please help us classify your presentation.</p>

		<spring:bind path="cfpSubmission.skillLevel">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="skill-level" class="col-lg-2 control-label">Skill Level*</label>
			<div class="col-lg-10">
				<form:select cssClass="form-control" path="skillLevel" id="skill-level">
					<form:option value="" label="Please Select a Skill Level" />
					<form:options items="${skillLevels}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:errors path="skillLevel" cssClass="fieldError" />
			</div>
		</div>

		<spring:bind path="cfpSubmission.presentationType">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="skill-level" class="col-lg-2 control-label">Presentation Type*</label>
			<div class="col-lg-10">
				<form:select cssClass="form-control" path="presentationType" id="presentation-type"
					itemValue="presentationType.id">
					<form:option value="" label="Please Select a Presentation Type" />
					<form:options items="${presentationTypes}" itemLabel="name" itemValue="id"/>
				</form:select>
				<form:errors path="presentationType" cssClass="fieldError" />
			</div>
		</div>

		<spring:bind path="cfpSubmission.sessionRecordingApproved">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<div class="col-lg-offset-2 col-lg-10">
				<div class="checkbox">
					<label>
						<form:checkbox path="sessionRecordingApproved" id="sessionRecordingApproved"/> Can we record your session?
					</label>
				</div>
				<form:errors path="sessionRecordingApproved" cssClass="fieldError"/>
			</div>
		</div>
		<p>
			Please tell us if you have any preferences/requirements for your
			presentation, e.g. if you can only speak on specific days, morning/afternoon
			etc. Also, if you have any comments, please let us know.
		</p>

		<spring:bind path="cfpSubmission.slotPreference">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="slotPreference" class="col-lg-2 control-label">Slot Preference or Comments</label>
			<div class="col-lg-10">
				<form:textarea cssClass="form-control" path="slotPreference" id="slotPreference" rows="5" maxLength="1000"/>
				<form:errors path="slotPreference" cssClass="fieldError"/>
				<span class="help-block">E.g. Can you only present on one day or have other time-contraints?...</span>
			</div>
		</div>

		<spring:bind path="cfpSubmission.status">
			<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
		</spring:bind>
		<div class="form-group${errorClass}">
			<label for="cfpSubmissionStatusType" class="col-lg-2 control-label">Status*</label>
			<div class="col-lg-10">
				<form:select cssClass="form-control" path="status" id="cfpSubmissionStatusTypes"
					itemValue="status.key">
					<form:option value="" label="Please Set the Status" />
					<form:options items="${cfpSubmissionStatusTypes}" itemLabel="name" itemValue="key"/>
				</form:select>
				<form:errors path="presentationType" cssClass="fieldError" />
			</div>
		</div>

		<h3>Speaker Details</h3>
		<c:forEach items="${cfpSubmission.cfpSubmissionSpeakers}" var="speaker" varStatus="status">
			<form:hidden path="cfpSubmissionSpeakers[${status.index}].id"/>
			<h4>Speaker ${status.index+1}</h4>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].firstName">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].firstName" class="col-lg-2 control-label">First Name*</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].firstName" id="speakers[${status.index}].firstName" maxlength="255"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].firstName" cssClass="fieldError"/>
				</div>
			</div>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].lastName">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].lastName" class="col-lg-2 control-label">Last Name*</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].lastName" id="speakers[${status.index}].lastName" maxlength="255"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].lastName" cssClass="fieldError"/>
				</div>
			</div>
			<p>
				Please provide some contact information. We will use primarily email between
				now and the conference. However, on the days of the conference, it is important
				that we can reach you via cell phone, just in case emergencies arise.
			</p>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].email">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].email" class="col-lg-2 control-label">Email*</label>
				<div class="col-lg-10">
					<form:input type="email" cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].email" id="speakers[${status.index}].email"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].email" cssClass="fieldError"/>
				</div>
			</div>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].location">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].location" class="col-lg-2 control-label">Location*</label>
				<div class="col-lg-10">
					<form:input type="text" cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].location" id="speakers[${status.index}].location" tabindex="4"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].location" cssClass="fieldError"/>
					<span class="help-block">Where do you live?</span>
				</div>
			</div>

			<div class="form-group${errorClass}">
				<div class="col-lg-offset-2 col-lg-10">
					<div class="checkbox">
						<label>
							<form:checkbox path="cfpSubmissionSpeakers[${status.index}].mustReimburseTravelCost" id="speakers[${status.index}].mustReimburseTravelCost" tabindex="5"/> Please check if you require travel cost reimbursement.
						</label>
					</div>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].mustReimburseTravelCost" cssClass="fieldError"/>
				</div>
			</div>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].phone">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].phone" class="col-lg-2 control-label">Cell Phone*</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].phone" id="speakers[${status.index}].phone"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].phone" cssClass="fieldError"/>
					<span class="help-block">In case we need to urgently reach you during the conference.</span>
				</div>
			</div>

			<spring:bind path="cfpSubmissionSpeakers[${status.index}].bio">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].biotext" class="col-lg-2 control-label">Bio*</label>
				<div class="col-lg-10">
					<form:textarea cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].bio" id="speakers[${status.index}].biotext" rows="10" maxlength="10000"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].bio" cssClass="fieldError"/>
					<span class="help-block"><a href="http://daringfireball.net/projects/markdown/" target="_blank">
					Markdown</a> is supported for the bio.</span>
				</div>
			</div>

			<h5>Social links</h5>

			<spring:bind path="cfpSubmissionSpeakers[${status.index}].twitterId">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].twitterId" class="col-lg-2 control-label">Twitter Id</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].twitterId" id="speakers[${status.index}].twitterId" maxlength="255"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].twitterId" cssClass="fieldError"/>
					<span class="help-block">Please provide just the Twitter ID, not a URL.</span>
				</div>
			</div>

			<spring:bind path="cfpSubmissionSpeakers[${status.index}].googlePlusId">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].googlePlusId" class="col-lg-2 control-label">Google Plus Id</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].googlePlusId" id="speakers[${status.index}].googlePlusId" maxlength="255"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].googlePlusId" cssClass="fieldError"/>
					<span class="help-block">Please provide just the Google Plus ID, not a URL, e.g.: 104285850599215045327</span>
				</div>
			</div>

			<spring:bind path="cfpSubmissionSpeakers[${status.index}].linkedInId">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].linkedInId" class="col-lg-2 control-label">LinkedIn Id</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].linkedInId" id="speakers[${status.index}].linkedInId" maxlength="255"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].linkedInId" cssClass="fieldError"/>
					<span class="help-block">Please provide just the username in your public profile URL, e.g. (in bold): http://www.linkedin.com/in/<strong>hillert</strong></span>
				</div>
			</div>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].lanyrdId">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].lanyrdId" class="col-lg-2 control-label">Lanyrd Id</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].lanyrdId" id="speakers[${status.index}].lanyrdId" maxlength="255" tabindex="14"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].lanyrdId" cssClass="fieldError"/>
					<span class="help-block">Please provide just the username in your public profile URL, e.g. (in bold): http://lanyrd.com/profile/<strong>ghillert</strong>/</span>
				</div>
			</div>
			<spring:bind path="cfpSubmissionSpeakers[${status.index}].githubId">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].githubId" class="col-lg-2 control-label">GithubId Username</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].githubId" id="speakers[${status.index}].githubId" maxlength="255" tabindex="14"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].githubId" cssClass="fieldError"/>
					<span class="help-block">Please provide just the GitHub username, e.g. (in bold): https://github.com/<strong>ghillert</strong></span>
				</div>
			</div>

			<h5>Swag</h5>
			<p style="clear: left;">Please let us know your T-shirt size, you we can ensure we have the right size.</p>

			<spring:bind path="cfpSubmissionSpeakers[${status.index}].tshirtSize">
				<c:set var="errorClass" value="${(not empty status.errorMessage) ? ' has-error' : ''}"/>
			</spring:bind>
			<div class="form-group${errorClass}">
				<label for="speakers[${status.index}].tshirtSize" class="col-lg-2 control-label">T-Shirt Size*</label>
				<div class="col-lg-10">
					<form:input cssClass="form-control" path="cfpSubmissionSpeakers[${status.index}].tshirtSize" id="speakers[${status.index}].tshirtSize"/>
					<form:errors path="cfpSubmissionSpeakers[${status.index}].tshirtSize" cssClass="fieldError"/>
					<span class="help-block">E.g. S, M, L etc.</span>
				</div>
			</div>

		</c:forEach>

		<div class="form-group">
			<div class="col-lg-offset-2 col-lg-10">
				<button type="submit" class="btn btn-default" name="cancel">Cancel</button>
				<button type="submit" class="btn btn-default" name="delete">Delete</button>
				<button type="submit" class="btn btn-default" lang="save">Save</button>

			</div>
		</div>

		<p>Fields denoted with * are mandatory.
	</form:form>
	</div>
</div>

<div class="modal fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				Are you sure?
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-primary" id="delete">Delete</button>
				<button type="button" data-dismiss="modal" class="btn">Cancel</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	$('button[name="delete"]').on('click', function(e){
		var form=$(this).closest('form');
		e.preventDefault();
		$('#confirm').modal({
			backdrop: 'static', keyboard: false
		})
		.one('click', '#delete', function() {
			form.trigger('submit');
		});
	});

<!--
	$(function(){
		var tabindex = 1;
		$('input,select,textarea').each(function() {
			if (this.type != "hidden") {
				var $input = $(this);
				$input.attr("tabindex", tabindex);
				tabindex++;
			}
		});
		$('input,select,textarea').first().focus();
	});
//-->
</script>
