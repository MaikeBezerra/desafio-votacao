package com.voting.app.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIError {
    // Pradronizando no formato RFC 7807 https://datatracker.ietf.org/doc/html/rfc7807
    private Integer status;
    private String type;
    private String title;
    private String detail;

}
