package com.lychee.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalProject {

    private Long id;

    private String projectName;

    private String projectType;

    private String projectDescription;

    private String projectManagerName;

    private String projectMembers;

    private String costName;

    private String chargeable;

    private String productive;

    private String usableHours;

    private String usableReim;

    private String file;

    private String spName;

}
