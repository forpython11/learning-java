package org.example.lesson08;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApiResponse<UserProfile> userResponse = ApiResponse.success(
                new UserProfile("U001", "Ada")
        );
        ApiResponse<List<String>> rolesResponse = ApiResponse.success(
                List.of("ADMIN", "EDITOR")
        );
        ApiResponse<UserProfile> failureResponse = ApiResponse.failure(
                "User not found"
        );

        printResponse(userResponse);
        printResponse(rolesResponse);
        printResponse(failureResponse);
    }

    private static <T> void printResponse(ApiResponse<T> response) {
        // DONE 3: 根据 isSuccess() 打印 "Success: 数据" 或 "Failure: 消息"。
        if(response.isSuccess()){
            System.out.println("Success: "+ response.getData());
        }else{
            System.out.println("Failure: "+ response.getMessage());
        }
    }
}
