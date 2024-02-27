package com.lychee.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalNode {

    public ApprovalNode(Long approvalProjectId, String nodeCode, String nodeName, String nodeDate, String payPercent) {
        this.approvalProjectId = approvalProjectId;
        this.nodeCode = nodeCode;
        this.nodeName = nodeName;
        this.nodeDate = nodeDate;
        this.payPercent = payPercent;
    }

    private Long id;

    private Long approvalProjectId;

    private String nodeCode;

    private String nodeName;

    private String nodeDate;

    private String payPercent;

}
