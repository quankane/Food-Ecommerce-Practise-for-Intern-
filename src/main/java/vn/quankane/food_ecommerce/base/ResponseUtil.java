package vn.quankane.food_ecommerce.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.quankane.food_ecommerce.base.response.ResponseData;
import vn.quankane.food_ecommerce.base.response.ResponseError;

public class ResponseUtil {

    public static ResponseEntity<ResponseData<?>> success(String message, Object data) {
        return success(HttpStatus.OK, message, data);
    }

    public static ResponseEntity<ResponseData<?>> success(HttpStatus status, String message) {
        ResponseData<?> response = new ResponseData<>(status.value(), message);
        return new ResponseEntity<>(response, status == HttpStatus.NO_CONTENT ? HttpStatus.OK : status);
    }

    public static ResponseEntity<ResponseData<?>> success(HttpStatus status, String message, Object data) {
        ResponseData<?> response = new ResponseData<>(status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseError<?>> error(HttpStatus status, String message) {
        ResponseError<?> response = new ResponseError<>(status.value(), message);
        return new ResponseEntity<>(response, status);
    }
}
