package org.example.lesson14;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProductApiClient {
    private final HttpClient httpClient;
    private final String baseUrl;

    public ProductApiClient(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public String fetchProductJson(String productId) throws IOException, InterruptedException {
        HttpRequest request = buildRequest(productId);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ensureSuccess(response);
        return response.body();
    }

    private URI buildProductUri(String productId) {
        // DONE 1: 拼接商品路径并创建 URI。
        return URI.create(baseUrl+"/products/"+productId);
    }

    private HttpRequest buildRequest(String productId) {
        // DONE 2: 添加 Accept: application/json 请求头。
        return HttpRequest.newBuilder().header(
                "Accept","application/json"
                )
                .uri(buildProductUri(productId))
                .GET()
                .build();
    }

    private void ensureSuccess(HttpResponse<String> response) {
        // DONE 3: 状态码不是 2xx 时抛出 IllegalStateException。
        if(response.statusCode()<200||response.statusCode()>=300){
            throw new IllegalStateException("HTTP request failed: " + response.statusCode());
        }
    }
}
