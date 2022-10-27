package ru.practicum.explorewithme.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private StatusError status;
    private String timestamp;

//    try{
//        if(run.run(i, us, adminPermission))runer = true;
//    }catch (java.lang.Throwable e){
//        e.printStackTrace();
//    }

}
