package service.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import service.model.soap.userservice.GetUserByIdRequest;
import service.model.soap.userservice.UserRes;
import service.service.UserService;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE = "service/model/soap/userservice";
    @Autowired
    private UserService service;

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetUserByIdRequest")
    @ResponsePayload
    public UserRes getLoanStatus(@RequestPayload GetUserByIdRequest request) {
        return service.getUserById(request.getId()).toUserRes();
    }

}
