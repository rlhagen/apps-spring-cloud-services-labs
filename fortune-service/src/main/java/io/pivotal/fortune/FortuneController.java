package io.pivotal.fortune;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@FeignClient("fortune-service")
@RestController
public class FortuneController {
    private FortuneService fortuneService;

    @Autowired
    RabbitTemplate rabbit;

    @Autowired
    public FortuneController(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @RequestMapping(value = "/")
    public Iterable<Fortune> getAll(){
        return this.fortuneService.getAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addFortune(@RequestBody Fortune quote){
        this.fortuneService.add(quote);
    }

    @RequestMapping(value = "/async", method = RequestMethod.POST)
    public void addAsync(@RequestBody Fortune fortune){
        rabbit.convertAndSend("fortune-exchange", "fortune-queue", fortune);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void clear(){
        this.fortuneService.clear();
    }

    @RequestMapping(value = "/random")
    public Fortune getRandom(){
        return this.fortuneService.random();
    }

}
