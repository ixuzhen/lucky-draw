package cn.itedus.lottery.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private int code;
    private String message;
    private T data;

    public static <E> R<E> successWithoutData(){
        return new R<E>(200,null,null);
    }
    public static <E> R<E> successWithMessage(String message){
        return new R<E>(200,message,null);
    }

    public static <E> R<E> successWithData(E data){
        return new R<E>(200,null,data);
    }

    public static <E> R<E> failed(String message){
        return new R<E>(400,message,null);
    }

}
