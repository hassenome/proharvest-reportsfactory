package com.ec.proharvest.client;

import java.util.List;

import com.ec.proharvest.service.dto.UserDTO;

import org.springframework.web.bind.annotation.RequestMapping;

@AuthorizedFeignClient(name = "pilot")
public interface UserhandlerFeignClient {
    @RequestMapping(value = "/api/users")
    List<UserDTO> getUsersFromPilot();  
}
