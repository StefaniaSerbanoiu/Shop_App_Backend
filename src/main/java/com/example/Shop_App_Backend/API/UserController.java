package com.example.Shop_App_Backend.API;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Validated // This annotation is used at the controller level to enable method validation.
@CrossOrigin(origins = "http://localhost:5173") // Specifying frontend origin
public class UserController {

}
