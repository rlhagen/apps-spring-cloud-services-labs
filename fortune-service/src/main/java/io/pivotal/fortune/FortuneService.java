package io.pivotal.fortune;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class FortuneService {

    @Autowired
    FortuneRepository repository;

    public Iterable<Fortune> getAll(){
        return repository.findAll();
    }

    public void add(Fortune fortune){
        repository.save(fortune);
    }

    public void clear(){
        repository.deleteAll();
    }

    public Fortune random(){
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt((int) this.repository.count() - 1);
        return StreamSupport.stream(this.repository.findAll().spliterator(), false).collect(Collectors.toList()).get(index);
    }

    @RabbitListener(queues = "fortune-queue")
    public void receiver(@Payload Fortune fortune){
        this.repository.save(fortune);
    }

}
