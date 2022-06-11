<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="../fragments/adminHeader.jsp" />

<div class="container">

<h3>Veuillez saisir un ou plusieurs critères pour telecharger les Fichiers des notes :</h3>
  <form:form method="POST" action="showPreferences" modelAttribute="preferences">
    <table>
      <tr>
        <td><b>Module:</b></td>
        <td><form:select path="module" items="${allModules}" itemValue="idModule" itemLabel="titre" /></td>
        <td><b>Niveau:</b></td>
        <td><form:select path="niveau" items="${allNiveau}" itemValue="idNiveau" itemLabel="titre" /></td>
        <td><b>Session:</b></td>
        <td>
       
          <form:select path="session">
              <form:option value="-1" label="--- Choisir Session ---"/>
              <form:option value="0" label="Normale"/>
              <form:option value="1" label="Ratrappage"/>
          </form:select>
        </td>
      </tr>
      <tr>
        
       
      </tr>
      <tr>
     
        <td> <input type="submit" value="Telecharger" class="btn btn-primary" style="text-align: right"></td>
        
      </tr>
    </table>
  </form:form>
  </div>
  