<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title"><b>List of Patients</b></h3>
    </div>
    <div class="panel-body" style="color: black;">


        <c:if test="${!empty patientList}">
            <table id="listp" class="table table-striped" style="color: black;">
                <thead>
                <tr>
                    <th width="80">Patient ID</th>
                    <th width="150">Patient Name</th>
                    <th width="150">Age</th>
                    <th width="150">Gender</th>
                    <th width="150">Identifiers</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${patientList}" var="patient" varStatus="status">
                    <tr>
                        <td>${patient.patientId}</td>
                        <td>${patient.personName}</td>
                        <td>${patient.age}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.identifiers}</td>
                       </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>