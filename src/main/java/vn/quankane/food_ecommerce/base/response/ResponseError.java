package vn.quankane.food_ecommerce.base.response;

import java.io.Serializable;

public class ResponseError<T> extends ResponseData<T> implements Serializable {

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
