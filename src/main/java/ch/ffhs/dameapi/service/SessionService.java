package ch.ffhs.dameapi.service;


import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;


@Service
public class SessionService {


    /**
     * Catch the generated session id from the connected frontend
     *
     *
     * @return String of the session id
     * --> Error Handling by general Exception Handling
     */
    public String getHttpSessionId() {
        try{
            return RequestContextHolder.currentRequestAttributes().getSessionId();
        }
        catch(Exception e){
            //TODO: write to LOG
            return "unknown";
        }
    }
}
