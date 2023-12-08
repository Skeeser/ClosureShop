package com.keeser.web.common;


public interface ResultCode {
    int STATUS_JSON_UNVAIL = 0;  // json无效
    int STATUS_OK = 200;  // 请求成功
    int STATUS_CREATED = 201;  // 创建成功
    int STATUS_DELETED = 204;  // 删除成功
    int STATUS_BAD_REQUEST = 400;  // 请求的地址不存在或者包含不支持的参数
    int STATUS_UNAUTHORIZED = 401;  // 未授权
    int STATUS_FORBIDDEN = 403;  // 被禁止访问
    int STATUS_NOT_FOUND = 404;  // 请求的资源不存在
    int STATUS_UNPROCESSABLE_ENTITY = 422;  // [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误
    int STATUS_INTERNAL_SERVER_ERROR = 500;  // 内部错误
}
