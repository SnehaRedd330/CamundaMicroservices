package com.mudynamics.workflowservice.repositories;

public interface DashboardProcessData {
String getProcessName();
String  getProcessStatus();
String getProcessDescription();
long getVersion();
String getProcessDefinitionKey();
String getCreatedBy();

}
