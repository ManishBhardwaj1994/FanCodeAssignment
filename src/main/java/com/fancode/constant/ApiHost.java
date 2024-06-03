package com.fancode.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiHost {
    BASE_URL("https://jsonplaceholder.typicode.com");
    private String apiHost;
}
