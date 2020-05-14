package com.tom.mq;

import com.alibaba.fastjson.JSON;
import com.tom.utils.MailUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

public class MailListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        byte[] body = message.getBody();

        Map<String, String> map = JSON.parseObject(body, Map.class);

        MailUtils.sendMail(map.get("to"), map.get("title"), map.get("content"));
    }
}
