package com.app.tgdcc.telegram.updatehandlers;



import com.app.tgdcc.telegram.BotConfig;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChatHandler extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        /*//check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();

            //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
            if(message.hasText()) {

                //create a object that contains the information to send back the message
                String id = "-1001489343293";
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(id); //who should get the message? the sender from which we got the message...
                sendMessageRequest.setText("Groupe ID: " + message.getMediaGroupId());

                try {
                    execute(sendMessageRequest); //at the end, so some magic and send the message ;)
                } catch (TelegramApiException e) {
                    //do some error handling
                }
            }
        }*/



    }

    @Override
    public String getBotUsername() {
        return BotConfig.USERNAMEMYPROJECT;
    }

    @Override
    public String getBotToken() {
        return BotConfig.TOKENMYPROJECT;
    }

    public void sendMessage(SendMessage message){
        try {
            execute(message); //at the end, so some magic and send the message ;)
        } catch (TelegramApiException e) {
            //do some error handling
        }
    }

}
