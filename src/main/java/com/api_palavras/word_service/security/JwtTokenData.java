package com.api_palavras.word_service.security;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JwtTokenData {

    private  String subject;
    private Date issuedAt;
    private Date expiration;
    private List<String> roles;
}

