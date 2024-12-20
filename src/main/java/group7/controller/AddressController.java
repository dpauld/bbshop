package group7.controller;

import group7.controllerImpl.AddressControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AddressController implements AddressControllerImpl {
}
