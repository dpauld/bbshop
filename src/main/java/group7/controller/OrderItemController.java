package group7.controller;

import group7.controllerImpl.OrderItemControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class OrderItemController implements OrderItemControllerImpl {
}
