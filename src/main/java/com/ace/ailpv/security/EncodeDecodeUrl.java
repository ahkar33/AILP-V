package com.ace.ailpv.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncodeDecodeUrl {
    public String decodeUrl(String givenUrl){
        String decodedUrl = null;
        try {
            decodedUrl = URLDecoder.decode(givenUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedUrl;
    }

    public String encodeUrl(String givenUrl){
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(givenUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedUrl;
    }
}
