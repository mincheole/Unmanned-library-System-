<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*, java.net.*" %>
<%
    String rfid_key = null;
	System.out.println("Create rfid obj");
	rfid_key = request.getParameter("rfid");
	System.out.println(rfid_key);
	out.println(rfid_key);
	System.out.println("rfid jsp end");
%>