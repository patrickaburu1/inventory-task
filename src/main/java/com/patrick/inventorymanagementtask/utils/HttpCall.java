package com.patrick.inventorymanagementtask.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author patrick on 4/14/20
 * @project myduka-pos
 */
@Service
public class HttpCall<T> {

    public ResponseEntity<T> sendPostAPIRequest(String switchUrl, String json, HttpHeaders httpHeaders, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> httpEntity = new HttpEntity<>(json, httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(switchUrl)
                .build()
                .encode();

        return restTemplate.exchange(uriComponents.toString(), HttpMethod.POST, httpEntity, clazz);
    }

    public ResponseEntity<T> sendGetAPIRequest(String switchUrl, HttpHeaders httpHeaders, Class<T> clazz){
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(switchUrl)
                .build()
                .encode();

        return restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, clazz);
    }
}
