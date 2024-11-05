package com.fitdine.user.common.response;


import com.fitdine.user.common.code.ApiResponseCode;

public class ApiResponseGenerator {

    private ApiResponseGenerator() {

    }

    public static ApiResponse<Void> success(){
        return new ApiResponse<>(ApiResponseCode.SUCCESS);
    }

    public static <D> ApiResponse<D> success(D data){
        return new ApiResponse<>(ApiResponseCode.SUCCESS, ApiResponseCode.SUCCESS.getDefaultMessage(), data);
    }

    public static ApiResponse<Void> fail(){
        return new ApiResponse<>(ApiResponseCode.UNKNOWN_ERROR);
    }

    public static ApiResponse<Void> fail(ApiResponseCode code){
        return new ApiResponse<>(code);
    }

    public static ApiResponse<Void> fail(ApiResponseCode code, String msg){
        return new ApiResponse<>(code, msg, null);
    }

}
